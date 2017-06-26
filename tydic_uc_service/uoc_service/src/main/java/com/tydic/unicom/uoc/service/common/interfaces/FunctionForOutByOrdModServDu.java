package com.tydic.unicom.uoc.service.common.interfaces;

public interface FunctionForOutByOrdModServDu {

	public String getParam(String param_json,String paramName);


	/**
	 * 返回当前系统时间字符串，不同type返回格式如下：
	 *	type=1时 返回yyyymmddhh24miss;
	 *	type=2时 返回yyyy-mm-dd hh-24mi-ss;
	 *	type=3时 返回yyyymmdd;
	 *	type=4时 返回yyyy-mm-dd;
	 * @param type
	 * @return
	 */
	public String getSysdate(String type);
	
	public  String getServOrderNoByOperCode(String operCode,String order_no) throws Exception;
	
	public  String getActivityInfo (String orderNo)  throws Exception;
	
	public  String transCertTypeCq (String orderNo)  throws Exception;
	
	public String getFeeInfo(String orderNo) throws Exception;
	
	public String getProductInfo(String orderNo) throws Exception;
}
