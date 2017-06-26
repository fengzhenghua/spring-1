package com.tydic.unicom.uoc.base.uocinst.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class ProcInstTaskAssignRecordPo extends BasePo{

	private static final long serialVersionUID = 1L;

	private String id;
	private String task_id;
	private String order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String assign_oper_no;
	private String assign_time;
	private String to_depart_no;
	private String to_oper_no;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
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
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getAssign_oper_no() {
		return assign_oper_no;
	}
	public void setAssign_oper_no(String assign_oper_no) {
		this.assign_oper_no = assign_oper_no;
	}
	public String getAssign_time() {
		return assign_time;
	}
	public void setAssign_time(String assign_time) {
		this.assign_time = assign_time;
	}
	public String getTo_depart_no() {
		return to_depart_no;
	}
	public void setTo_depart_no(String to_depart_no) {
		this.to_depart_no = to_depart_no;
	}
	public String getTo_oper_no() {
		return to_oper_no;
	}
	public void setTo_oper_no(String to_oper_no) {
		this.to_oper_no = to_oper_no;
	}
}
