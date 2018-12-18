package com.swapping.springcloud.ms.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sms")
public class SmsController {


    public static final String SMS_REDIS_KEY = "ms-sms:smsKey";

    @Autowired
    RestTemplate testRestTemplate;

    @Autowired
    StringRedisTemplate redisTemplate;


    /**
     * 发送短信前 获取token验证接口
     * @return
     */
    @RequestMapping(value = "/getToken")
    public String getToken(){

        JSONObject object = null;
        String token = null;

        try {
            boolean hasKey = redisTemplate.hasKey(SMS_REDIS_KEY);
            //redis中是否缓存了的
            if (hasKey){
                object = JSON.parseObject(redisTemplate.opsForValue().get(SMS_REDIS_KEY));
            }else {
                //云码系统 仅调用接口未提供管理功能  所以此处参数写死
                String appKey = "15312453";
                String appSecret = "NTWXdCs3Hvyu51T5";

                BASE64Encoder encoder = new BASE64Encoder();
                String authorization = encoder.encode((appKey+":"+appSecret).getBytes());

                RestTemplate template=new RestTemplate();

                //封装 头信息
                HttpHeaders requestHeaders = new HttpHeaders();
                MediaType contentType = MediaType.parseMediaType("application/json;charset=UTF-8");
                requestHeaders.setContentType(contentType);
                requestHeaders.add("Authorization","Basic "+authorization);


                HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
                String url = "https://gateway.pisen.com.cn/oauth/token?grant_type=client_credentials";

                ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, requestEntity, String.class);

                String body = response.getBody();
                object = JSON.parseObject(body);
                int timeOut = object.getInteger("expires_in");
                redisTemplate.opsForValue().set(SMS_REDIS_KEY,object.toJSONString(),timeOut, TimeUnit.SECONDS);
            }



            System.out.println(object.toJSONString());
            String tokerType = object.getString("token_type");
            String accessToken = object.getString("access_token");



            tokerType = upperCaseFirst(tokerType);
            token = tokerType+" "+accessToken;

        }catch (Exception e){
            token = "error";
        }

        System.out.println(token);
        return token;
    }



    public static  String upperCaseFirst(String str){
        return upperCase(str.substring(0, 1)) + str.substring(1);
    }


    public static String upperCase(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }


    /**
     * 发送短信接口
     * @return
     */
    @RequestMapping(value = "/sendSms")
    public String  sendSms(){
        String token = "Bearer f04e7044-ee95-4076-875a-e192a9371515";
        String code = "";

        RestTemplate template=new RestTemplate();

        //封装 头信息
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType contentType = MediaType.parseMediaType("application/json;charset=UTF-8");
        requestHeaders.setContentType(contentType);
        requestHeaders.add("Authorization",token);
        requestHeaders.add("AppKey","15312453");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", "16602803310");
        jsonObject.put("msg", "测试发送短信内容");

        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);
        String url = "https://gateway.pisen.com.cn/api/sms/Send";

        ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, requestEntity, String.class);

        String body = response.getBody();
        JSONObject object = JSONObject.parseObject(body);
        JSONObject object2 = JSONObject.parseObject(object.getString("SendResult"));
        System.out.println(body);
        boolean isError = object2.getBoolean("IsError");
        if (!isError){
            code = "200";
        }else {
            code = object.getString("ErrCode");
        }


        return code;
    }

}
