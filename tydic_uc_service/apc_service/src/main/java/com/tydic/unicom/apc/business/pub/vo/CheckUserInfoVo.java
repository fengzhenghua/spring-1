package com.tydic.unicom.apc.business.pub.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class CheckUserInfoVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2057502130255996330L;
	
	
	private String oper_no;
	private String tele_type;
	private String id_number;
	private String id_type;
	private String customer_name;
	private String device_number;
	private String checkType;
	private String flag;
	
	public String getOper_no() {
		return oper_no;
	}
	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}
	public String getTele_type() {
		return tele_type;
	}
	public void setTele_type(String tele_type) {
		this.tele_type = tele_type;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public String getId_type() {
		return id_type;
	}
	public void setId_type(String id_type) {
		this.id_type = id_type;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Override
	public String toString() {
		return "CheckUserInfoVo [oper_no=" + oper_no + ", tele_type="
				+ tele_type + ", id_number=" + id_number + ", id_type="
				+ id_type + ", customer_name=" + customer_name
				+ ", device_number=" + device_number + ", checkType="
				+ checkType + ", flag=" + flag + ", toString()="
				+ super.toString() + "]";
	}

}
