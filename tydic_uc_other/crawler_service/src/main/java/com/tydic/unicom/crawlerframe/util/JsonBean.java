package com.tydic.unicom.crawlerframe.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonBean {

	Logger logger = Logger.getLogger(JsonBean.class);

	public Map<String, Object> jsonToMap(String jsonStr) throws Exception {
		// 最外层解析
		if (jsonStr != null && jsonStr.startsWith("{") && jsonStr.endsWith("}")) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject json = JSONObject.fromObject(jsonStr);
			for (Object k : json.keySet()) {
				Object v = json.get(k);
				// 如果内层还是数组的话，继续解析
				if (v instanceof JSONArray) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Iterator<JSONObject> it = ((JSONArray) v).iterator();
					while (it.hasNext()) {
						JSONObject json2 = it.next();
						list.add(jsonToMap(json2.toString()));
					}
					map.put(k.toString(), list);
				} else {
					Map<String, Object> m = jsonToMap(v.toString());
					if (m == null)
						map.put(k.toString(), v);
					else
						map.put(k.toString(), m);
				}
			}
			return map;
		} else {
			return null;
		}
	}

	public Map<String, String> jsonToMapStr(String jsonStr) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 一层解析
		if (jsonStr != null && jsonStr.startsWith("{") && jsonStr.endsWith("}")) {
			JSONObject json = JSONObject.fromObject(jsonStr);
			for (Object k : json.keySet()) {
				Object v = json.get(k);
				map.put(k.toString(), v.toString());

			}
			return map;
		} else {
			return map;
		}
	}

	/**
	 * 全域查找，找到就返回
	 * FFF.DDD
	 * @return
	 */
	public String jsonfindall(JSONObject jsoninfo,String pagename){
		String[] sp = pagename.split(".");
		if(sp.length==1){
			return (String) jsoninfo.get("sp");
		}
		jsoninfo.get("sp");
		
		return "";
	}
	
	
	public String mapToJson(Map<String, Object> map) {
		JSONObject jsonObject1 = JSONObject.fromObject(map);
		return jsonObject1.toString();
	}

}