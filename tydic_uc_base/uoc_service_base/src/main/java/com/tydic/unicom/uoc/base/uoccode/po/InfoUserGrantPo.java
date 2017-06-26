package com.tydic.unicom.uoc.base.uoccode.po;

import java.util.Date;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoUserGrantPo extends BasePo {
	
	private static final long serialVersionUID = -123865021298652215L;
	
	private String device_number;
	private String user_no;
	private String user_status;
	private String grant_type;
	private double total_grant;
	private double residual_grant;
	private double each_grant;
	private Date in_net_date;
	//当前年月
	private String current_month;
	//前一天年月
	private String yester_month;
	//前一天的号数
	private String yester_day;
	
	public String getYester_month() {
		return yester_month;
	}
	public void setYester_month(String yester_month) {
		this.yester_month = yester_month;
	}
	public String getYester_day() {
		return yester_day;
	}
	public void setYester_day(String yester_day) {
		this.yester_day = yester_day;
	}
	public String getCurrent_month() {
		return current_month;
	}
	public void setCurrent_month(String current_month) {
		this.current_month = current_month;
	}
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public String getUser_status() {
		return user_status;
	}
	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public double getTotal_grant() {
		return total_grant;
	}
	public void setTotal_grant(double total_grant) {
		this.total_grant = total_grant;
	}
	public double getResidual_grant() {
		return residual_grant;
	}
	public void setResidual_grant(double residual_grant) {
		this.residual_grant = residual_grant;
	}
	public double getEach_grant() {
		return each_grant;
	}
	public void setEach_grant(double each_grant) {
		this.each_grant = each_grant;
	}
	public Date getIn_net_date() {
		return in_net_date;
	}
	public void setIn_net_date(Date in_net_date) {
		this.in_net_date = in_net_date;
	}
	@Override
	public String toString() {
		return "InfoUserGrantPo [device_number=" + device_number + ", user_no="
				+ user_no + ", user_status=" + user_status + ", grant_type="
				+ grant_type + ", total_grant=" + total_grant
				+ ", residual_grant=" + residual_grant + ", each_grant="
				+ each_grant + ", in_net_date=" + in_net_date + ", toString()="
				+ super.toString() + "]";
	}

	
}
