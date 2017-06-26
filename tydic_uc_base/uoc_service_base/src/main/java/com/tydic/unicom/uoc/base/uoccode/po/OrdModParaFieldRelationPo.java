package com.tydic.unicom.uoc.base.uoccode.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class OrdModParaFieldRelationPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String attr_code;
	private String mod_code;
	private String table_name;
	private String field_name;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
}
