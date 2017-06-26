package com.tydic.unicom.uoc.service.mod.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class OrdModOperSplitRuleVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String province_code;
	private String area_code;
	private String source_oper_code;
	private String source_oper_name;
	private String source_system_code;
	private String target_oper_code;
	private String target_oper_name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getSource_oper_code() {
		return source_oper_code;
	}
	public void setSource_oper_code(String source_oper_code) {
		this.source_oper_code = source_oper_code;
	}
	public String getSource_oper_name() {
		return source_oper_name;
	}
	public void setSource_oper_name(String source_oper_name) {
		this.source_oper_name = source_oper_name;
	}
	public String getSource_system_code() {
		return source_system_code;
	}
	public void setSource_system_code(String source_system_code) {
		this.source_system_code = source_system_code;
	}
	public String getTarget_oper_code() {
		return target_oper_code;
	}
	public void setTarget_oper_code(String target_oper_code) {
		this.target_oper_code = target_oper_code;
	}
	public String getTarget_oper_name() {
		return target_oper_name;
	}
	public void setTarget_oper_name(String target_oper_name) {
		this.target_oper_name = target_oper_name;
	}
}
