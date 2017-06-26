package com.tydic.unicom.crawlerframe.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RString {
	// public String rs;

	public RString() {
		// TODO Auto-generated constructor stub
		// rs = ps;
	}

	/**
	 * 
	 * @param str			传入的字符
	 * @param regEx			查找的正则表达式内容
	 * @param include		是否包含查找的正则表达式文本
	 * @return	1,2,3,4,5
	 */
	public static String RegExfinds(String str, String regEx,boolean include) {
		synchronized (str) {
			// 编译正则表达式
			Pattern pattern = Pattern.compile(regEx);
			// 忽略大小写的写法
			// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(str);
			String fd = "";
			// 查找字符串中是否有匹配正则表达式的字符/字符串
			while (matcher.find()) {
				if(!include){
					String tmp = "";
					try{
						tmp = matcher.group(1);
					}catch(Exception e){
						//没有找到包含的字符串，这个错误一般是在正则表达式中没有包含  ()照成的
						tmp = matcher.group(0);
					}
					fd = fd + "," + tmp;
				}else{
					fd = fd + "," + matcher.group(0);
				}
			}
			return fd;
		}
	}

	/**
	 * 
	 * @param str			传入的字符
	 * @param regEx			查找的正则表达式内容
	 * @param include		是否包含查找的正则表达式文本
	 * @return
	 */
	public static String RegExfind(String str, String regEx,boolean include) {
		synchronized (str) {
			// 编译正则表达式
			Pattern pattern = Pattern.compile(regEx);
			// 忽略大小写的写法
			// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(str);
			// 查找字符串中是否有匹配正则表达式的字符/字符串
			if (matcher.find()) {
				if(!include){
					String tmp = "";
					try{
						tmp = matcher.group(1);
					}catch(Exception e){
						//没有找到包含的字符串，这个错误一般是在正则表达式中没有包含  ()照成的
						tmp = matcher.group(0);
					}
					return tmp;
				}else{
					return matcher.group(0);
				}
			} else
				return "";
		}
	}

	public static void main(String[] args) {
		RString rs = new RString();
//		String f = rs.find("\"#J{var a=$0;a;}#context.2.姓名\"", "#J\\{.*\\}#", true);
		String v = "#J{var a=$0;a;}#context.2.姓名,context.2.姓名1\"";
		String format = rs.RegExfind(v, "(.*)\\\"$", false);

		System.out.println(format);
		
		
		
//		System.out.println("return " + f);
	}

}
