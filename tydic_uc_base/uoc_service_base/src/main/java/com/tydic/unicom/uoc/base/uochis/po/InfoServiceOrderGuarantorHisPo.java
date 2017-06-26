package com.tydic.unicom.uoc.base.uochis.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoServiceOrderGuarantorHisPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String serv_order_no;
	private String ofr_order_no;
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String guarant_type;
	private String guarant_project;
	private String guarant_method;
	private String guarant_eff_time;
	private String guarant_depart_code;
	private String guarant_sn;

	public String getGuarant_type() {
		return guarant_type;
	}
	public void setGuarant_type(String guarant_type) {
		this.guarant_type = guarant_type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getServ_order_no() {
		return serv_order_no;
	}
	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
	}
	public String getOfr_order_no() {
		return ofr_order_no;
	}
	public void setOfr_order_no(String ofr_order_no) {
		this.ofr_order_no = ofr_order_no;
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
	public String getGuarant_project() {
		return guarant_project;
	}
	public void setGuarant_project(String guarant_project) {
		this.guarant_project = guarant_project;
	}
	public String getGuarant_method() {
		return guarant_method;
	}
	public void setGuarant_method(String guarant_method) {
		this.guarant_method = guarant_method;
	}
	public String getGuarant_eff_time() {
		return guarant_eff_time;
	}
	public void setGuarant_eff_time(String guarant_eff_time) {
		this.guarant_eff_time = guarant_eff_time;
	}
	public String getGuarant_depart_code() {
		return guarant_depart_code;
	}
	public void setGuarant_depart_code(String guarant_depart_code) {
		this.guarant_depart_code = guarant_depart_code;
	}
	public String getGuarant_sn() {
		return guarant_sn;
	}
	public void setGuarant_sn(String guarant_sn) {
		this.guarant_sn = guarant_sn;
	}

}
