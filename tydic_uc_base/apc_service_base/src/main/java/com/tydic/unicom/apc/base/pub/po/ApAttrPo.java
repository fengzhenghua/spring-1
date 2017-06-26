package com.tydic.unicom.apc.base.pub.po;

import com.tydic.unicom.vdsBase.po.BasePo;

/**
 * 触点属性表实体类
 * @author ZhangCheng
 * @date 2017-04-24
 */
public class ApAttrPo extends BasePo {

	private static final long serialVersionUID = 1L;
	/** 触点属性表ID */
	private String id;
	/** 触点方案ID */
	private String ap_id;
	/** 属性类型ID */
	private String attr_type;
	/** 属性英文key */
	private String attr_id;
	/** 属性值value */
	private String attr_value;
	/** 属性名称 */
	private String attr_name;
	
	@Override
	public String toString() {
		return "ApAttrPo [id=" + id + ", ap_id=" + ap_id + ", attr_type=" + attr_type + ", attr_id=" + attr_id
				+ ", attr_value=" + attr_value + ", attr_name=" + attr_name + "]";
	}
	
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
	public String getAp_id() {
		return ap_id;
	}
	public void setAp_id(String ap_id) {
		this.ap_id = ap_id;
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
