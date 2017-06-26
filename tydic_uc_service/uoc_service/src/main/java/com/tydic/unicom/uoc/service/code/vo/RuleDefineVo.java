package com.tydic.unicom.uoc.service.code.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class RuleDefineVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String rule_code;
	private String type_code;
	private String rule_name;
	private String province_code;
	private String area_code;
	private String state;
	private String rule_desc;
	private String get_type;
	private String get_method;
	private String create_staff;
	private String create_time;
	
	public String getRule_code() {
		return rule_code;
	}
	public void setRule_code(String rule_code) {
		this.rule_code = rule_code;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRule_desc() {
		return rule_desc;
	}
	public void setRule_desc(String rule_desc) {
		this.rule_desc = rule_desc;
	}
	public String getGet_type() {
		return get_type;
	}
	public void setGet_type(String get_type) {
		this.get_type = get_type;
	}
	public String getGet_method() {
		return get_method;
	}
	public void setGet_method(String get_method) {
		this.get_method = get_method;
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
}
