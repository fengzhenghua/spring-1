package com.tydic.unicom.crm.listener;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.ObjectUtils;

public class GlobalConfig {
	private static Map dataMap = new HashMap();

	private static GlobalConfig globalConfig = new GlobalConfig();
	

	public static GlobalConfig newInstance() {
		return globalConfig;
	}
	
	public Object get(String key) {
	    return dataMap.get(key);
	}
	
	public void initDataMap( InputStream resourceStream) throws Exception {
        Properties appConfig = new Properties();
        appConfig.load(resourceStream);
        Enumeration enumeration = appConfig.propertyNames();
        while(enumeration.hasMoreElements()) {
            String key = ObjectUtils.toString(enumeration.nextElement());
            String value = appConfig.getProperty(key);
            dataMap.put(key, value);
        }
	}
}
