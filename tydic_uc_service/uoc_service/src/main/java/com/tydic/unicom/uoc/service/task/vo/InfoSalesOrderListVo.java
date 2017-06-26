package com.tydic.unicom.uoc.service.task.vo;

import java.util.List;

import com.tydic.unicom.uoc.base.common.po.AccNbrPo;
import com.tydic.unicom.webUtil.BaseVo;

public class InfoSalesOrderListVo extends BaseVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sale_order_no;
	private String cert_no;
	private String cust_name;
	private List<AccNbrPo> acc_nbr_list;
	private String accept_time;
	private String accept_system;
	private String order_type;
	private String accept_oper_name;
	private String accept_depart_name;
	private String province_code;
	private String area_code;
	private String order_state;
	private String task_id;
	private String tache_code;
	private String finish_flag;
	public String getSale_order_no() {
		return sale_order_no;
	}
	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	
	public List<AccNbrPo> getAcc_nbr_list() {
		return acc_nbr_list;
	}
	public void setAcc_nbr_list(List<AccNbrPo> acc_nbr_list) {
		this.acc_nbr_list = acc_nbr_list;
	}
	public String getAccept_time() {
		return accept_time;
	}
	public void setAccept_time(String accept_time) {
		this.accept_time = accept_time;
	}
	public String getAccept_system() {
		return accept_system;
	}
	public void setAccept_system(String accept_system) {
		this.accept_system = accept_system;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getAccept_oper_name() {
		return accept_oper_name;
	}
	public void setAccept_oper_name(String accept_oper_name) {
		this.accept_oper_name = accept_oper_name;
	}
	
	public String getAccept_depart_name() {
		return accept_depart_name;
	}
	public void setAccept_depart_name(String accept_depart_name) {
		this.accept_depart_name = accept_depart_name;
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
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public String getFinish_flag() {
		return finish_flag;
	}
	public void setFinish_flag(String finish_flag) {
		this.finish_flag = finish_flag;
	}
	

}
