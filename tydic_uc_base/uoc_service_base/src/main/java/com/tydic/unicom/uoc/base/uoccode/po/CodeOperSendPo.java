package com.tydic.unicom.uoc.base.uoccode.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class CodeOperSendPo extends BasePo{

	private static final long serialVersionUID = 1L;

	private String oper_no;
	private String send_name;
	private String real_phone;
	private String send_addr;
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
