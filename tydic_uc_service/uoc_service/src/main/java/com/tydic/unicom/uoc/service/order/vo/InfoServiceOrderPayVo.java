package com.tydic.unicom.uoc.service.order.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoServiceOrderPayVo extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String serv_order_no;
	private String ofr_order_no;
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String pay_type;
	private String total_fee;
	private String discount_fee;
	private String payed_fee;
	private String pay_sn;
	private String pay_oper_no;
	private String pay_oper_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
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
	public String getPay_sn() {
		return pay_sn;
	}
	public void setPay_sn(String pay_sn) {
		this.pay_sn = pay_sn;
	}
	public String getPay_oper_no() {
		return pay_oper_no;
	}
	public void setPay_oper_no(String pay_oper_no) {
		this.pay_oper_no = pay_oper_no;
	}
	public String getPay_oper_name() {
		return pay_oper_name;
	}
	public void setPay_oper_name(String pay_oper_name) {
		this.pay_oper_name = pay_oper_name;
	}
	
}
