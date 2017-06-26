package com.tydic.unicom.uoc.base.uocinst.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoSaleOrderFeePo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String total_fee;
	private String discount_fee;
	private String payed_fee;
	private String pay_flag;
	private String pay_time;
	private String create_time;
	
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getPay_flag() {
		return pay_flag;
	}
	public void setPay_flag(String pay_flag) {
		this.pay_flag = pay_flag;
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
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
}
