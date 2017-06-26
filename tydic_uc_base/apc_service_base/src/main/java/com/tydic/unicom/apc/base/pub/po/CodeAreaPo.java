package com.tydic.unicom.apc.base.pub.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class CodeAreaPo extends BasePo {

	private static final long serialVersionUID = 5141023392877329109L;

	
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
