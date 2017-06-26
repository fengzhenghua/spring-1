package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class GetServOrderNoVo extends BaseVo{
	
	private static final long serialVersionUID = 1L;
	
	private String jsession_id;
	private String sim_id;
	private String acc_nbr;
	private String area_code;
	private String province_code;
	private String part_month;
	private String contact_tel;
	public String getJsession_id() {
		return jsession_id;
	}
	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}
	public String getSim_id() {
		return sim_id;
	}
	public void setSim_id(String sim_id) {
		this.sim_id = sim_id;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getContact_tel() {
		return contact_tel;
	}
	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}
	
}
