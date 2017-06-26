/**
 * 
 */
package com.tydic.unicom.crm.web.uss.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 对字符串进行加解密和加解压
 * 
 * @author zhangrong
 * 
 *         2016-7-19
 */
@SuppressWarnings("restriction")
public class GZipUtil {
	private static Logger log = LoggerFactory.getLogger(GZipUtil.class);

	/**
	 * 将字符串压缩后Base64
	 * 
	 * @param primStr
	 *            待加压加密函数
	 * @return
	 */
	public static String gzipString(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return primStr;
		}
		ByteArrayOutputStream out = null;
		GZIPOutputStream gout = null;
		try {
			out = new ByteArrayOutputStream();
			gout = new GZIPOutputStream(out);
			gout.write(primStr.getBytes("UTF-8"));
			gout.flush();
		} catch (IOException e) {
			log.error("对字符串进行加压加密操作失败：", e);
			return null;
		} finally {
			if (gout != null) {
				try {
					gout.close();
				} catch (IOException e) {
					log.error("对字符串进行加压加密操作，关闭gzip操作流失败：", e);
				}
			}
		}
		return new BASE64Encoder().encode(out.toByteArray());
	}

	/**
	 * 将压缩并Base64后的字符串进行解密解压
	 * 
	 * @param compressedStr
	 *            待解密解压字符串
	 * @return
	 */
	public static final String ungzipString(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		GZIPInputStream gin = null;
		String decompressed = "";
		try {
			byte[] compressed = new BASE64Decoder().decodeBuffer(compressedStr);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			gin = new GZIPInputStream(in);
			byte[] buffer = new byte[1024];
			int offset = 0;
			while ((offset = gin.read(buffer)) > 0) {
				out.write(buffer, 0, offset);
			}
			for (int i = 0; i < compressed.length; i++) {
				decompressed += compressed[i]+",";
				}
			
//			decompressed = out.toString("UTF-8");
//			decompressed=new String(out.toByteArray(),"UTF-8");
		} catch (IOException e) {
			log.error("对字符串进行解密解压操作失败：", e);
			decompressed = null;
		} finally {
			if (gin != null) {
				try {
					gin.close();
				} catch (IOException e) {
					log.error("对字符串进行解密解压操作，关闭压缩流失败：", e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("对字符串进行解密解压操作，关闭输入流失败：", e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error("对字符串进行解密解压操作，关闭输出流失败：", e);
				}
			}
		}
		return decompressed;
	}

	public static void main(String[] args) {
		System.out.println(ungzipString(gzipString("1234567")));
	}
	/**
	 * 代替String对象的replaceAll函数(用于非法字符的处理)
	 * 
	 * @param strOld
	 * @param strLook
	 * @param strReplace
	 * @author 况浩
	 * @return
	 */
	public static String replaceString(String strOld, String strLook,
			String strReplace) {
		String strNew = "";
		if (strOld == null) { // 为null返回""
			return "";
		}

		if (strLook == null || strReplace == null) { // 其中一个为null,返回oldstr;
			return strOld;
		}

		int i = strOld.indexOf(strLook);
		if (i != -1) {
			while (i != -1) {
				strNew = strNew + strOld.substring(0, i) + strReplace;
				strOld = strOld.substring(i + strLook.length());
				// i=strOld.indexOf(strLook);
				i = strOld.indexOf(strLook);
			}
			strNew = strNew + strOld;
		} else {
			strNew = strOld;
		}
		return strNew; // 返回组成的新串

	}
}