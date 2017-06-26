package com.tydic.unicom.uif.service.interfaces;

import java.util.Map;

public interface UifJsonToBeanServDu {
	
public Map<String, Object> jsonToBean(String json,Class cla ) throws Exception;
	
	public Map<String, Object> jsonToMap(String json) throws Exception;
	
	public Map<String, String> jsonToMapStr(String json) throws Exception;
	
	public String mapToJson(Map<String, Object> map);

}
