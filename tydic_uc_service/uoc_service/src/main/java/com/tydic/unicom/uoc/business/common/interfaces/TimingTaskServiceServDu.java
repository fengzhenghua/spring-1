package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface TimingTaskServiceServDu {

	/**
	 * 不激活25天自动撤单
	 * */
	public UocMessage cancleOrder() throws Exception;
	
	/**
	 * 机卡比对
	 * */
	public UocMessage syncCardCompare() throws Exception;
}
