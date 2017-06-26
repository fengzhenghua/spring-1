package com.tydic.unicom.uoc.base.uocinst.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoServiceOrderAgreementPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String serv_order_no;
	private String ofr_order_no;
	private String sale_order_no;
	private String part_month;
	private String agreement_id;
	private String province_code;
	private String area_code;
	private String limit_engine;
	private String limit_months;
	private String end_method;
	private String guarant_type;
	private String ext_field_1;
	private String ext_field_2;
	
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getGuarant_type() {
		return guarant_type;
	}
	public void setGuarant_type(String guarant_type) {
		this.guarant_type = guarant_type;
	}
	public String getLimit_engine() {
		return limit_engine;
	}
	public void setLimit_engine(String limit_engine) {
		this.limit_engine = limit_engine;
	}
	public String getLimit_months() {
		return limit_months;
	}
	public void setLimit_months(String limit_months) {
		this.limit_months = limit_months;
	}
	public String getAgreement_id() {
		return agreement_id;
	}
	public void setAgreement_id(String agreement_id) {
		this.agreement_id = agreement_id;
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
	public String getEnd_method() {
		return end_method;
	}
	public void setEnd_method(String end_method) {
		this.end_method = end_method;
	}
	public String getExt_field_1() {
		return ext_field_1;
	}
	public void setExt_field_1(String ext_field_1) {
		this.ext_field_1 = ext_field_1;
	}
	public String getExt_field_2() {
		return ext_field_2;
	}
	public void setExt_field_2(String ext_field_2) {
		this.ext_field_2 = ext_field_2;
	}

}
