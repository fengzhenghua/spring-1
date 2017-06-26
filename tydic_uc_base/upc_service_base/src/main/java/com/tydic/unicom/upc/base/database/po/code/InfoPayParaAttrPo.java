package com.tydic.unicom.upc.base.database.po.code;

import java.util.Date;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoPayParaAttrPo extends BasePo {

	private static final long serialVersionUID = -8742194467630707482L;
	private String attr_id;
	private String pay_para_id;
	private String attr_code;
	private String attr_value;
	private Date create_time;
	private Date update_time;
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	public String getPay_para_id() {
		return pay_para_id;
	}
	public void setPay_para_id(String pay_para_id) {
		this.pay_para_id = pay_para_id;
	}
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	public String getAttr_value() {
		return attr_value;
	}
	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
