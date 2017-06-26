package com.tydic.unicom.uoc.business.common.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ExpressVo  extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String jsession_id;
	private String serv_order_no;
	private String oper_no;
	private String send_name;
	private String real_phone;
	private String send_addr;
	public String getJsession_id() {
		return jsession_id;
	}
	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}
	public String getServ_order_no() {
		return serv_order_no;
	}
	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
	}
	public String getOper_no() {
		return oper_no;
	}
	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	public String getReal_phone() {
		return real_phone;
	}
	public void setReal_phone(String real_phone) {
		this.real_phone = real_phone;
	}
	public String getSend_addr() {
		return send_addr;
	}
	public void setSend_addr(String send_addr) {
		this.send_addr = send_addr;
	}
	
	
}
