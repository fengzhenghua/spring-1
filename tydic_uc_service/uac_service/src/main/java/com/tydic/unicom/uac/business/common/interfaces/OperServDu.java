package com.tydic.unicom.uac.business.common.interfaces;

import com.tydic.unicom.uac.business.common.vo.InfoOperVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface OperServDu {

	/**
	 * 从redis获取用户信息
	 * */
	public UocMessage getOperInfoFromRedis(String jsessionId) throws Exception;
	
	/**
	 * 根据工号获取工号信息
	 * */
	public InfoOperVo queryInfoOperByLoginCode(String operNo) throws Exception;
	/**
	 * 验证是否登录，调用UACBS0001-获取登录信息
	 */
	public UocMessage isLogin(String jsessionId) throws Exception;
}
