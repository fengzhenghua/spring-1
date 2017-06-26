package com.tydic.unicom.uoc.base.uoccode.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class ProcModAppPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String proc_mod_code;
	private String proc_mod_name;
	private String province_code;
	private String area_code;
	private String oper_code;
	private String used_time_len;
	
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
	public String getProc_mod_name() {
		return proc_mod_name;
	}
	public void setProc_mod_name(String proc_mod_name) {
		this.proc_mod_name = proc_mod_name;
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
	public String getOper_code() {
		return oper_code;
	}
	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}
	public String getUsed_time_len() {
		return used_time_len;
	}
	public void setUsed_time_len(String used_time_len) {
		this.used_time_len = used_time_len;
	}
	
}
