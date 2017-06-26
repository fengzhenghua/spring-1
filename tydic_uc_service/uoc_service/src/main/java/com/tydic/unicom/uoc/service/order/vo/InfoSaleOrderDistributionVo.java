package com.tydic.unicom.uoc.service.order.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoSaleOrderDistributionVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String logistics_type;
	private String contact_name;
	private String contact_tel;
	private String post_province;
	private String post_city;
	private String post_area;
	private String address;
	private String post_code;
	private String distribution_begin_time;
	private String distribution_end_time;
	private String self_chl_id;
	private String self_chl_name;
	private String pay_type;
	
	public String getLogistics_type() {
		return logistics_type;
	}
	public void setLogistics_type(String logistics_type) {
		this.logistics_type = logistics_type;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getSale_order_no() {
		return sale_order_no;
	}
	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_tel() {
		return contact_tel;
	}
	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}
	public String getPost_province() {
		return post_province;
	}
	public void setPost_province(String post_province) {
		this.post_province = post_province;
	}
	public String getPost_city() {
		return post_city;
	}
	public void setPost_city(String post_city) {
		this.post_city = post_city;
	}
	public String getPost_area() {
		return post_area;
	}
	public void setPost_area(String post_area) {
		this.post_area = post_area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public String getDistribution_begin_time() {
		return distribution_begin_time;
	}
	public void setDistribution_begin_time(String distribution_begin_time) {
		this.distribution_begin_time = distribution_begin_time;
	}
	public String getDistribution_end_time() {
		return distribution_end_time;
	}
	public void setDistribution_end_time(String distribution_end_time) {
		this.distribution_end_time = distribution_end_time;
	}
	public String getSelf_chl_id() {
		return self_chl_id;
	}
	public void setSelf_chl_id(String self_chl_id) {
		this.self_chl_id = self_chl_id;
	}
	public String getSelf_chl_name() {
		return self_chl_name;
	}
	public void setSelf_chl_name(String self_chl_name) {
		this.self_chl_name = self_chl_name;
	}

}
