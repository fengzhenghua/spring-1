package com.tydic.unicom.uoc.base.uoccode.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class CommDFUserExtinfo extends BasePo{
	
	private static final long serialVersionUID = 1L;
	
	private String month_id;
	private String day_id;
	private String service_num;
	private String subs_id;
	private String attr_code;
	private String attr_value_code;
	private String status;
	private String effect_time;
	private String expire_time;
	private String insert_date;
	
	public String getMonth_id() {
		return month_id;
	}
	public void setMonth_id(String month_id) {
		this.month_id = month_id;
	}
	public String getDay_id() {
		return day_id;
	}
	public void setDay_id(String day_id) {
		this.day_id = day_id;
	}
	public String getService_num() {
		return service_num;
	}
	public void setService_num(String service_num) {
		this.service_num = service_num;
	}
	public String getSubs_id() {
		return subs_id;
	}
	public void setSubs_id(String subs_id) {
		this.subs_id = subs_id;
	}
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	public String getAttr_value_code() {
		return attr_value_code;
	}
	public void setAttr_value_code(String attr_value_code) {
		this.attr_value_code = attr_value_code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEffect_time() {
		return effect_time;
	}
	public void setEffect_time(String effect_time) {
		this.effect_time = effect_time;
	}
	public String getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}
	public String getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}
}
