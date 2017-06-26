package com.tydic.unicom.apc.base.pub.interfaces;

import java.util.Map;

public interface BssService {
	
	/**
	 * @author HXF
	 * @desc 使用axis2工具调用webService服务,使用xml格式报文进行调用,返回xml格式报文
	 * @param URL(String) WebService服务地址
	 * @param actionName(String) action名称
	 * @param xmlStr(String) xml格式字符串
	 * @return returnXmlStr(String) xml格式字符串
	 * */
	public String callWebServiceByAxis2(String URL, String actionName, String xmlStr);
	
	/**
	 * 
	 * @param URL
	 * @param actionName
	 * @param xmlStr
	 * @param maxLength  最大显示日志长度
	 * @return
	 */
	public String callWebServiceByAxis2(String URL, String actionName, String xmlStr, int maxLogLength);
	
	/**
	 * @author HXF
	 * @desc 使用HttpClient工具调用http服务,使用xml格式报文进行调用,返回xml格式报文
	 * @param URL(String) http服务地址
	 * @param xmlStr(String) 入参报文,xml格式字符串
	 * @return returnXmlStr(String) 出参报文,xml格式字符串
	 * */
	public String callHttpServiceByHttpClient(String URL, String xmlStr, Map<String, Object> inMap);
	
	public String contactHttpXmlStr(String xmlStr, Map<String, Object> inMap);
	
	public String callHttpServiceByHttpServlet(String url, String query) throws Exception;
	
	/**
	 * @author hn
	 * @desc 使用axis2工具调用webService服务,使用xml格式报文进行调用,返回xml格式报文
	 * @param URL(String) WebService服务地址
	 * @param actionName(String) action名称
	 * @param xmlStr(String) xml格式字符串
	 * @return returnXmlStr(String) xml格式字符串
	 * */
	public String hnCallWebServiceByAxis2(String URL, String actionName, String xmlStr);
	
}
