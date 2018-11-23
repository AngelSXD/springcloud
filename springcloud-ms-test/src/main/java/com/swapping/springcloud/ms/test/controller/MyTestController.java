package com.swapping.springcloud.ms.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/test")
public class MyTestController {


    @Autowired
    RestTemplate testRestTemplate;

    Date date;
    /**
     * 登录 并 获取sessionKey
     * @return
     */
    @RequestMapping("/loginAndGetSessionKey")
    public String loginAndGetSessionKey(){

        String sessionKey = null;
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType contentType = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(contentType);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("loginName", "19999999999");
        jsonObject.put("loginPwd", "123456");
        jsonObject.put("loginType", "tenement");
        jsonObject.put("uiType", "pc");
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);

        //发送清除请求
        String url = "http://open.lqjava.com/wj/pisenfw/gateway/login";
//        String url = "http://localhost:34626/login";
        ResponseEntity<String> response = null;
        try {
            response = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                //请求成功
                String body = response.getBody();
                JSONObject parseObject = JSON.parseObject(body);
                System.out.println(parseObject);
                sessionKey = parseObject.toJSONString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessionKey;

    }

    /**
     * 生成sign
     * ba323306745f2fea44a77b57b0fdaca0a8346719
     *
     *
     * @return
     */
    @RequestMapping("/createSign")
    public String createSign(){
        String sessionKey = "0027c4fbf4d844a2b9edd5f589865c77";
        Map<String,Object> treeMap = new TreeMap<>();
        treeMap.put("luna-zuul-auth-login-name","13000000000");
        treeMap.put("luna-zuul-auth-ui-type","pc");
        treeMap.put("securityCode","299210229282059566");

        String sign = createSign(sessionKey,treeMap);
        return sign;
    }

    @RequestMapping("/createSign2")
    public String createSign2(){

        String sessionKey = "8fe2793a0ae44ba5805464e776b51b0b";
        Map<String,Object> treeMap = new TreeMap<>();
        treeMap.put("luna-zuul-auth-login-name","13000000000");
        treeMap.put("luna-zuul-auth-ui-type","pc");

        List<String> scList = new ArrayList<>();
        scList.add("123");
        scList.add("456");

        date = new Date(1542326400000L);
//        System.out.println("时间:"+date.getTime());
        treeMap.put("scList", JSONArray.parseArray(JSON.toJSONString(scList)));
        System.out.println(JSONArray.parseArray(JSON.toJSONString(scList)));
        treeMap.put("date",date);

        String sign = createSign(sessionKey,treeMap);
        return sign;
    }


    private String createSign(String sessionKey,Map<String,Object> params){

        Map<String,Object> json = new TreeMap<>();

        Set<Map.Entry<String,Object>> entrySet = params.entrySet();

        for (Map.Entry<String, Object> entry : entrySet) {

            Object value2 = entry.getValue();

            if(value2 != null){

                Object value = null;

                String simpleName = entry.getValue().getClass().getSimpleName();
                if("JSONArray".equals(simpleName)){
                    value = "array";
                }else if("JSONObject".equals(simpleName)){
                    value = "object";
                }else{
                    value = entry.getValue();
                }
                json.put(entry.getKey(), value);
            }else{
                json.put(entry.getKey(), "object");

            }


        }

        String x = JSON.toJSONString(json) + sessionKey;
        System.out.println(x);
        return DigestUtils.sha1Hex(x);
    }



    @RequestMapping("/getScBaseInfo")
    public String getScBaseInfo(){

        RestTemplate template=new RestTemplate();
        Map<String,String> params = new HashMap<>();
        params.put("luna-zuul-auth-login-name","13000000000");
        params.put("luna-zuul-auth-ui-type","pc");
        params.put("luna-zuul-auth-sign","ba323306745f2fea44a77b57b0fdaca0a8346719");
        params.put("securityCode","299210229282059566");
//        String result=template.getForObject(
//                "http://localhost:34626/ms-code/ten/getScBaseInfo?" +
//                        "luna-zuul-auth-login-name={luna-zuul-auth-login-name}" +
//                        "&luna-zuul-auth-ui-type={luna-zuul-auth-ui-type}" +
//                        "&luna-zuul-auth-sign={luna-zuul-auth-sign}" +
//                        "&securityCode={securityCode}", String.class, params);

        String result=template.getForObject(
                "http://open.lqjava.com/wj/pisenfw/gateway/ms-code/ten/getScBaseInfo?" +
                        "luna-zuul-auth-login-name={luna-zuul-auth-login-name}" +
                        "&luna-zuul-auth-ui-type={luna-zuul-auth-ui-type}" +
                        "&luna-zuul-auth-sign={luna-zuul-auth-sign}" +
                        "&securityCode={securityCode}", String.class, params);


        return result;
    }

    @RequestMapping("/actQa")
    public String actQa(){

        RestTemplate template=new RestTemplate();

        //封装 头信息
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType contentType = MediaType.parseMediaType("application/json;charset=UTF-8");
        requestHeaders.setContentType(contentType);

        //组装基础数据
        Map<String,Object> params = new HashMap<>();
        params.put("luna-zuul-auth-login-name","13000000000");
        params.put("luna-zuul-auth-ui-type","pc");
        params.put("luna-zuul-auth-sign","b6dc01c64a50451244f135b1d2ca3d7d87b36008");

        List<String> scList = new ArrayList<>();
        scList.add("123");
        scList.add("456");
        params.put("scList",JSONArray.parseArray(JSON.toJSONString(scList)));
        Date date = new Date();
        date.setTime(1542361391578L);
        params.put("date",date);



        HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(params), requestHeaders);

        String url = "http://open.lqjava.com/wj/pisenfw/gateway/ms-code/ten/code/qa/actQa";

        ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, requestEntity, String.class);

        String body = response.getBody();
        JSONObject parseObject = JSON.parseObject(body);
        System.out.println(body);
        String result = null;
        if (parseObject.getBoolean("success")) {
            result = (String) parseObject.get("obj");
        }

        return result;
    }
}
