package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class OfrOrderAndServiceOrderVo extends BaseVo{


	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ofr_order_no;
	private String serv_order_no;

	public String getOfr_order_no() {
		return ofr_order_no;
	}
	public void setOfr_order_no(String ofr_order_no) {
		this.ofr_order_no = ofr_order_no;
	}
	public String getServ_order_no() {
		return serv_order_no;
	}
	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
	}
	
	
}
