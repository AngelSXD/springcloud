package com.swapping.springcloud.ms.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@RestController
@RequestMapping("/test")
public class MyTestController {


    @Autowired
    RestTemplate testRestTemplate;

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
        jsonObject.put("loginName", "13000000000");
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
     * 2da8a91c94164df9ac3f08c21061841a
     *
     * {"luna-zuul-auth-login-name":"sxd","luna-zuul-auth-ui-type":"pc","securityCode":"262541833908841115"}db7b75f07d7d41c3bc5b033c2009c0b5
     * @return
     */
    @RequestMapping("/createSign")
    public String createSign(){
        String sessionKey = "4f76b87dfd544c6c9290d0d0196e9b09";
        Map<String,Object> treeMap = new TreeMap<>();
        treeMap.put("luna-zuul-auth-login-name","13000000000");
        treeMap.put("luna-zuul-auth-ui-type","pc");
        treeMap.put("securityCode","182970001001848481");

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
        params.put("luna-zuul-auth-sign","f0d3a31cb7d26da0e088be8cb91be785ce418d13");
        params.put("securityCode","182970001001848481");
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



}
