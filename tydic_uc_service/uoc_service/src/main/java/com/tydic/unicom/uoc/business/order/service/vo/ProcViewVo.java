package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ProcViewVo extends BaseVo{
	private static final long serialVersionUID = 1L;

	private String tache_code;
	private String deal_time;
	private String seq_no;
	private String tache_name;

	public String getTache_code() {
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}

	public String getDeal_time() {
		return deal_time;
	}

	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}

	public String getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}

	public String getTache_name() {
		return tache_name;
	}

	public void setTache_name(String tache_name) {
		this.tache_name = tache_name;
	}

}
