package com.tydic.unicom.uif.service.vo;

import com.tydic.unicom.security.BaseObject;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午9:18:54
 * @ClassName CallAopVo
 * @Description 调用aop入参
 * @version V1.0
 */
public class CallAopVo extends BaseObject {
	
	private static final long serialVersionUID = -6905004158853011538L;
	
	private String appkey;
	
	private String url;
	
	private String msg;
	
	private String apptx;
	
	private String timestamp;
	
	private String bizkey;
	
	private String method;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getApptx() {
		return apptx;
	}
	
	public void setApptx(String apptx) {
		this.apptx = apptx;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getBizkey() {
		return bizkey;
	}
	
	public void setBizkey(String bizkey) {
		this.bizkey = bizkey;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getAppkey() {
		return appkey;
	}
	
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	
}
