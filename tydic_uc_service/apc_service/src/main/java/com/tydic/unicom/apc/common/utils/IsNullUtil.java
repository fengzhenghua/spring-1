package com.tydic.unicom.apc.common.utils;

public class IsNullUtil {

	//判断一个对象是否为空
	public static boolean isNull(Object o) {
		if(o instanceof String){
			String str=(String)o;
			if (str==null) {
				return true;
			}else if ("".equals(str.trim())) {
				return true;
			}else {
				return false;
			}
		}else {
			if (o==null) {
				return true;
			}else {
				return false;
			}
		}
	}
}
