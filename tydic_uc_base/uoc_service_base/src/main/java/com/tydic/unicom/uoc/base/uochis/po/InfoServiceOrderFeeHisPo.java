package com.tydic.unicom.uoc.base.uochis.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoServiceOrderFeeHisPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String serv_order_no;
	private String ofr_order_no;
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String fee_item_code;
	private String fee_item_name;
	private String fee_item_type;
	private String total_fee;
	private String discount_fee;
	private String payed_fee;
	private String discount_sn;
	private String discount_comments;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDiscount_sn() {
		return discount_sn;
	}
	public void setDiscount_sn(String discount_sn) {
		this.discount_sn = discount_sn;
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
	public String getFee_item_code() {
		return fee_item_code;
	}
	public void setFee_item_code(String fee_item_code) {
		this.fee_item_code = fee_item_code;
	}
	public String getFee_item_name() {
		return fee_item_name;
	}
	public void setFee_item_name(String fee_item_name) {
		this.fee_item_name = fee_item_name;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getDiscount_fee() {
		return discount_fee;
	}
	public void setDiscount_fee(String discount_fee) {
		this.discount_fee = discount_fee;
	}
	public String getPayed_fee() {
		return payed_fee;
	}
	public void setPayed_fee(String payed_fee) {
		this.payed_fee = payed_fee;
	}
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getFee_item_type() {
		return fee_item_type;
	}
	public void setFee_item_type(String fee_item_type) {
		this.fee_item_type = fee_item_type;
	}
	public String getDiscount_comments() {
		return discount_comments;
	}
	public void setDiscount_comments(String discount_comments) {
		this.discount_comments = discount_comments;
	}
	
}
