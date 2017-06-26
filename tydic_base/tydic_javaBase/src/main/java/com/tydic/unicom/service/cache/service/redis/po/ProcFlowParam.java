package com.tydic.unicom.service.cache.service.redis.po;

import java.io.Serializable;

public class ProcFlowParam implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String proc_mod_code;
	private String province_code;
	private String area_code;
	private String tache_code;
	private String cond_param;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProc_mod_code() {
		return proc_mod_code;
	}
	public void setProc_mod_code(String proc_mod_code) {
		this.proc_mod_code = proc_mod_code;
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
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public String getCond_param() {
		return cond_param;
	}
	public void setCond_param(String cond_param) {
		this.cond_param = cond_param;
	}
}
