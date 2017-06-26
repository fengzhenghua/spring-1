package com.tydic.unicom.uif.service.impl.ablit.provider.aop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月18日 上午12:01:58
 * @ClassName EcAopMap
 * @Description
 * @version V1.0
 */
public class EcAopMap extends HashMap<String, String> {
	
	private static final long serialVersionUID = -1277791390393392630L;
	
	public EcAopMap() {
	}
	
	public EcAopMap(Map<? extends String, ? extends String> m) {
		super(m);
	}
	
	public String put(String key, Object value) {
		String strValue;
		if (value == null) {
			strValue = null;
		} else {
			if ((value instanceof String)) {
				strValue = (String)value;
			} else {
				if ((value instanceof Integer)) {
					strValue = ((Integer)value).toString();
				} else {
					if ((value instanceof Long)) {
						strValue = ((Long)value).toString();
					} else {
						if ((value instanceof Float)) {
							strValue = ((Float)value).toString();
						} else {
							if ((value instanceof Double)) {
								strValue = ((Double)value).toString();
							} else {
								if ((value instanceof Boolean)) {
									strValue = ((Boolean)value).toString();
								} else {
									if ((value instanceof Date)) {
										DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										strValue = format.format((Date)value);
									} else {
										strValue = JSON.toJSONString(value);
									}
								}
							}
						}
					}
				}
			}
		}
		return put(key, strValue);
	}
	
	public String put(String key, String value) {
		if ((key != null) && (key.length() > 0)) {
			return (String)super.put(key, value);
		}
		return null;
	}
}
