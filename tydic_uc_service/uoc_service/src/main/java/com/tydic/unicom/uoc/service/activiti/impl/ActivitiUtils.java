package com.tydic.unicom.uoc.service.activiti.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;


/**
 * 
 * @author zhangrong
 *
 * 2016年10月9日
 */
public class ActivitiUtils {

	/**
	 * 将请求json信息专为json字符串
	 * @param request
	 * @return
	 */
	public static String getRequsetBody(HttpServletRequest request){
		byte[] byteArray;
		try {
			byteArray = IOUtils.toByteArray(request.getInputStream());
			String charset = request.getCharacterEncoding()!=null ? request.getCharacterEncoding() : "UTF-8";
			return new String(byteArray, charset);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 将json字符串转为json实体
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject StringToJson(String jsonStr){
		JSONObject jsonObj = null;
		try {
			jsonObj = JSONObject.fromObject(jsonStr);
		} catch (Exception e) {
		}
		return jsonObj;
	}

	
	/**
	 * 获取GET方法的参数列表
	 * @param request
	 * @return
	 */
	public static Map<String,String> getHttpGetBody(HttpServletRequest request ){
		
		Map<String,String> httpGetParams=new HashMap<String,String>();
		
		@SuppressWarnings("rawtypes")
		Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String paramName = (String) en.nextElement();
            httpGetParams.put(paramName, request.getParameter(paramName));
        }
		
		return httpGetParams;
	}
	
	/**
	 * 返回信息
	 * @param response
	 * @param res
	 */
	public static void writeData(HttpServletResponse response, Object res) 
	{
		response.setContentType("text/json"); 
	    /*设置字符集为'UTF-8'*/
	    response.setCharacterEncoding("UTF-8"); 
		try
		{
			if(res instanceof String)
			{
				response.getWriter().write((String)res);
			}
			else
			{
				response.getWriter().write(res.toString());
			}
		} catch (Exception e) 
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 读取配置文件
	 * @param file
	 * @return
	 */
	public static Properties getProperties(String file){
		ActivitiUtils pt = new ActivitiUtils(); 
		
		InputStream inputStream = pt.getClass().getClassLoader().getResourceAsStream(file);  
        Properties properties = new Properties();  
        try{  
            properties.load(inputStream);  
        }catch (IOException ioE){  
            ioE.printStackTrace();  
        }finally{  
            try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }  
        
        return properties;
	}
}
