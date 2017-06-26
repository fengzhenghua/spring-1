package com.tydic.unicom.uoc.service.mod.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class OutOrdModeVo extends BaseVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String attr_code; //参数编码
	private String table_name; //表名
	private String field_name; //表中字段名称
	private String field_value;//字段值
	private String curr_seq;   //本级序列
	private String upper_seq;  //上级序列
	private String attr_type; //参数类型

	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
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
	public String getField_value() {
		return field_value;
	}
	public void setField_value(String field_value) {
		this.field_value = field_value;
	}
	public String getCurr_seq() {
		return curr_seq;
	}
	public void setCurr_seq(String curr_seq) {
		this.curr_seq = curr_seq;
	}
	public String getUpper_seq() {
		return upper_seq;
	}
	public void setUpper_seq(String upper_seq) {
		this.upper_seq = upper_seq;
	}
	public String getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(String attr_type) {
		this.attr_type = attr_type;
	}	
	

}
