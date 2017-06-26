package com.tydic.unicom.uoc.base.uoccode.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class OrdModDefinePo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String mod_code;
	private String mod_name;
	private String mod_desc;
	private String province_code;
	private String area_code;
	private String state;
	private String json_module;
	private String create_staff;
	private String create_time;
	private String interface_param_json;
	private String interface_type;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getJson_module() {
		return json_module;
	}
	public void setJson_module(String json_module) {
		this.json_module = json_module;
	}
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public String getMod_name() {
		return mod_name;
	}
	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
	}
	public String getMod_desc() {
		return mod_desc;
	}
	public void setMod_desc(String mod_desc) {
		this.mod_desc = mod_desc;
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
	public String getCreate_staff() {
		return create_staff;
	}
	public void setCreate_staff(String create_staff) {
		this.create_staff = create_staff;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getInterface_param_json() {
		return interface_param_json;
	}
	public void setInterface_param_json(String interface_param_json) {
		this.interface_param_json = interface_param_json;
	}
	public String getInterface_type() {
		return interface_type;
	}
	public void setInterface_type(String interface_type) {
		this.interface_type = interface_type;
	}
	
	
}
