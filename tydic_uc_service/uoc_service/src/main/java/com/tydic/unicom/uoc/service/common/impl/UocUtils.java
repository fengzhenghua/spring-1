package com.tydic.unicom.uoc.service.common.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class UocUtils {

	private static Logger logger = Logger.getLogger(UocUtils.class);
	
	/**bean to map*/
	public static Map<String,Object> beanToMap(Object obj) throws Exception{
		
		if(obj == null){  
            return null;  
        } 
		
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        
        for (PropertyDescriptor property : propertyDescriptors) { 
            String key = property.getName();  

            // 过滤class属性  
            if (!key.equals("class")) {  
                // 得到property对应的getter方法  
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);
                if(value != null){
                	resultMap.put(key, value);
                }
            }  

        }
        return resultMap;
	}
	
}
