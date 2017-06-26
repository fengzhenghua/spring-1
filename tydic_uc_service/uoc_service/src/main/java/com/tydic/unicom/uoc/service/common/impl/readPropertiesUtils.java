package com.tydic.unicom.uoc.service.common.impl;

import java.io.InputStream;
import java.util.Properties;

public class readPropertiesUtils {
	
	private static Properties urlProps;
	 
    public static String getPropertiesForUrl(String property){
        Properties props = new Properties();
        try {
        	InputStream in = readPropertiesUtils.class.getResourceAsStream("/alipay.properties");
        	props.load(in);
        } catch (Exception e1) {
        // TODO Auto-generated catch block
            e1.printStackTrace();
        }
	           urlProps = props;
	           return urlProps.getProperty(property);
        }

}
