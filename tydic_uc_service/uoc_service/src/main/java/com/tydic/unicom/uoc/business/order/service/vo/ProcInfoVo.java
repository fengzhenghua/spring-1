package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ProcInfoVo extends BaseVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tache_name;
	private String oper_no;
	private String oper_name;
	private String create_time;
	private String finish_time;
	public String getTache_name() {
		return tache_name;
	}
	public void setTache_name(String tache_name) {
		this.tache_name = tache_name;
	}
	public String getOper_no() {
		return oper_no;
	}
	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}
	public String getOper_name() {
		return oper_name;
	}
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
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
	
	

}
