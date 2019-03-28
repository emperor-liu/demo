/**
 * Project Name hxs
 * File Name EncryptionUtil
 * Package Name com.huxiaosu.demo.security.utils
 * Create Time 2019/3/6
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.security.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;


/**
 * Description
 * 加密工具
 * <ul>
 * <li>SHA-1</li>
 * <li>MD5</li>
 * <li>Base64</li>
 * <li>RSA</li>
 * <li>DES</li>
 * </ul>
 *
 * @ClassName: EncryptionUtil
 * @author: liujie
 * @date: 2019/3/6 10:34
 */
@Slf4j
public class EncryptionUtil {
    private final static char[] hexDigits = "0123456789ABCDEF".toCharArray();


    /**
     * Description： SHA-1加密
     *
     * @param data 待加密数据
     * @return String
     * @author name：lljqiu
     **/
    public static String encryptSHA1(String data) {
        return data == null ? null : hashEncrypt(data, "SHA-1");
    }

    /**
     * Description： md5 加密
     *
     * @param data
     * @return String
     * @author name：lljqiu
     **/
    public static String encryptMD5(String data) {
        return data == null ? null : hashEncrypt(data, "MD5");
    }


    /**
     * Description： hash 加密算法
     *
     * @param data
     * @param algorithm
     * @return String
     * @author name：lljqiu
     **/
    private static String hashEncrypt(String data, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data.getBytes());
            return bytes2Hex(md.digest());
        } catch (Exception never) {
            throw new RuntimeException("hash 算法异常，没有该算法");
        }
    }

    /**
     * Description： byte ==>> String
     *
     * @param bytes
     * @return String
     * @author name：lljqiu
     **/
    private static String bytes2Hex(byte[] bytes) {
        int len = bytes.length;
        char[] str = new char[len * 2];
        for (int i = 0; i < len; i++) {
            byte b = bytes[i];
            str[i * 2] = hexDigits[b >>> 4 & 0xF];
            str[i * 2 + 1] = hexDigits[b & 0xF];
        }
        return new String(str);
    }


}
