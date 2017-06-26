package com.tydic.unicom.uoc.service.common.vo;

import java.util.Map;

import com.tydic.unicom.webUtil.BaseVo;

public class RedisVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private Map<String,Object> map;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
}
