package com.tydic.unicom.uoc.service.code.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class CodeListTabFieldVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String real_engine;
	private String tab_name;
	private String field_name;
	private String type_code;
	private String province_code;
	private String area_code;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReal_engine() {
		return real_engine;
	}
	public void setReal_engine(String real_engine) {
		this.real_engine = real_engine;
	}
	public String getTab_name() {
		return tab_name;
	}
	public void setTab_name(String tab_name) {
		this.tab_name = tab_name;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
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
