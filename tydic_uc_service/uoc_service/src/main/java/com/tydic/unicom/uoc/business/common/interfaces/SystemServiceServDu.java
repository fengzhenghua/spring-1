package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface SystemServiceServDu {

	/**
	 * 退出
	 * */
	public UocMessage loginOutMethod(String jsessionId) throws Exception;
}
