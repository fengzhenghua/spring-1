package com.tydic.unicom.uoc.service.common.interfaces;

import java.util.Map;

import com.tydic.unicom.webUtil.UocMessage;

public interface OperServDu {
	/**
	 * 获取登陆信息  17.1BASE0017
	 * @param jsession_id
	 * @return
	 */
	public UocMessage isLogin(String jsession_id);
	
	/**
	 * 公用参数拼接  BASE0023
	 * @param oper_info 操作员信息
	 * @return
	 */
	public String loginShareParam(Map<String,Object> oper_info,String jsession_id);
}
