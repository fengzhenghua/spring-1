package com.tydic.unicom.upc.alipay.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.tydic.unicom.upc.alipay.config.Configs;

import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyangkly on 15/6/27.
 * 杂物工具类
 */
public class Utils {

    private Utils() {
        // No instances.
    }

    public static String toAmount(long amount) {
        return new BigDecimal(amount).divide(new BigDecimal(100)).toString();
    }

    public static String toDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static boolean isEmpty(Object object) {
        if (object instanceof String) {
            return StringUtils.isEmpty((String) object);
        }
        return object == null;
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static <T> boolean isListNotEmpty(List<T> list) {
        return list != null && list.size() > 0;
    }

    public static <T> boolean isListEmpty(List<T> list) {
        return !isListNotEmpty(list);
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
    public static boolean verify(Map<String, String> params, String publicKey) throws Exception{
    	//过滤空值、sign与sign_type参数
    	String sign = params.get("sign");
    	//String sign_type = params.get("sign_type");
    	params.remove("sign");
    	params.remove("sign_type");
        //获取待签名字符串
        String preSignStr = createLinkString(params);
        //获得签名验证结果
        return verify(preSignStr, sign, publicKey, params.get("charset"));
    }
    
    private static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        //字典排序，在签名验证签名过程中都必须经过字典排序
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    private static boolean verify(String content, String sign, String ali_public_key, String input_charset) throws Exception{
		KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // 更改为RSA2
        byte[] encodedKey = Base64.decodeBase64(ali_public_key);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
		java.security.Signature signature = java.security.Signature.getInstance(Configs.getDecodSingTypeInstance()); //SHA1WithRSA更改为
	
		signature.initVerify(pubKey);
		signature.update( content.getBytes(input_charset) );
	
		boolean bverify = signature.verify( Base64.decodeBase64(sign) );
		return bverify;

	}
}
