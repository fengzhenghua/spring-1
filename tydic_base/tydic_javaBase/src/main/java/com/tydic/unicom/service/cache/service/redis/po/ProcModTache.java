package com.tydic.unicom.service.cache.service.redis.po;

import java.io.Serializable;

public class ProcModTache implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tache_code;
	private String tache_name;
	private String tache_desc;
	private String used_time_len;
	private String tache_type;
	private String is_need_check;
	private String oper_type;
	private String province_code;
	private String area_code;
	
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public String getTache_name() {
		return tache_name;
	}
	public void setTache_name(String tache_name) {
		this.tache_name = tache_name;
	}
	public String getTache_desc() {
		return tache_desc;
	}
	public void setTache_desc(String tache_desc) {
		this.tache_desc = tache_desc;
	}
	public String getUsed_time_len() {
		return used_time_len;
	}
	public void setUsed_time_len(String used_time_len) {
		this.used_time_len = used_time_len;
	}
	public String getTache_type() {
		return tache_type;
	}
	public void setTache_type(String tache_type) {
		this.tache_type = tache_type;
	}
	public String getIs_need_check() {
		return is_need_check;
	}
	public void setIs_need_check(String is_need_check) {
		this.is_need_check = is_need_check;
	}
	public String getOper_type() {
		return oper_type;
	}
	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
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
