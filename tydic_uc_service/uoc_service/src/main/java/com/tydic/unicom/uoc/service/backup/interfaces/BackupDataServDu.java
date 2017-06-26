package com.tydic.unicom.uoc.service.backup.interfaces;

import java.util.Map;

import com.tydic.unicom.webUtil.UocMessage;

public interface BackupDataServDu {
	
	public UocMessage queryRawData(String order_no,String oper_type)  throws Exception;
	
	public UocMessage insertRawData(Map<String, Object> dataMap,String oper_type)  throws Exception;
	
	public UocMessage deleteRawData(String order_no,String oper_type,Map<String, Object> dataMap)  throws Exception;

}
