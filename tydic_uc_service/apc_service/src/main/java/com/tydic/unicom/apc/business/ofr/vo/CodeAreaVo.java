package com.tydic.unicom.apc.business.ofr.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class CodeAreaVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String area_code;
	private String parent_area_code;
	private String area_name;
	private String province_code;
	
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getParent_area_code() {
		return parent_area_code;
	}
	public void setParent_area_code(String parent_area_code) {
		this.parent_area_code = parent_area_code;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
}
