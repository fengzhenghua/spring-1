package com.tydic.unicom.uoc.base.uoccode.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class OrdModAttrDefinePo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String attr_code;
	private String mod_code;
	private String attr_name;
	private String attr_type;
	private String attr_desc;
	private String is_allow_null;
	private String is_default_value;
	private String get_default_type;
	private String default_value;
	

	public String getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(String attr_type) {
		this.attr_type = attr_type;
	}
	public String getGet_default_type() {
		return get_default_type;
	}
	public void setGet_default_type(String get_default_type) {
		this.get_default_type = get_default_type;
	}
	public String getDefault_value() {
		return default_value;
	}
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public String getAttr_desc() {
		return attr_desc;
	}
	public void setAttr_desc(String attr_desc) {
		this.attr_desc = attr_desc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIs_allow_null() {
		return is_allow_null;
	}
	public void setIs_allow_null(String is_allow_null) {
		this.is_allow_null = is_allow_null;
	}
	public String getIs_default_value() {
		return is_default_value;
	}
	public void setIs_default_value(String is_default_value) {
		this.is_default_value = is_default_value;
	}
	
}
