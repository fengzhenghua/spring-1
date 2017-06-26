package com.tydic.unicom.apc.business.pub.vo;

import com.tydic.unicom.webUtil.BaseVo;

/**
 * 触点属性视图显示对象
 * @author ZhangCheng
 * @date 2017-04-24
 */
public class ApAttrVo extends BaseVo {

	private static final long serialVersionUID = 1L;
	private String jsession_id;
	/** 操作类型：100新增，101修改，102删除 */
	private String oper_type;
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
		return "ApAttrVo [jsession_id=" + jsession_id + ", oper_type=" + oper_type + ", id=" + id + ", ap_id=" + ap_id
				+ ", attr_type=" + attr_type + ", attr_id=" + attr_id + ", attr_value=" + attr_value + ", attr_name=" + attr_name + "]";
	}
	
	public String getAttr_name() {
		return attr_name;
	}

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	
	public String getJsession_id() {
		return jsession_id;
	}

	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}

	public String getOper_type() {
		return oper_type;
	}


	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
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
