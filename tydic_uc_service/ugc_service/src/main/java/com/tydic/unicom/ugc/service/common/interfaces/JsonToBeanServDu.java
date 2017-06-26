package com.tydic.unicom.ugc.service.common.interfaces;

import java.util.Map;

public interface JsonToBeanServDu {

    public Map<String, Object> jsonToBean(String json,Class cla ) throws Exception;

	public Map<String, Object> jsonToMap(String json) throws Exception;

	public Map<String, String> jsonToMapStr(String json) throws Exception;

	public String mapToJson(Map<String, Object> map);

	public Object jsonInfoToBean(String json_info, Class<?> clazz);
}
