package com.tydic.unicom.apc.service.pub.interfaces;

import java.util.Map;

import com.tydic.unicom.apc.business.pub.vo.NumberChgRequestVo;
import com.tydic.unicom.apc.business.pub.vo.NumberQryRequestVo;

public interface OppoNumberServDu {

	/**
	 * 查号
	 * */
	public Map<String ,Object> numberQry(NumberQryRequestVo reqVo) throws Exception;
	
	/**
	 *号码预占
	 * */
	public Map<String ,Object> numberChg(NumberChgRequestVo reqVo) throws Exception;
}
