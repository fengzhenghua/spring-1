package com.tydic.unicom.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * 对密码进行加密和验证的类
 */
public class EncodeUtil {
	
	// 十六进制下数字到字符的映射数组
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
	
	/** * 把inputString加密 */
	public static String encode(String inputString) {
		return encodeByMD5(inputString);
	}
	
	/**
	 * 验证输入的密码是否正确
	 * @param password 加密后的密码
	 * @param inputString 输入的字符串
	 * @return 验证结果，TRUE:正确 FALSE:错误
	 */
	private static boolean validatePassword(String password, String inputString) {
		if (password.equals(encodeByMD5(inputString))) {
			return true;
		} else {
			return false;
		}
	}
	
	/** 对字符串进行MD5加密 */
	private static String encodeByMD5(String originString) {
		if (originString != null) {
			try {
				// 创建具有指定算法名称的信息摘要
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
				byte[] results = md.digest(originString.getBytes());
				// 将得到的字节数组变成字符串返回
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 宁夏版专用,对字符串进行MD5加密
	 * @author 覃健 qinjian@tydic.com
	 * @date 2015年10月12日 下午3:08:12
	 */
	public static String encodeByMD5(String InputPwd, String ParaPwd){
	    long bytes = 6L; long retlen = 6L;
	    long randSeed1 = 23456L;
	    long randSeed2 = 31212L;
	    long randSeed3 = 1029L;
	    long modSeed = 32768L;
	    long[] mm = new long[101];
	    char tempChar = '\000';
	    char tempChar1 = '\000';
	    char tempChar2 = '\000';
	    String cmm = ""; String pwd = "";
	    String result = "";

	    if ((InputPwd == null) || (InputPwd.length() <= 0)) {
	      return null;
	    }
	    if ((ParaPwd == null) || (ParaPwd.length() <= 0)) {
	      return null;
	    }
	    pwd = InputPwd + ParaPwd;

	    long longtmp = 0L;
	    int length = pwd.length();
	    for (int i = 1; i <= length; i++) {
	      tempChar = pwd.charAt(i - 1);
	      longtmp = tempChar;
	      randSeed1 = (randSeed1 + longtmp * i) % modSeed;
	      randSeed2 = (randSeed2 + longtmp * (length - i)) % (modSeed / 4L) * 2L;
	      randSeed3 = (randSeed3 + longtmp * longtmp) % (modSeed / 4L) * 2L + 1L;
	    }

	    InputPwd = pwd;
	    if (bytes > 10L) {
	      bytes = 10L;
	    }
	    length = InputPwd.length();

	    if (length <= bytes)
	    {
	      for (length += 1; length <= bytes; length++) {
	        randSeed1 = (randSeed1 * randSeed2 + randSeed3) % modSeed;
	        longtmp = randSeed1 % 126L;
	        if (longtmp < 33L) {
	          longtmp = 65L + longtmp;
	        }
	        InputPwd = InputPwd + (char)(int)longtmp;
	      }
	    }

	    length = ParaPwd.length();

	    if (length <= bytes)
	    {
	      for (length += 1; length <= bytes; length++) {
	        randSeed1 = (randSeed1 * randSeed2 + randSeed3) % modSeed;
	        longtmp = randSeed1 % 126L;
	        if (longtmp < 33L) {
	          longtmp = 65L + longtmp;
	        }
	        ParaPwd = ParaPwd + (char)(int)longtmp;
	      }

	    }

	    longtmp = (randSeed1 * randSeed2 + randSeed3) % modSeed;
	    for (int j = 1; j <= bytes; j++) {
	      for (int k = 1; k <= bytes; k++) {
	        tempChar1 = InputPwd.charAt(j - 1);
	        tempChar2 = ParaPwd.charAt(j - 1);
	        longtmp = (longtmp * randSeed1 + tempChar1 * tempChar2 * j) % modSeed;

	        if (longtmp >= modSeed / 2L) {
	          randSeed1 = (randSeed1 * randSeed2 + randSeed3) % modSeed;
	          mm[(int)(randSeed1 % (bytes * bytes))] = randSeed1;
	        } else {
	          randSeed1 = (randSeed1 * (randSeed3 + 1L) + randSeed2 + 1L) % modSeed;

	          mm[(int)(randSeed1 % (bytes * bytes))] = randSeed1;
	        }
	      }
	    }

	    for (int k = (int)(bytes * bytes); k >= 1; k--) {
	      if (k > 1) {
	        mm[(k - 1)] += mm[k] / 256L;
	      }
	    }

	    for (int k = 1; k <= bytes * bytes; k++) {
	      randSeed1 = randSeed1 * randSeed1 % modSeed;
	      if (mm[k] == 0L) {
	        mm[k] = randSeed1;
	      }

	    }

	    for (int k = 1; k <= bytes; k++) {
	      for (int i = 1; i <= bytes; i++) {
	        tempChar1 = InputPwd.charAt(i - 1);
	        tempChar2 = ParaPwd.charAt(k - 1);
	        longtmp = mm[(int)(i + (k - 1) * bytes)] * tempChar2 * tempChar1 % 62L;
	        if (longtmp < 10L) {
	          cmm = cmm + (char)(int)(longtmp + 48L);
	        }
	        else if (longtmp < 36L) {
	          cmm = cmm + (char)(int)(longtmp - 10L + 97L);
	        }
	        else if (longtmp < 62L)
	          cmm = cmm + (char)(int)(longtmp - 36L + 65L);
	        else {
	          cmm = cmm + "_";
	        }

	      }

	    }

	    result = cmm.substring(0, (int)retlen);
	    return result;
	  }
	
	/**
	 * 转换字节数组为十六进制字符串
	 * @param 字节数组
	 * @return 十六进制字符串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	
	/** 将一个字节转化成十六进制形式的字符串 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	/*
	 * public static void main(String[] args) { String pwd1="1"; String pwd2=""; EncodeUtil cipher = new EncodeUtil(); System.out.println("未加密的密码:"+pwd1);
	 * //将123加密 pwd2 = cipher.generatePassword(pwd1); System.out.println("加密后的密码:"+pwd2); System.out.print("验证密码是否下确:"); if(cipher.validatePassword(pwd2, pwd1))
	 * { System.out.println("正确"); } else { System.out.println("错误"); } }
	 */
	
	private static byte[] base64DecodeChars = new byte[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1,
	        -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
	        12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34,
	        35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};
	
	/**
	 * base64解密
	 * @param base64编码
	 * @return 字节数组
	 */
	public static byte[] decode(String str) {
		byte[] data = str.getBytes();
		int len = data.length;
		ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
		int i = 0;
		int b1, b2, b3, b4;
		
		while (i < len) {
			
			/* b1 */
			do {
				b1 = base64DecodeChars[data[i++]];
			} while (i < len && b1 == -1);
			if (b1 == -1) {
				break;
			}
			
			/* b2 */
			do {
				b2 = base64DecodeChars[data[i++]];
			} while (i < len && b2 == -1);
			if (b2 == -1) {
				break;
			}
			buf.write((int)((b1 << 2) | ((b2 & 0x30) >>> 4)));
			
			/* b3 */
			do {
				b3 = data[i++];
				if (b3 == 61) {
					return buf.toByteArray();
				}
				b3 = base64DecodeChars[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1) {
				break;
			}
			buf.write((int)(((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
			
			/* b4 */
			do {
				b4 = data[i++];
				if (b4 == 61) {
					return buf.toByteArray();
				}
				b4 = base64DecodeChars[b4];
			} while (i < len && b4 == -1);
			if (b4 == -1) {
				break;
			}
			buf.write((int)(((b3 & 0x03) << 6) | b4));
		}
		return buf.toByteArray();
	}
	
	/**
	 * 返回SHA-1算法的 MessageDigest对象字节数组
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] getSHA1Digest(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			bytes = md.digest(data.getBytes("utf-8"));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse);
		}
		return bytes;
	}

	/**
	 * 二进制字节数组转十六进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}
	
	//生成包头签名 --by yangzhifan
	public static String APP_KEY = "FACE-CCDD-6677-RRFF";
	public static String encodeHmacMD5(byte[] data, byte[] key) throws Exception {
		// 还原密钥  
		SecretKey secretKey = new SecretKeySpec(key, "HmacMD5");  
		// 实例化Mac  
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());  
		//初始化mac  
		mac.init(secretKey);  
		//执行消息摘要  
		byte[] digest = mac.doFinal(data);  
		return new HexBinaryAdapter().marshal(digest);//转为十六进制的字符串  
	}
	public static String hmacMD5(String input_src) throws Exception {

		if (input_src==null || "".equals(input_src)) {
			return "";
		}

		byte[] _secret =new HexBinaryAdapter().unmarshal("838C4C7C98A6A64E44D4249E229A61DD814C163683C76B956A9AF41B671C2205100702FC82751D03ECABCA716E19A90DBE7C4E07CAED810A2FCCAB43536F722A");
		String reqstr = APP_KEY+input_src;
//		System.out.println("--reqstr---");
//		System.out.println(reqstr);
//		System.out.println("--reqstr---");
		String str=encodeHmacMD5(reqstr.getBytes("utf-8"),_secret);
		//String str=encodeHmacMD5((input_src).getBytes("utf-8"),_secret);
		return str;

	}


}
