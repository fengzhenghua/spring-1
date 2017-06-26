package com.tydic.unicom.uif.service.vo;

import com.tydic.unicom.security.BaseObject;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午11:32:04
 * @ClassName TapServiceVo
 * @Description 能力平台参数
 * @version V1.0
 */
public class TapServiceVo extends BaseObject {
	
	private static final long serialVersionUID = 4046751298092596447L;
	
	private String token;
	
	private String commUrl;
	
	private String appKey;
	
	private String secret;
	
	private Integer connectTimeout;
	
	private Integer readTimeout;
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getCommUrl() {
		return commUrl;
	}
	
	public void setCommUrl(String commUrl) {
		this.commUrl = commUrl;
	}
	
	public String getAppKey() {
		return appKey;
	}
	
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	public String getSecret() {
		return secret;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public Integer getConnectTimeout() {
		return connectTimeout;
	}
	
	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
	public Integer getReadTimeout() {
		return readTimeout;
	}
	
	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}
	
}
