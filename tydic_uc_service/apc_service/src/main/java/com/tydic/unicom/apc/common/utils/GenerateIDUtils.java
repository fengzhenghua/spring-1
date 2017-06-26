package com.tydic.unicom.apc.common.utils;

import java.util.UUID;

/**
 * 生成主键工具类
 * @author ZhangCheng
 * @date 2017-04-24
 */
public class GenerateIDUtils {
	
	/**
	 * 获取32位字符串，通过JDK的UUID获取。
	 * */
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
