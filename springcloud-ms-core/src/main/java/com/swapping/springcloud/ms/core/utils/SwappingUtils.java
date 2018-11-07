package com.swapping.springcloud.ms.core.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * swapping项目下所有的工具方法
 * @author SXD
 * @date    2018.10.26
 */
public class SwappingUtils {

    public static final String MD5_KEY = "Angel_SXD";

    /**
     *  公共获取UUID方法
     *  获取长度为32位的UUID
     * @return
     */
    public static String uid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * MD5加密方法
     *
     * @param original  原始文
     * @return  加密文
     */
    public static String MD5(String original){
        return DigestUtils.md5Hex(original+MD5_KEY);
    }

    /**
     * MD5验证方法
     * @param original  原始文
     * @param md5       待验证加密文
     * @return  验证通过true、失败false
     */
    public static boolean MD5_verify(String original,String md5){
        String encryption = DigestUtils.md5Hex(original+MD5_KEY);
        return  encryption.equals(md5) ? true : false;
    }
}
