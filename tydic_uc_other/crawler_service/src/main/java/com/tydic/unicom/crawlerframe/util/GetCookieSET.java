package com.tydic.unicom.crawlerframe.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class GetCookieSET {
	// 从响应信息中获取cookie
	public String getCookiefromResponse(HttpResponse httpResponse) {
		Header headers[] = httpResponse.getHeaders("Set-Cookie");
		if (headers == null || headers.length == 0) {
			return null;
		}
		String cookie = "";
		for (int i = 0; i < headers.length; i++) {
			cookie += headers[i].getValue();
			if (i != headers.length - 1) {
				cookie += ";";
			}
		}
		return getCookiefromResponse(cookie);
	}
	
	public static String getCookiefromResponse(String cookie) {
		Map<String, String> cookieMap = new HashMap<String, String>(64);
		
		String cookies[] = cookie.split(";");
		for (String c : cookies) {
			c = c.trim();
			System.out.println(c);
			
			String key = "";
			String val = "";
			if(c.indexOf("=")>0){
				key = c.substring(0,c.indexOf("="));
				if(key.equals("path") ||key.equals("expires")||key.equals("domain"))
					continue;
				val = c.substring(c.indexOf("=")+1);
				cookieMap.put(key,val);
			}
		}
		String cookiesTmp = "";
		
		for (String key : cookieMap.keySet()) {
			cookiesTmp += key + "=" + cookieMap.get(key) + ";";
		}
		return cookiesTmp;
	}
}
