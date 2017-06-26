package com.tydic.unicom.crawler.business.vo;

import java.io.Serializable;
import java.util.HashMap;

import com.tydic.unicom.crawler.common.BaseVoPo;

public class BusinessResParamVo extends BaseVoPo{

	private static final long serialVersionUID = 1L;
	
	private String user;
	private String password;
	private String type;
	private String jsession_id;
	private String safecode;
	private String userid;
	private String jsonInfo;
	private String order_id;

	private String other;
	
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	//检查信息使用
	private String checklist;
	
	
	public String getJsonInfo() {
		return jsonInfo;
	}
	public void setJsonInfo(String jsonInfo) {
		this.jsonInfo = jsonInfo;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getJsession_id() {
		return jsession_id;
	}
	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}
	public String getSafecode() {
		return safecode;
	}
	public void setSafecode(String safecode) {
		this.safecode = safecode;
	}
	public String getChecklist() {
		return checklist;
	}
	public void setChecklist(String checklist) {
		this.checklist = checklist;
	}
		
	
	
}
