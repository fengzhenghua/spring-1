package com.tydic.unicom.uoc.base.uocinst.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoServiceOrderAttrPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String serv_order_no;
	private String ofr_order_no;
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String attr_code;
	private String attr_type;
	private String attr_after_value;
	private String attr_before_value;
	private String curr_seq;
	private String upper_seq;

	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(String attr_type) {
		this.attr_type = attr_type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOfr_order_no() {
		return ofr_order_no;
	}
	public void setOfr_order_no(String ofr_order_no) {
		this.ofr_order_no = ofr_order_no;
	}
	public String getCurr_seq() {
		return curr_seq;
	}
	public void setCurr_seq(String curr_seq) {
		this.curr_seq = curr_seq;
	}
	public String getUpper_seq() {
		return upper_seq;
	}
	public void setUpper_seq(String upper_seq) {
		this.upper_seq = upper_seq;
	}
	public String getServ_order_no() {
		return serv_order_no;
	}
	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
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
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	public String getAttr_after_value() {
		return attr_after_value;
	}
	public void setAttr_after_value(String attr_after_value) {
		this.attr_after_value = attr_after_value;
	}
	public String getAttr_before_value() {
		return attr_before_value;
	}
	public void setAttr_before_value(String attr_before_value) {
		this.attr_before_value = attr_before_value;
	}

}
