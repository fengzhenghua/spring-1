package com.tydic.unicom.uif.service.impl.ablit.provider.aop;

import java.util.TreeMap;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午11:01:55
 * @ClassName EcAopClient
 * @Description
 * @version V1.0
 */
public class EcAopClient {
	
	private String url;
	private String appkey;
	private EcAopHttp aopHttp = new EcAopHttp();
	
	public EcAopClient(String url, String appkey) {
		this.url = url;
		this.appkey = appkey;
	}
	
	public EcAopMethod createEcAopMethod(String method, Class<?> outClazz) {
		return new EcAopMethod(this, method, outClazz);
	}
	
	public EcAopResult exec(TreeMap<String, String> treeMap) {
		treeMap.put("appkey", this.appkey);
		
		return this.aopHttp.execute(this.url, treeMap);
	}
	
	public void setTimeoutMillis(int timeoutMillis) {
		this.aopHttp.setTimeoutMillis(timeoutMillis);
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public String getAppkey() {
		return this.appkey;
	}
}
