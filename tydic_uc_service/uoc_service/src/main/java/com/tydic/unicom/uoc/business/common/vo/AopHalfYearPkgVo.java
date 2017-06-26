package com.tydic.unicom.uoc.business.common.vo;

import com.tydic.unicom.webUtil.BaseVo;
/**
 * Aop订购半年包入参
 * 
 *
 */
public class AopHalfYearPkgVo extends BaseVo{
	
	private static final long serialVersionUID = 1L;
	
	private String jsession_id;
	private String money;
	private String count;
	private String valid_month;
	private String res_type;
	private String acc_nbr;
	private String order_no;
	
	
	public String getJsession_id() {
		return jsession_id;
	}
	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getValid_month() {
		return valid_month;
	}
	public void setValid_month(String valid_month) {
		this.valid_month = valid_month;
	}
	public String getRes_type() {
		return res_type;
	}
	public void setRes_type(String res_type) {
		this.res_type = res_type;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	
}
