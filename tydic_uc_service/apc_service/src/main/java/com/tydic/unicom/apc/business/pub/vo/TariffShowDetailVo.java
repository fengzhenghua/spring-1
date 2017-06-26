package com.tydic.unicom.apc.business.pub.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class TariffShowDetailVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	private String rent;
	private String province_flow_charge;
	private String country_flow_charge;
	private String province_minutes_charge;
	private String country_minutes_charge;
	private String province_free_minutes;
	private String country_free_minutes;
	private String province_free_flow;
	private String country_free_flow;
	private String other_info;
	public String getRent() {
		return rent;
	}
	public void setRent(String rent) {
		this.rent = rent;
	}
	public String getProvince_flow_charge() {
		return province_flow_charge;
	}
	public void setProvince_flow_charge(String province_flow_charge) {
		this.province_flow_charge = province_flow_charge;
	}
	public String getCountry_flow_charge() {
		return country_flow_charge;
	}
	public void setCountry_flow_charge(String country_flow_charge) {
		this.country_flow_charge = country_flow_charge;
	}
	public String getProvince_minutes_charge() {
		return province_minutes_charge;
	}
	public void setProvince_minutes_charge(String province_minutes_charge) {
		this.province_minutes_charge = province_minutes_charge;
	}
	public String getCountry_minutes_charge() {
		return country_minutes_charge;
	}
	public void setCountry_minutes_charge(String country_minutes_charge) {
		this.country_minutes_charge = country_minutes_charge;
	}
	public String getProvince_free_minutes() {
		return province_free_minutes;
	}
	public void setProvince_free_minutes(String province_free_minutes) {
		this.province_free_minutes = province_free_minutes;
	}
	public String getCountry_free_minutes() {
		return country_free_minutes;
	}
	public void setCountry_free_minutes(String country_free_minutes) {
		this.country_free_minutes = country_free_minutes;
	}
	public String getProvince_free_flow() {
		return province_free_flow;
	}
	public void setProvince_free_flow(String province_free_flow) {
		this.province_free_flow = province_free_flow;
	}
	public String getCountry_free_flow() {
		return country_free_flow;
	}
	public void setCountry_free_flow(String country_free_flow) {
		this.country_free_flow = country_free_flow;
	}
	public String getOther_info() {
		return other_info;
	}
	public void setOther_info(String other_info) {
		this.other_info = other_info;
	}
	
}
