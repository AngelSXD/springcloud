package com.swapping.springcloud.ms.test.tests;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;
import java.util.TreeMap;

public class SignTest {

    public static void main(String[] args) {

        System.out.println(createSign());
    }


    public static String createSign(){
        String sessionKey = "ccb290e5eeae9394";
        String sign = "";

        Map<String,Object>  sortMap = new TreeMap<>();
        sortMap.put("account","liyang");
        sortMap.put("code","1619782622");
        sortMap.put("saleDate","2018-11-23");


        String str1 = "";

        for (String s : sortMap.keySet()) {
            str1 +=("&"+s+"="+sortMap.get(s));
        }
        str1=str1.substring(1);

        System.out.println("str1结果:"+str1);
        String str2 = str1+sessionKey;
        System.out.println("str2结果:"+str2);
        sign = DigestUtils.md5Hex(str2);
        return sign;
    }

}
