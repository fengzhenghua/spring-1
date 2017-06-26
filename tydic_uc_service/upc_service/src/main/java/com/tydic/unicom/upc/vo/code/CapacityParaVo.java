package com.tydic.unicom.upc.vo.code;

import com.tydic.unicom.webUtil.BaseVo;

public class CapacityParaVo extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7729694818471932936L;
	private String busi_id;// 系统id
	private String aopname; // 调用接口的service_name
	private String fromat; // 向能力平台传输的格式
	private String common_url; // 能力平台的地址
	private String secret; // 能力 平台校验
	private String token;  // 能力平台校验
	private String grand;  //调用能力平台的接口
	private String app_key;
		
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public String getBusi_id() {
		return busi_id;
	}
	public void setBusi_id(String busi_id) {
		this.busi_id = busi_id;
	}
	public String getAopname() {
		return aopname;
	}
	public void setAopname(String aopname) {
		this.aopname = aopname;
	}
	public String getFromat() {
		return fromat;
	}
	public void setFromat(String fromat) {
		this.fromat = fromat;
	}
	public String getCommon_url() {
		return common_url;
	}
	public void setCommon_url(String common_url) {
		this.common_url = common_url;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getGrand() {
		return grand;
	}
	public void setGrand(String grand) {
		this.grand = grand;
	}
}
