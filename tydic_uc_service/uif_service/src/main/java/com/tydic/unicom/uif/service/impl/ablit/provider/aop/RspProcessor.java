package com.tydic.unicom.uif.service.impl.ablit.provider.aop;

import com.alibaba.fastjson.JSON;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月18日 上午12:02:36
 * @ClassName RspProcessor
 * @Description
 * @version V1.0
 */
public class RspProcessor {
	
	public <T> void process(EcAopResult result, Class<T> outClazz) {
		if ((!result.isSucc()) || (result.getResponse() == null) || (outClazz == null)) {
			return;
		}
		if (outClazz == String.class)
			result.setOut(result.getResponse());
		
		Object out = JSON.toJavaObject(JSON.parseObject(result.getResponse()), outClazz);
		result.setOut(out);
	}
}
