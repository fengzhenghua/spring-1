package com.tydic.unicom.uif.service.impl.ablit.provider.aop;

import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月18日 上午12:02:24
 * @ClassName ReqProcessor
 * @Description
 * @version V1.0
 */
public class ReqProcessor {
	
	@SuppressWarnings("rawtypes")
	public Map<String, String> process(Object in) {
		if (in == null)
			throw new RuntimeException("in parameter is null");
		
		Map temp = (in instanceof Map) ? (Map)in : obj2Map(in);
		
		EcAopMap result = new EcAopMap();
		for (Iterator i$ = temp.keySet().iterator(); i$.hasNext();) {
			Object obj = i$.next();
			result.put(obj + "", temp.get(obj));
		}
		
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	private Map<?, ?> obj2Map(Object in) {
		try {
			return (Map)JSON.toJSON(in);
		} catch (Exception e) {
			throw new RuntimeException("invalid in parameter type", e);
		}
	}
}
