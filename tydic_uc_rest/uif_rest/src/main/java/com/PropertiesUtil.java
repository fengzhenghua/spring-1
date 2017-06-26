package com;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties urlProps;

	public static String getPropertiesForUrl(String property) {
		Properties props = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getResourceAsStream("/appconfig.properties");
			props.load(in);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		urlProps = props;
		return urlProps.getProperty(property);
	}
}
