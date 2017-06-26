package com.tydic.unicom.service.cache.service.redis.po;

import java.io.Serializable;

public class RuleAttrDefine implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String attr_code;
	private String attr_name;
	private String rule_code;
	private String province_code;
	private String area_code;
	private String attr_desc;
	private String attr_type;
	private String is_golbal_attr;
	private String default_value;
	private String get_type;
	private String get_method;
	
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public String getRule_code() {
		return rule_code;
	}
	public void setRule_code(String rule_code) {
		this.rule_code = rule_code;
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
	public String getAttr_desc() {
		return attr_desc;
	}
	public void setAttr_desc(String attr_desc) {
		this.attr_desc = attr_desc;
	}
	public String getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(String attr_type) {
		this.attr_type = attr_type;
	}
	public String getIs_golbal_attr() {
		return is_golbal_attr;
	}
	public void setIs_golbal_attr(String is_golbal_attr) {
		this.is_golbal_attr = is_golbal_attr;
	}
	public String getDefault_value() {
		return default_value;
	}
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
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
}
