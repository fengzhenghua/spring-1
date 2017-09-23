package com.course.encryption;


//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by fengzhenghua on
 *  2017-08-09 8:36.
 */
public class Base64Encryption {
    /**
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    //base64编码
    public static String base64ToString(String str) throws UnsupportedEncodingException {

       // =  Base64.encode(str.getBytes("utf-8"));
        String enStr =  Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
       return  enStr;
    }
    //base64解密
    public static String base64Ency(String Str) throws UnsupportedEncodingException {
        byte[] bytes =  Base64.getDecoder().decode(Str.getBytes(StandardCharsets.UTF_8));
        String decryStr = new String(bytes,"utf-8");
        return decryStr;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(base64ToString("淘汰"));
        System.out.println(base64Ency("5reY5rGw"));

    }
}
