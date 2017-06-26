package com.tydic.unicom.uoc.service.code.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ProcTacheRetVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String id;
	private String tache_code;
	private String oper_code;
	private String accept_system;
	private String call_url;
	private String call_ord_mod;
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

	public String getOper_code() {
		return oper_code;
	}

	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}

	public String getAccept_system() {
		return accept_system;
	}

	public void setAccept_system(String accept_system) {
		this.accept_system = accept_system;
	}

	public String getCall_url() {
		return call_url;
	}

	public void setCall_url(String call_url) {
		this.call_url = call_url;
	}

	public String getCall_ord_mod() {
		return call_ord_mod;
	}

	public void setCall_ord_mod(String call_ord_mod) {
		this.call_ord_mod = call_ord_mod;
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
