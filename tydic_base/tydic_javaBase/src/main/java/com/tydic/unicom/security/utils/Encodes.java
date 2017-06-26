package com.tydic.unicom.security.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.tydic.unicom.exception.DefinitionExceptions;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月5日 下午9:20:02
 * @ClassName Encodes
 * @Description 编码类
 * @version V1.0
 */
public class Encodes {
	
	private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	
	public static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}
	
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw DefinitionExceptions.unchecked(e);
		}
	}
	
	public static String encodeBase64(byte[] input) {
		return Base64.encodeBase64String(input);
	}
	
	public static String encodeUrlSafeBase64(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}
	
	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input);
	}
	
	public static String encodeBase62(byte[] input) {
		char[] chars = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
		}
		return new String(chars);
	}
	
	public static String escapeXml(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}
	
	public static String unescapeXml(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}
	
	public static String urlEncode(String part) {
		try {
			return URLEncoder.encode(part, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw DefinitionExceptions.unchecked(e);
		}
	}
	
	public static String urlDecode(String part) {
		try {
			return URLDecoder.decode(part, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw DefinitionExceptions.unchecked(e);
		}
	}
	
	public static String encodeMd5(String input) {
		StringBuilder buf = new StringBuilder();
		if (StringUtils.isNotEmpty(input))
			try {
				byte[] unencodedPassword = input.getBytes("UTF-8");
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.reset();
				md.update(unencodedPassword);
				byte[] encodedPassword = md.digest();
				
				for (int i = 0; i < encodedPassword.length; i++) {
					if ((encodedPassword[i] & 0xFF) < 16) {
						buf.append("0");
					}
					buf.append(Long.toString(encodedPassword[i] & 0xFF, 16));
				}
				return buf.toString();
			} catch (Exception localException) {
			}
		return null;
	}
	
	public static byte[] encodeMd5(byte[] content) {
		if ((content != null) && (content.length > 0)) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.reset();
				md.update(content);
				return md.digest(content);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
