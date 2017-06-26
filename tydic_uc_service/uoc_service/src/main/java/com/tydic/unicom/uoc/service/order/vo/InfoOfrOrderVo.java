package com.tydic.unicom.uoc.service.order.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoOfrOrderVo extends BaseVo {

private static final long serialVersionUID = 1L;
	
	private String ofr_order_no;
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String ofr_id;
	private String ofr_name;
	private String cancle_flag;
	private String order_state;
	private String create_time;
	private String finish_time;
	private String ord_mod_code;
	private String accept_oper_no;
	private String accept_oper_name;
	private String accept_depart_no;
	private String accept_depart_name;
	
	
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getCancle_flag() {
		return cancle_flag;
	}
	public void setCancle_flag(String cancle_flag) {
		this.cancle_flag = cancle_flag;
	}
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	public String getOrd_mod_code() {
		return ord_mod_code;
	}
	public void setOrd_mod_code(String ord_mod_code) {
		this.ord_mod_code = ord_mod_code;
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
	public String getOfr_id() {
		return ofr_id;
	}
	public void setOfr_id(String ofr_id) {
		this.ofr_id = ofr_id;
	}

	public String getOfr_name() {
		return ofr_name;
	}
	public void setOfr_name(String ofr_name) {
		this.ofr_name = ofr_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}
	public String getAccept_oper_no() {
		return accept_oper_no;
	}
	public void setAccept_oper_no(String accept_oper_no) {
		this.accept_oper_no = accept_oper_no;
	}
	public String getAccept_oper_name() {
		return accept_oper_name;
	}
	public void setAccept_oper_name(String accept_oper_name) {
		this.accept_oper_name = accept_oper_name;
	}
	public String getAccept_depart_no() {
		return accept_depart_no;
	}
	public void setAccept_depart_no(String accept_depart_no) {
		this.accept_depart_no = accept_depart_no;
	}
	public String getAccept_depart_name() {
		return accept_depart_name;
	}
	public void setAccept_depart_name(String accept_depart_name) {
		this.accept_depart_name = accept_depart_name;
	}

}
