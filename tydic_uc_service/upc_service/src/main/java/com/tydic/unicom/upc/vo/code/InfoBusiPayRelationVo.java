package com.tydic.unicom.upc.vo.code;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoBusiPayRelationVo extends BaseVo {

	private static final long serialVersionUID = 1432935283773494239L;

	private String busi_id;
	private String pay_type;
	private String pay_para_id;
	private String show_flag;
	private String state;
	private String notify_url;
	public String getBusi_id() {
		return busi_id;
	}
	public void setBusi_id(String busi_id) {
		this.busi_id = busi_id;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_para_id() {
		return pay_para_id;
	}
	public void setPay_para_id(String pay_para_id) {
		this.pay_para_id = pay_para_id;
	}
	public String getShow_flag() {
		return show_flag;
	}
	public void setShow_flag(String show_flag) {
		this.show_flag = show_flag;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	
	
}
