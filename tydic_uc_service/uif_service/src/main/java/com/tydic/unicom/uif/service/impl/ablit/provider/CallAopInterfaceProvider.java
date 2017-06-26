package com.tydic.unicom.uif.service.impl.ablit.provider;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tydic.unicom.uif.service.impl.ablit.provider.aop.EcAopClient;
import com.tydic.unicom.uif.service.impl.ablit.provider.aop.EcAopMethod;
import com.tydic.unicom.uif.service.impl.ablit.provider.aop.EcAopResult;
import com.tydic.unicom.uif.service.interfaces.IAbilitProvider;
import com.tydic.unicom.uif.service.vo.CallAopVo;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午6:48:21
 * @ClassName CallAopInterfaceProvider
 * @Description 调用AOP接口,不通过能力平台
 * @version V1.0
 */
public class CallAopInterfaceProvider implements IAbilitProvider<CallAopVo> {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final int TIME_OUT = 60000;
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public String callAblit(CallAopVo callAopVo) {
		EcAopClient ecAopClient = new EcAopClient(callAopVo.getUrl(), callAopVo.getAppkey());
		// 准备平台参数参数
		Map inputMap = new HashMap();
		inputMap.put("msg", callAopVo.getMsg());
		inputMap.put("apptx", callAopVo.getApptx());
		inputMap.put("timestamp", callAopVo.getTimestamp());
		inputMap.put("bizkey", callAopVo.getBizkey());
		
		// 设置超时时间
		ecAopClient.setTimeoutMillis(TIME_OUT);
		
		// 执行调用
		EcAopMethod ecAopMethod = ecAopClient.createEcAopMethod(callAopVo.getMethod(), Map.class);
		EcAopResult result = ecAopMethod.exec(inputMap);
		logger.info(result.getOut().toString());
		return result.getResponse();
	}
}
