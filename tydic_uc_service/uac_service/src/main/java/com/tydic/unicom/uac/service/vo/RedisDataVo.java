package com.tydic.unicom.uac.service.vo;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Id;

public class RedisDataVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private Object obj;
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
