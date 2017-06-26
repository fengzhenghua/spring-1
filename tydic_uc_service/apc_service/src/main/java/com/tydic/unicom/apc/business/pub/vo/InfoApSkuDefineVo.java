package com.tydic.unicom.apc.business.pub.vo;

import java.util.List;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoApSkuDefineVo extends BaseVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sku_id;
	private String sku_name;
	private String sku_type;
	private String bind_flag;
	private String sku_desc;
	private List<SkuAttrVo> attr_list;
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	public String getSku_type() {
		return sku_type;
	}
	public void setSku_type(String sku_type) {
		this.sku_type = sku_type;
	}
	public String getBind_flag() {
		return bind_flag;
	}
	public void setBind_flag(String bind_flag) {
		this.bind_flag = bind_flag;
	}
	public String getSku_desc() {
		return sku_desc;
	}
	public void setSku_desc(String sku_desc) {
		this.sku_desc = sku_desc;
	}
	public List<SkuAttrVo> getAttr_list() {
		return attr_list;
	}
	public void setAttr_list(List<SkuAttrVo> attr_list) {
		this.attr_list = attr_list;
	}
}
