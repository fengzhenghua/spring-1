package com.tydic.unicom.uoc.service.mod.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class OrdModCheckRuleVo extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String mod_code;
	private String mod_check_method;
	private String mod_check_desc;
	private String input_json;
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
	public String getMod_check_method() {
		return mod_check_method;
	}
	public void setMod_check_method(String mod_check_method) {
		this.mod_check_method = mod_check_method;
	}
	public String getMod_check_desc() {
		return mod_check_desc;
	}
	public void setMod_check_desc(String mod_check_desc) {
		this.mod_check_desc = mod_check_desc;
	}
	public String getInput_json() {
		return input_json;
	}
	public void setInput_json(String input_json) {
		this.input_json = input_json;
	}
	
}
