package com.tydic.unicom.apc.business.pub.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface OppoExamineServiceServDu {

	/**
	 * 获取微信配置参数
	 * */
	public UocMessage getWXJSConfig(String url, String mcht_flag) throws Exception;
	
	/**
	 * 下载微信文件
	 * */
	public UocMessage downloadWXFile(String oper_no,String order_id,String mcht_flag,String server_id,String validate_data,String video_server_id,String flag) throws Exception;
	
	/**
	 * 获取唇语信息
	 * */
	public UocMessage getLipLanguageInfo(String mcht_flag,String url) throws Exception;
	
	/**
	 * 活体审核
	 * */
	public UocMessage oppoLivingExamineSubmit(String order_id,String validate_data,String video,String video_file_name,String video_server_id) throws Exception;
}
