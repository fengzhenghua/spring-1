package com.tydic.unicom.service.cache.service.redis.po;

import java.io.Serializable;

public class OrdModStateRule implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String province_code;
	private String area_code;
	private String oper_code_a;
	private String oper_name_a;
	private String state_code_a;
	private String state_name_a;
	private String oper_code_b;
	private String oper_name_b;
	private String state_code_b;
	private String state_name_b;
	private String rela_type;
	private String rela_desc;
	
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
	public String getOper_code_a() {
		return oper_code_a;
	}
	public void setOper_code_a(String oper_code_a) {
		this.oper_code_a = oper_code_a;
	}
	public String getOper_name_a() {
		return oper_name_a;
	}
	public void setOper_name_a(String oper_name_a) {
		this.oper_name_a = oper_name_a;
	}
	public String getState_code_a() {
		return state_code_a;
	}
	public void setState_code_a(String state_code_a) {
		this.state_code_a = state_code_a;
	}
	public String getState_name_a() {
		return state_name_a;
	}
	public void setState_name_a(String state_name_a) {
		this.state_name_a = state_name_a;
	}
	public String getOper_code_b() {
		return oper_code_b;
	}
	public void setOper_code_b(String oper_code_b) {
		this.oper_code_b = oper_code_b;
	}
	public String getOper_name_b() {
		return oper_name_b;
	}
	public void setOper_name_b(String oper_name_b) {
		this.oper_name_b = oper_name_b;
	}
	public String getState_code_b() {
		return state_code_b;
	}
	public void setState_code_b(String state_code_b) {
		this.state_code_b = state_code_b;
	}
	public String getState_name_b() {
		return state_name_b;
	}
	public void setState_name_b(String state_name_b) {
		this.state_name_b = state_name_b;
	}
	public String getRela_type() {
		return rela_type;
	}
	public void setRela_type(String rela_type) {
		this.rela_type = rela_type;
	}
	public String getRela_desc() {
		return rela_desc;
	}
	public void setRela_desc(String rela_desc) {
		this.rela_desc = rela_desc;
	}
}
