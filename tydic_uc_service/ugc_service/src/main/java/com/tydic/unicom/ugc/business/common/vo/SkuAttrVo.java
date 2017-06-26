package com.tydic.unicom.ugc.business.common.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class SkuAttrVo extends BaseVo{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String sku_id;
	private String attr_type;
	private String attr_id;
	private String attr_value;
	private String attr_name;
	
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public String getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(String attr_type) {
		this.attr_type = attr_type;
	}
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	public String getAttr_value() {
		return attr_value;
	}
	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}
	
}

