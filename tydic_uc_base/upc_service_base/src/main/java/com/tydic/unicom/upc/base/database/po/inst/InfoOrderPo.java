package com.tydic.unicom.upc.base.database.po.inst;

import java.math.BigDecimal;
import java.util.Date;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoOrderPo extends BasePo {
	private static final long serialVersionUID = -4777793686417103325L;

	private String order_id;
	private String busi_id;
	private String order_type;
	private String pay_type;
	private String order_status;
	private Date create_time;
	private String out_order_id;
	private String create_ip_address;
	private BigDecimal total_fee;
	private BigDecimal discount_fee;
	private BigDecimal real_fee;
	private Date pay_notify_time;
	private String pay_notify_code;
	private String pay_notify_msg;
	private String pay_notify_trans_id;
	private Date cust_notify_time;
	private String cust_notify_code;
	private String cust_notify_msg;
	private String out_refund_no;
	private String detail_name;
	private String out_remark;
	private String req_way;
	private String order_refund_id;
	private String pay_order_id;
	private String trade_time; /// 对账主要根据时间来筛选
	
	public String getTrade_time() {
		return trade_time;
	}
	public void setTrade_time(String trade_time) {
		this.trade_time = trade_time;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getBusi_id() {
		return busi_id;
	}
	public void setBusi_id(String busi_id) {
		this.busi_id = busi_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
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
	public String getOut_order_id() {
		return out_order_id;
	}
	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}
	public String getCreate_ip_address() {
		return create_ip_address;
	}
	public void setCreate_ip_address(String create_ip_address) {
		this.create_ip_address = create_ip_address;
	}
	public BigDecimal getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}
	public BigDecimal getDiscount_fee() {
		return discount_fee;
	}
	public void setDiscount_fee(BigDecimal discount_fee) {
		this.discount_fee = discount_fee;
	}
	public BigDecimal getReal_fee() {
		return real_fee;
	}
	public void setReal_fee(BigDecimal real_fee) {
		this.real_fee = real_fee;
	}
	public Date getPay_notify_time() {
		return pay_notify_time;
	}
	public void setPay_notify_time(Date pay_notify_time) {
		this.pay_notify_time = pay_notify_time;
	}
	public String getPay_notify_code() {
		return pay_notify_code;
	}
	public void setPay_notify_code(String pay_notify_code) {
		this.pay_notify_code = pay_notify_code;
	}
	public String getPay_notify_msg() {
		return pay_notify_msg;
	}
	public void setPay_notify_msg(String pay_notify_msg) {
		this.pay_notify_msg = pay_notify_msg;
	}
	public String getPay_notify_trans_id() {
		return pay_notify_trans_id;
	}
	public void setPay_notify_trans_id(String pay_notify_trans_id) {
		this.pay_notify_trans_id = pay_notify_trans_id;
	}
	public Date getCust_notify_time() {
		return cust_notify_time;
	}
	public void setCust_notify_time(Date cust_notify_time) {
		this.cust_notify_time = cust_notify_time;
	}
	public String getCust_notify_code() {
		return cust_notify_code;
	}
	public void setCust_notify_code(String cust_notify_code) {
		this.cust_notify_code = cust_notify_code;
	}
	public String getCust_notify_msg() {
		return cust_notify_msg;
	}
	public void setCust_notify_msg(String cust_notify_msg) {
		this.cust_notify_msg = cust_notify_msg;
	}
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
	public String getDetail_name() {
		return detail_name;
	}
	public void setDetail_name(String detail_name) {
		this.detail_name = detail_name;
	}
	public String getOut_remark() {
		return out_remark;
	}
	public void setOut_remark(String out_remark) {
		this.out_remark = out_remark;
	}
	public String getReq_way() {
		return req_way;
	}
	public void setReq_way(String req_way) {
		this.req_way = req_way;
	}
	public String getOrder_refund_id() {
		return order_refund_id;
	}
	public void setOrder_refund_id(String order_refund_id) {
		this.order_refund_id = order_refund_id;
	}
	public String getPay_order_id() {
		return pay_order_id;
	}
	public void setPay_order_id(String pay_order_id) {
		this.pay_order_id = pay_order_id;
	}
	
	
}
