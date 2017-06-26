package com.tydic.unicom.uoc.base.common.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class WriteCardResultInPo extends BasePo {

	/**
		 *
		 */
	private static final long serialVersionUID = 1L;
	private String jsession_id;
	private String serv_order_no;
	private String sim_id_begin;
	private String sim_id_end;
	private String acc_nbr;
	private String cust_name;
	private String accept_time_start;
	private String accept_time_end;
	private String province_code;
	private String area_code;
	private String part_month;
	private String write_card_result;
	private String page_no; // 当前页码
	private String page_size; // 每页条数

	public String getJsession_id() {
		return jsession_id;
	}

	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}

	public String getServ_order_no() {
		return serv_order_no;
	}

	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
	}

	public String getSim_id_begin() {
		return sim_id_begin;
	}

	public void setSim_id_begin(String sim_id_begin) {
		this.sim_id_begin = sim_id_begin;
	}

	public String getSim_id_end() {
		return sim_id_end;
	}

	public void setSim_id_end(String sim_id_end) {
		this.sim_id_end = sim_id_end;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getAccept_time_start() {
		return accept_time_start;
	}

	public void setAccept_time_start(String accept_time_start) {
		this.accept_time_start = accept_time_start;
	}

	public String getAccept_time_end() {
		return accept_time_end;
	}

	public void setAccept_time_end(String accept_time_end) {
		this.accept_time_end = accept_time_end;
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

	public String getWrite_card_result() {
		return write_card_result;
	}

	public void setWrite_card_result(String write_card_result) {
		this.write_card_result = write_card_result;
	}

	public String getPage_no() {
		return page_no;
	}

	public void setPage_no(String page_no) {
		this.page_no = page_no;
	}

	public String getPage_size() {
		return page_size;
	}

	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}

	}
