package com.tydic.unicom.uac.base.database.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoInputOrderPo extends BasePo{

	private static final long serialVersionUID = 1L;

	private String order_id;
	private String order_name;
	private String order_type;
	private String order_sub_type;
	private String order_number;
	private String order_date;
	private String end_date;
	private String chnl_code;
	private String oper_id;
	private String oper_dept_no;
	private String create_date;
	private String check_status;
	private String link_order_id;
	private String compute_flag;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_name() {
		return order_name;
	}

	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getOrder_sub_type() {
		return order_sub_type;
	}

	public void setOrder_sub_type(String order_sub_type) {
		this.order_sub_type = order_sub_type;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getChnl_code() {
		return chnl_code;
	}

	public void setChnl_code(String chnl_code) {
		this.chnl_code = chnl_code;
	}

	public String getOper_id() {
		return oper_id;
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	public String getOper_dept_no() {
		return oper_dept_no;
	}

	public void setOper_dept_no(String oper_dept_no) {
		this.oper_dept_no = oper_dept_no;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getCheck_status() {
		return check_status;
	}

	public void setCheck_status(String check_status) {
		this.check_status = check_status;
	}

	public String getLink_order_id() {
		return link_order_id;
	}

	public void setLink_order_id(String link_order_id) {
		this.link_order_id = link_order_id;
	}

	public String getCompute_flag() {
		return compute_flag;
	}

	public void setCompute_flag(String compute_flag) {
		this.compute_flag = compute_flag;
	}

}
