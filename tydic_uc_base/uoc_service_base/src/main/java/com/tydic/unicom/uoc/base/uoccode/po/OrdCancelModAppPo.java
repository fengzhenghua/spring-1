package com.tydic.unicom.uoc.base.uoccode.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class OrdCancelModAppPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String mod_code;
	private String province_code;
	private String area_code;
	private String used_system;
	private String used_service;
	private String oper_code;
	private String tache_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
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
	public String getUsed_system() {
		return used_system;
	}
	public void setUsed_system(String used_system) {
		this.used_system = used_system;
	}
	public String getUsed_service() {
		return used_service;
	}
	public void setUsed_service(String used_service) {
		this.used_service = used_service;
	}
	public String getOper_code() {
		return oper_code;
	}
	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}
	public String getTache_id() {
		return tache_id;
	}
	public void setTache_id(String tache_id) {
		this.tache_id = tache_id;
	}

	
}
