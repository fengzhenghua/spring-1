package com.tydic.unicom.service.cache.service.redis.po;

import java.io.Serializable;

public class ProcModTacheService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String tache_code;
	private String service_name;
	private String service_desc;
	private String sort;
	private String input_str;
	private String province_code;
	private String area_code;
	private String queue_id;
	private String queue_name;
	private String oper_code;
	
	public String getOper_code() {
		return oper_code;
	}
	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getService_desc() {
		return service_desc;
	}
	public void setService_desc(String service_desc) {
		this.service_desc = service_desc;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getInput_str() {
		return input_str;
	}
	public void setInput_str(String input_str) {
		this.input_str = input_str;
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
	public String getQueue_id() {
		return queue_id;
	}
	public void setQueue_id(String queue_id) {
		this.queue_id = queue_id;
	}
	public String getQueue_name() {
		return queue_name;
	}
	public void setQueue_name(String queue_name) {
		this.queue_name = queue_name;
	}
}
