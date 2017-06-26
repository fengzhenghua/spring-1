package com.tydic.unicom.util;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月28日 下午4:36:23
 * @ClassName EnumHelper
 * @Description 获取枚举类型
 * @version V1.0
 */
public class EnumHelper {
	/**
	 * 获取枚举类型
	 * 
	 * @param enumStr 枚举字符
	 * @param clazz 枚举类型的class
	 * @return
	 */
	public static <T extends Object> T getEnum(String enumStr, Class<T> clazz) {
		if (StringUtils.isNotEmpty(enumStr) && clazz != null) {
			if (clazz.isEnum()) {
				enumStr = enumStr.trim();
				//枚举常量
				T[] enums = clazz.getEnumConstants();
				if (enums != null && enums.length > 0) {
					for (int i = 0; i < enums.length; i++) {
						//匹配枚举字符串匹配
						if (enums[i].toString().equalsIgnoreCase(enumStr)) {
							return enums[i];
						}
					}
				}
			}
		}
		return null;
	}
	/**
	 * 获取枚举类型
	 * @param enumOrdinal 枚举数字常量
	 * @param clazz 枚举类型class
	 * @return
	 */
	public static <T extends Object> T getEnum(int enumOrdinal, Class<T> clazz) {
		if ( clazz != null) {
			if (clazz.isEnum()) {
				//枚举常量
				T[] enums = clazz.getEnumConstants();
				if (enums != null && enums.length > 0) {
					for (int i = 0; i < enums.length; i++) {
						//匹配枚举值相匹配
						if(enumOrdinal ==((Enum<?>)enums[i]).ordinal()){
							return enums[i];
						}
					}
				}
			}
		}
		return null;
	}

}
