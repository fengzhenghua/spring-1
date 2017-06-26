package com.tydic.unicom.upc.vo.inst;

import java.util.Date;

import com.tydic.unicom.webUtil.BaseVo;

public class OrderPayTransVo extends BaseVo {

	private static final long serialVersionUID = 3906395912293162993L;

	private String pay_order_id;
	private String order_id;
	private String pay_type;
	private String order_status;
	private Date create_time;
	private Date update_time;
	private String pay_msg;
	public String getPay_order_id() {
		return pay_order_id;
	}
	public void setPay_order_id(String pay_order_id) {
		this.pay_order_id = pay_order_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getPay_msg() {
		return pay_msg;
	}
	public void setPay_msg(String pay_msg) {
		this.pay_msg = pay_msg;
	}
	
}
