package com.tydic.unicom.uoc.pub.common.service.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class ActivemqSendPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String json_in;
	private String service_code;
	private String order_type;
	private String order_id;
	private String province_code;
	private TaskInst taskInst;
	
	public TaskInst getTaskInst() {
		return taskInst;
	}
	public void setTaskInst(TaskInst taskInst) {
		this.taskInst = taskInst;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getJson_in() {
		return json_in;
	}
	public void setJson_in(String json_in) {
		this.json_in = json_in;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
}
