package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class OrderCancelApplyVo extends BaseVo{
	
	private static final long serialVersionUID = 1L;
	
	private String jsession_id;
	private String order_no;
	private String order_type;
	private String tache_code;
	private String cancel_type;
	private String cancel_reason;
	
	private String province_code;
	private String area_code;
	private String part_month;
	private String cancel_time;
	private String cancel_oper_no;
	private String cancel_tache_code;
	
	public String getJsession_id() {
		return jsession_id;
	}
	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public String getCancel_type() {
		return cancel_type;
	}
	public void setCancel_type(String cancel_type) {
		this.cancel_type = cancel_type;
	}
	public String getCancel_reason() {
		return cancel_reason;
	}
	public void setCancel_reason(String cancel_reason) {
		this.cancel_reason = cancel_reason;
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
	public String getCancel_time() {
		return cancel_time;
	}
	public void setCancel_time(String cancel_time) {
		this.cancel_time = cancel_time;
	}
	public String getCancel_oper_no() {
		return cancel_oper_no;
	}
	public void setCancel_oper_no(String cancel_oper_no) {
		this.cancel_oper_no = cancel_oper_no;
	}
	public String getCancel_tache_code() {
		return cancel_tache_code;
	}
	public void setCancel_tache_code(String cancel_tache_code) {
		this.cancel_tache_code = cancel_tache_code;
	}
	
}
