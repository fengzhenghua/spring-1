package com.tydic.unicom.uoc.service.order.his.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoOfrOrderPayHisVo extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String ofr_order_no;
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String fee_item_code;
	private String pay_engine;
	private String pay_sn;
	private String discount_sn;
	private String pay_oper_no;
	private String pay_oper_name;
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
	public String getFee_item_code() {
		return fee_item_code;
	}
	public void setFee_item_code(String fee_item_code) {
		this.fee_item_code = fee_item_code;
	}
	public String getPay_engine() {
		return pay_engine;
	}
	public void setPay_engine(String pay_engine) {
		this.pay_engine = pay_engine;
	}
	public String getPay_sn() {
		return pay_sn;
	}
	public void setPay_sn(String pay_sn) {
		this.pay_sn = pay_sn;
	}
	public String getDiscount_sn() {
		return discount_sn;
	}
	public void setDiscount_sn(String discount_sn) {
		this.discount_sn = discount_sn;
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
