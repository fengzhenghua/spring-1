package com.tydic.unicom.apc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

/**
 * APC系统codelist服务
 * @author ZhangCheng
 * @date 2017-04-19
 */
public interface CodeListServDu {
	
	public UocMessage queryCodeListByTypeCode(String jsession_id,String typeCode) throws Exception;
	
}
