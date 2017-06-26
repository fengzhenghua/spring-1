package com.tydic.unicom.service.cache.po;

import java.io.Serializable;

import javax.persistence.Id;

public class OperInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String	key_id;
	private String	oper_id;
	private String	jsessionid;
	
	
	
	@Id
	public String getKey_id() {
		return key_id;
	}
	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getJsessionid() {
		return jsessionid;
	}
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}
	
	
	
}
