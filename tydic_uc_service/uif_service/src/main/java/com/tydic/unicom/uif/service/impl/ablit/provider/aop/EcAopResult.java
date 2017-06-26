package com.tydic.unicom.uif.service.impl.ablit.provider.aop;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月18日 上午12:02:13
 * @ClassName EcAopResult
 * @version V1.0
 */
public class EcAopResult {
	
	private Exception exception;
	private String response;
	private int statusCode;
	private String txid;
	private Object out;
	private EcAopMethod ecAopMethod;
	
	public String getResponse() {
		return this.response;
	}
	
	public boolean isSucc() {
		return (this.exception == null) && (this.statusCode == 200);
	}
	
	public void setException(Exception e) {
		this.exception = e;
	}
	
	public Exception getException() {
		return this.exception;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public void setTxid(String txid) {
		this.txid = txid;
	}
	
	public String getTxid() {
		return this.txid;
	}
	
	public Object getOut() {
		return this.out;
	}
	
	public void setOut(Object out) {
		this.out = out;
	}
	
	public void setEcAopMethod(EcAopMethod ecAopMethod) {
		this.ecAopMethod = ecAopMethod;
	}
	
	public EcAopMethod getEcAopMethod() {
		return this.ecAopMethod;
	}
	
	public int getStatusCode() {
		return this.statusCode;
	}
}
