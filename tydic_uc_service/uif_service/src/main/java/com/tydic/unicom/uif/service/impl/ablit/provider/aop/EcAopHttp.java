package com.tydic.unicom.uif.service.impl.ablit.provider.aop;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月18日 上午12:01:52
 * @ClassName EcAopHttp
 * @Description
 * @version V1.0
 */

public class EcAopHttp {
	
	private MultiThreadedHttpConnectionManager connectionManager;
	private HttpClient httpClient;
	
	public EcAopHttp() {
		HttpClientParams params = new HttpClientParams();
		params.setParameter("http.socket.timeout", Integer.valueOf(10000));
		params.setParameter("http.protocol.cookie-policy", "ignoreCookies");
		HttpConnectionManagerParams httpConnectionManagerParams = new HttpConnectionManagerParams();
		
		this.connectionManager = new MultiThreadedHttpConnectionManager();
		this.connectionManager.setParams(httpConnectionManagerParams);
		this.httpClient = new HttpClient(params, this.connectionManager);
	}
	
	public void shutdown() {
		this.connectionManager.shutdown();
	}
	
	public EcAopResult execute(String url, Map<String, String> values) {
		PostMethod method = null;
		
		EcAopResult ecAopResult = new EcAopResult();
		try {
			method = new PostMethod(url);
			method.getParams().setContentCharset("UTF-8");
			fulfillParamsters(method, values);
			int statusCode = this.httpClient.executeMethod(method);
			ecAopResult.setStatusCode(statusCode);
			ecAopResult.setTxid(method.getResponseHeader("txid").getValue());
			ecAopResult.setResponse(method.getResponseBodyAsString());
		} catch (Exception e) {
			ecAopResult.setException(e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return ecAopResult;
	}
	
	@SuppressWarnings("rawtypes")
	private void fulfillParamsters(PostMethod method, Map<String, String> values) {
		for (Map.Entry entry : values.entrySet())
			method.addParameter((String)entry.getKey(), (String)entry.getValue());
	}
	
	public void setTimeoutMillis(int timeoutMillis) {
		this.httpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(timeoutMillis));
	}
}
