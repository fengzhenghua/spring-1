package com.tydic.unicom.apc.common.wxpay;

import java.io.InputStream;
import java.util.Properties;

public class WXPropertiesUtils {

	private static Properties urlProps;
	 
    public static String getPropertiesfhc(String property, String flag){
        Properties props = new Properties();
        if(urlProps == null){
	        try {
	        	InputStream in = WXPropertiesUtils.class.getResourceAsStream("/wxpay.properties");
	        	props.load(in);
	        } catch (Exception e1) {
	            e1.printStackTrace();
	            return null;
	        }
		    urlProps = props;
	    }
        
        if(flag != null && !"".equals(flag)){
        	property += "_"+flag;
        }
        
        return urlProps.getProperty(property);
    }
    
    public static String getPropertiesfhc(String property){
    	return getPropertiesfhc(property, null);
    }
}
