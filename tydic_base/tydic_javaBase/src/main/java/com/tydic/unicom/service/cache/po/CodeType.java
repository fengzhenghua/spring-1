package com.tydic.unicom.service.cache.po;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

public class CodeType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String	key_id;
	private String	type_code;
	private String	type_name;
	
	private List<CodeList>	codeList;
	
	@Id
	public String getKey_id() {
		return key_id;
	}
	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	
	public List<CodeList> getCodeList() {
		return codeList;
	}
	
	public void setCodeList(List<CodeList> codeList) {
		this.codeList = codeList;
	}
	
	
}
