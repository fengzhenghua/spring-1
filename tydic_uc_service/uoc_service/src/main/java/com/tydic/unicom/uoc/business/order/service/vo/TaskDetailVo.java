package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;


public class TaskDetailVo extends BaseVo{
	private static final long serialVersionUID = 1L;

	private String order_no;		    	//订单号
	private String task_name;				//任务名称
	private String tache_code;				//环节编码
	private String create_time;				//创建时间
	private String finish_time;	 			//结束时间
	private String limit_time;	 			//逾期时间
	private String global_limit_time;	 	//全局逾期时间
	private String oper_code;	 			//业务类型
	private String task_url;	 			//嵌套页面URL
	private String task_json;	 			//嵌套页面JSON参数
	private String cust_name;	 			//客户名称
	private String cert_no; 				//证件号码
	private String acc_nbr;	 				//号码
	private String prod_code;	 			//产品编码
	private String first_month_rent;	 	//首月计费方式
	private String prod_name;
	private String iccid;

	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
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
	public String getOper_code() {
		return oper_code;
	}
	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}
	public String getTask_url() {
		return task_url;
	}
	public void setTask_url(String task_url) {
		this.task_url = task_url;
	}
	public String getTask_json() {
		return task_json;
	}
	public void setTask_json(String task_json) {
		this.task_json = task_json;
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
	public String getFirst_month_rent() {
		return first_month_rent;
	}
	public void setFirst_month_rent(String first_month_rent) {
		this.first_month_rent = first_month_rent;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

}
