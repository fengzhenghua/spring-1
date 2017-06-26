package com.tydic.unicom.uoc.base.uocinst.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class ProcInstTaskInstPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String task_id;
	private String task_code;
	private String task_name;
	private String tache_code;
	private String province_code;
	private String area_code;
	private String part_month;
	private String order_no;
	private String order_type;
	private String proc_inst_id;
	private String oper_code;
	private String task_state;
	private String task_property;
	private String create_time;
	private String finish_time;
	private String accept_oper_no;
	private String accept_oper_name;
	private String accept_depart_no;
	private String accept_depart_name;
	private String limit_time;
	private String global_limit_time;
	private String person_flag; //个人任务标识：0个人任务1部门任务
	private String cust_name;	//客户名称
	private String cert_no;		//客户证件
	private String acc_nbr;		//业务号码
	private String prod_code;	//产品编码
	private String accept_no;	//前台受理流水
	private String page_no;		//当前页码
	private String page_size;	//每页条数
	
	private String tache_count;
	
	public String getPerson_flag() {
		return person_flag;
	}
	public void setPerson_flag(String person_flag) {
		this.person_flag = person_flag;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public String getProd_code() {
		return prod_code;
	}
	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}
	public String getAccept_no() {
		return accept_no;
	}
	public void setAccept_no(String accept_no) {
		this.accept_no = accept_no;
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
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
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
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getTask_code() {
		return task_code;
	}
	public void setTask_code(String task_code) {
		this.task_code = task_code;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
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
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getProc_inst_id() {
		return proc_inst_id;
	}
	public void setProc_inst_id(String proc_inst_id) {
		this.proc_inst_id = proc_inst_id;
	}
	
	public String getOper_code() {
		return oper_code;
	}
	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}
	public String getTask_state() {
		return task_state;
	}
	public void setTask_state(String task_state) {
		this.task_state = task_state;
	}
	public String getTask_property() {
		return task_property;
	}
	public void setTask_property(String task_property) {
		this.task_property = task_property;
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
	public String getLimit_time() {
		return limit_time;
	}
	public void setLimit_time(String limit_time) {
		this.limit_time = limit_time;
	}
	public String getGlobal_limit_time() {
		return global_limit_time;
	}
	public void setGlobal_limit_time(String global_limit_time) {
		this.global_limit_time = global_limit_time;
	}
	public String getTache_count() {
		return tache_count;
	}
	public void setTache_count(String tache_count) {
		this.tache_count = tache_count;
	}
	
	
	
}
