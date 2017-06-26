package com.tydic.unicom.service.cache.service.redis.po;

import java.io.Serializable;

public class ProcModTacheBtn implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String tache_code;
	private String btn_code;
	private String btn_desc;
	private String sort;
	private String call_url;
	private String province_code;
	private String area_code;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public String getBtn_code() {
		return btn_code;
	}
	public void setBtn_code(String btn_code) {
		this.btn_code = btn_code;
	}
	public String getBtn_desc() {
		return btn_desc;
	}
	public void setBtn_desc(String btn_desc) {
		this.btn_desc = btn_desc;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getCall_url() {
		return call_url;
	}
	public void setCall_url(String call_url) {
		this.call_url = call_url;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
}
