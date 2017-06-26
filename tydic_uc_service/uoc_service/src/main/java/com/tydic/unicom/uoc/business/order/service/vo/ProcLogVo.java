package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ProcLogVo extends BaseVo{
	private static final long serialVersionUID = 1L;

	private String tache_code;
	private String tache_name;
	private String create_time;
	private String deal_desc;
	private String deal_code;
	private String deal_time;
	private String oper_no;

	public String getTache_name() {
		return tache_name;
	}

	public void setTache_name(String tache_name) {
		this.tache_name = tache_name;
	}
	public String getDeal_desc() {
		return deal_desc;
	}
	public void setDeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}
	public String getDeal_code() {
		return deal_code;
	}
	public void setDeal_code(String deal_code) {
		this.deal_code = deal_code;
	}
	public String getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}
	public String getOper_no() {
		return oper_no;
	}
	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

}
