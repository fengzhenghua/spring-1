package com.tydic.unicom.uif.service.impl.ablit.provider.aop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月18日 上午12:02:04
 * @ClassName EcAopMethod
 * @Description
 * @version V1.0
 */
public class EcAopMethod {
	
	private EcAopClient ecAopClient;
	private String method;
	private Class<?> outClazz;
	private ReqProcessor reqProcessor = new ReqProcessor();
	private RspProcessor rspProcessor = new RspProcessor();
	
	public EcAopMethod(EcAopClient ecAopClient, String method, Class<?> outClazz) {
		this.ecAopClient = ecAopClient;
		this.method = method;
		this.outClazz = outClazz;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public EcAopResult exec(Object in) {
		EcAopResult ecAopResult = null;
		TreeMap treeMap = new TreeMap();
		try {
			Map inParams = this.reqProcessor.process(in);
			treeMap.putAll(inParams);
			treeMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			treeMap.put("method", this.method);
			
			ecAopResult = this.ecAopClient.exec(treeMap);
			
			this.rspProcessor.process(ecAopResult, this.outClazz);
			
			ecAopResult.setEcAopMethod(this);
			
			return ecAopResult;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public EcAopClient getEcAopClient() {
		return this.ecAopClient;
	}
	
	public String getMethod() {
		return this.method;
	}
	
	public Class<?> getOutClazz() {
		return this.outClazz;
	}
}
