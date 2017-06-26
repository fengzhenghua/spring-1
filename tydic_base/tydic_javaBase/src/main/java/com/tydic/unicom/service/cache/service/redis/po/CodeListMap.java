package com.tydic.unicom.service.cache.service.redis.po;

import java.io.Serializable;

public class CodeListMap implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String map_list_id;
	private String type_code;
	private String code_id;
	private String map_id;
	private String map_name;
	private String system;
	private String province_code;
	private String area_code;
	
	public String getMap_list_id() {
		return map_list_id;
	}
	public void setMap_list_id(String map_list_id) {
		this.map_list_id = map_list_id;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}
	public String getMap_id() {
		return map_id;
	}
	public void setMap_id(String map_id) {
		this.map_id = map_id;
	}
	public String getMap_name() {
		return map_name;
	}
	public void setMap_name(String map_name) {
		this.map_name = map_name;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
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
