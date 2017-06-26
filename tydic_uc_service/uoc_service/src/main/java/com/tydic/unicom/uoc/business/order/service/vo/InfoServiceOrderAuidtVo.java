package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoServiceOrderAuidtVo extends BaseVo {
	
	private static final long serialVersionUID = -6115868826927219348L;
	/**
	 * 业务名称
	 */
	private String oper_name;
	/**
	 * 服务订单号
	 */
	private String serv_order_no;
	/**
	 * 服务号码
	 */
	private String acc_nbr;
	/**
	 * 商品订单号
	 */
	private String ofr_order_no;
	/**
	 * 销售订单号
	 */
	private String sale_order_no;
	/**
	 * 创建时间
	 */
	private String create_time;
	/**
	 * 完成时间
	 */
	private String finish_time;
	
	/**
	 * 受理日期
	 */
	private String accept_date;
	
	/**
	 * 受理时间
	 */
	private String accept_time;
	
	/**
	 * 受理工号
	 */
	private String accept_oper_no;
	/**
	 * 受理员号
	 */
	private String accept_oper_name;
	/**
	 * 受理部门
	 */
	private String accept_depart_no;
	/**
	 * 受理部门名称
	 */
	private String accept_depart_name;
	/**
	 * 受理部门类型
	 */
	private String accept_depart_type;
	
	/**
	 * 审核状态
	 */
	private String autid_status;
	
	/**
	 * 客户名称
	 */
	private String cust_name;
	
	/**
	 * 联系号码
	 */
	private String conect_number;
	
	/**
	 * 地址
	 */
	private String cust_address;
	
	/**
	 * 证件号码
	 */
	private String cert_id;
	
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
	
	public String getOper_name() {
		return oper_name;
	}
	
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	
	public String getAcc_nbr() {
		return acc_nbr;
	}
	
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
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
	
	public String getAccept_depart_type() {
		return accept_depart_type;
	}
	
	public void setAccept_depart_type(String accept_depart_type) {
		this.accept_depart_type = accept_depart_type;
	}
	
	public String getAccept_date() {
		return accept_date;
	}
	
	public void setAccept_date(String accept_date) {
		this.accept_date = accept_date;
	}
	
	public String getAccept_time() {
		return accept_time;
	}
	
	public void setAccept_time(String accept_time) {
		this.accept_time = accept_time;
	}
	
	public String getAutid_status() {
		return autid_status;
	}
	
	public void setAutid_status(String autid_status) {
		this.autid_status = autid_status;
	}
	
	public String getCust_name() {
		return cust_name;
	}
	
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	
	public String getConect_number() {
		return conect_number;
	}
	
	public void setConect_number(String conect_number) {
		this.conect_number = conect_number;
	}
	
	public String getCust_address() {
		return cust_address;
	}
	
	public void setCust_address(String cust_address) {
		this.cust_address = cust_address;
	}
	
	public String getCert_id() {
		return cert_id;
	}
	
	public void setCert_id(String cert_id) {
		this.cert_id = cert_id;
	}
	
}
