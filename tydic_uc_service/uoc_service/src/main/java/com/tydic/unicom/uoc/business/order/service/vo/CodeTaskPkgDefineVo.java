package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class CodeTaskPkgDefineVo extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pkg_id;
	private String pkg_name;
	private String task_num;
	private String state;
	private String create_oper_no;
	private String create_time;
	public String getPkg_id() {
		return pkg_id;
	}
	public void setPkg_id(String pkg_id) {
		this.pkg_id = pkg_id;
	}
	public String getPkg_name() {
		return pkg_name;
	}
	public void setPkg_name(String pkg_name) {
		this.pkg_name = pkg_name;
	}
	public String getTask_num() {
		return task_num;
	}
	public void setTask_num(String task_num) {
		this.task_num = task_num;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreate_oper_no() {
		return create_oper_no;
	}
	public void setCreate_oper_no(String create_oper_no) {
		this.create_oper_no = create_oper_no;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	
}
