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
@RequestMapping(value = "/dealer")
public class DealerController {


    @Autowired
    RestTemplate testRestTemplate;

    Date date;

    /**
     * 经销商 登录 并 获取sessionKey
     * @return
     */
    @RequestMapping("/dealerLoginAndGetSessionKey")
    public String dealerLoginAndGetSessionKey(){

        String sessionKey = null;
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType contentType = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(contentType);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("loginName", "pisenxa01");
        jsonObject.put("loginPwd", "pisenxa01");
        jsonObject.put("loginType", "dealer");
        jsonObject.put("uiType", "pc");
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);

        //发送清除请求
        String url = "http://code.3ceasy.com/login";
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
     * 经销商调用接口  生成sign
     *
     *3be2186f6ee10577ca1e6e37698e713cb9fc3195
     *
     * @return
     */
    @RequestMapping("/createSignDealer")
    public String createSignDealer(){
        String sessionKey = "8dc0e104ac324f32b01d68ef152efa82";
        Map<String,Object> treeMap = new TreeMap<>();
        treeMap.put("luna-zuul-auth-login-name","pisenxa01");
        treeMap.put("luna-zuul-auth-ui-type","pc");


        treeMap.put("uid","9624");

        String sign = createSign(sessionKey,treeMap);
        return sign;
    }


    /**
     * 经销商调用接口  生成sign
     *
     *756214848934422e82b999238a992593f4df1d7a
     *
     * @return
     */
    @RequestMapping("/createSignDealer2")
    public String createSignDealer2(){
        String sessionKey = "51bdccbbeb874ea4a63dcb96881770ef";
        Map<String,Object> treeMap = new TreeMap<>();
        treeMap.put("luna-zuul-auth-login-name","pisennn02");
        treeMap.put("luna-zuul-auth-ui-type","pc");
        treeMap.put("billCode","fhd-0010");
        treeMap.put("deliveryId","yuncangdealer");


        List<String> scList = new ArrayList<>();
        scList.add("183340001099788259");
        scList.add("183340001098178028");
        treeMap.put("scList", JSONArray.parseArray(JSON.toJSONString(scList)));

        String sign = createSign(sessionKey,treeMap);
        return sign;
    }




    /**
     * 调用经销商接口
     *
     * 获取经销商信息
     * @return
     */
    @RequestMapping("/getDealerInfo")
    public String getDealerInfo(){

        RestTemplate template=new RestTemplate();
        Map<String,String> params = new HashMap<>();
        params.put("luna-zuul-auth-login-name","pisenxa01");
        params.put("luna-zuul-auth-ui-type","pc");
        params.put("luna-zuul-auth-sign","f5d426a1db2651394acd177e56bda27d40239813");
        params.put("uid","9624");

        String result=template.getForObject(
                "http://code.3ceasy.com/ms-dealer/dealer/dealer/childAndMapping?" +
                        "luna-zuul-auth-login-name={luna-zuul-auth-login-name}" +
                        "&luna-zuul-auth-ui-type={luna-zuul-auth-ui-type}" +
                        "&luna-zuul-auth-sign={luna-zuul-auth-sign}" +
                        "&uid={uid}", String.class, params);


        return result;
    }


    @RequestMapping("/newDispatchBill")
    public String newDispatchBill(){

        RestTemplate template=new RestTemplate();

        //封装 头信息
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType contentType = MediaType.parseMediaType("application/json;charset=UTF-8");
        requestHeaders.setContentType(contentType);

        //组装基础数据
        Map<String,Object> params = new HashMap<>();
        params.put("luna-zuul-auth-login-name","19900000000");
        params.put("luna-zuul-auth-ui-type","pc");
        params.put("luna-zuul-auth-sign","9034c51715ec1ebba35e0ae7b6eee15e459dd89b");
        params.put("billCode","fhd-0010");
        params.put("deliveryId","yuncangdealer");

        List<String> scList = new ArrayList<>();
        scList.add("183340001099788259");
        scList.add("183340001098178028");
        params.put("scList", JSONArray.parseArray(JSON.toJSONString(scList)));


        HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(params), requestHeaders);

        String url = "http://code.3ceasy.com/ms-code/dealer/newDispatchBill";

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


}
