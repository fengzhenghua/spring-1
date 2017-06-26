package com.tydic.unicom.uoc.base.uoccode.po;

import java.util.Date;

import com.tydic.unicom.vdsBase.po.BasePo;

public class UserGrantFeePo extends BasePo {

	private static final long serialVersionUID = 3877821507256186169L;
	
	private String device_number;//赠费号码
	private String active_flag;//号码生效标志
	private String acct_month;//赠费月份
	private double grant_fee;//赠送费用
	private String deal_flag;//处理标志
	private String deal_result;//处理结果
	private Date create_date;//创建时间
	private Date update_date;//修改时间
	private String grant_type;//赠款类型
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	public String getActive_flag() {
		return active_flag;
	}
	public void setActive_flag(String active_flag) {
		this.active_flag = active_flag;
	}
	public String getAcct_month() {
		return acct_month;
	}
	public void setAcct_month(String acct_month) {
		this.acct_month = acct_month;
	}
	public double getGrant_fee() {
		return grant_fee;
	}
	public void setGrant_fee(double grant_fee) {
		this.grant_fee = grant_fee;
	}
	public String getDeal_flag() {
		return deal_flag;
	}
	public void setDeal_flag(String deal_flag) {
		this.deal_flag = deal_flag;
	}
	public String getDeal_result() {
		return deal_result;
	}
	public void setDeal_result(String deal_result) {
		this.deal_result = deal_result;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	
	
	@Override
	public String toString() {
		return "UserGrantFeePo [device_number=" + device_number
				+ ", active_flag=" + active_flag + ", acct_month=" + acct_month
				+ ", grant_fee=" + grant_fee + ", deal_flag=" + deal_flag
				+ ", deal_result=" + deal_result + ", create_date="
				+ create_date + ", update_date=" + update_date
				+ ", grant_type=" + grant_type + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
