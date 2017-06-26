package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ServOrderListVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String sim_id;
	private String acc_nbr;
	private String contact_tel;
	private String serv_order_no;
	private String prod_code;

	public String getSim_id() {
		return sim_id;
	}

	public void setSim_id(String sim_id) {
		this.sim_id = sim_id;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getContact_tel() {
		return contact_tel;
	}

	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}

	public String getServ_order_no() {
		return serv_order_no;
	}

	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
	}

	public String getProd_code() {
		return prod_code;
	}

	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}

	}
