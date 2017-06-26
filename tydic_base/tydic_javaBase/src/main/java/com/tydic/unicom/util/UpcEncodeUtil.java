package com.tydic.unicom.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

/**
 * UPC请求使用的加密方式
 * @author 吴川
 *
 */
public class UpcEncodeUtil {

	//公钥加密过程
	public static String publicEncode(String paramStr, String publicKey, String signKey) throws Exception{
		
		//获取签名
		String sign = getSign(paramStr, signKey);
		
		//并上签名数据
		paramStr += "<sign>"+sign+"</sign>";
		
		//还原公钥
		PublicKey pk = RsaEncodeUtil.restorePublicKey(publicKey);
        
		//RSA加密
        byte[] encodedText = RsaEncodeUtil.rsaPublicEncode(pk, paramStr.getBytes("UTF-8"));
        
        //转成base64
        String result = Base64.encodeBase64String(encodedText);
		
		return result;
	}
	
	//公钥解密
	public static String publicDecode(String encodeStr, String publicKey) throws Exception{
		
		//还原公钥
		PublicKey pk = RsaEncodeUtil.restorePublicKey(publicKey);
        
		//RSA解密
        byte[] decodeBytes = RsaEncodeUtil.rsaPublicDecode(pk, Base64.decodeBase64(encodeStr));
        String result = new String(decodeBytes, "UTF-8");
		
		return result;
	}
	
	//解密后签名校验
	public static boolean signValid(String str, String signKey) throws Exception{
		
		Map<String, Object> paramMap = RsaEncodeUtil.getMapFromXML(str);
		
		//获取接收到的签名
		String reqSign = paramMap.get("sign").toString();
		
		//把签名从数据中移除
		paramMap.remove("sign");
		
		//再进行签名
		String sign = getSign(paramMap, signKey);
		
		return reqSign.equals(sign);
	}
	
	
	//私钥加密过程
	public static String privateEncode(String paramStr, String privateKey, String signKey) throws Exception{
		
		//获取签名
		String sign = getSign(paramStr, signKey);
		
		//并上签名数据
		paramStr += "<sign>"+sign+"</sign>";
		
		//还原公钥
		PrivateKey pk = RsaEncodeUtil.restorePrivateKey(privateKey);
        
		//RSA加密
        byte[] encodedText = RsaEncodeUtil.rsaPrivateEncode(pk, paramStr.getBytes("UTF-8"));
        
        //转成base64
        String result = Base64.encodeBase64String(encodedText);
		
		
		return result;
	}
	
	//私钥解密
	public static String privateDecode(String encodeStr, String privateKey) throws Exception{
		
		//还原公钥
		PrivateKey pk = RsaEncodeUtil.restorePrivateKey(privateKey);
        
		//RSA解密
        byte[] decodeBytes = RsaEncodeUtil.rsaPrivateDecode(pk, Base64.decodeBase64(encodeStr));
        String result = new String(decodeBytes, "UTF-8");
		
		return result;
	}
	
	//获取签名
	public static String getSign(String param, String signKey) throws NoSuchAlgorithmException, ParserConfigurationException, IOException, SAXException, DocumentException{
		Map<String, Object> paramMap = RsaEncodeUtil.getMapFromXML(param);
		return RsaEncodeUtil.sha1Sign(paramMap, signKey);
	}
	
	//获取签名
	public static String getSign(Map<String, Object> paramMap, String signKey) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		return RsaEncodeUtil.sha1Sign(paramMap, signKey);
	}
}
