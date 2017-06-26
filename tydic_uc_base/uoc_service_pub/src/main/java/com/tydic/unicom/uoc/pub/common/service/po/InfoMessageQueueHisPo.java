package com.tydic.unicom.uoc.pub.common.service.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoMessageQueueHisPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String order_id;
	private String order_type;
	private String province_code;
	private String area_code;
	private String part_month;
	private String service_code;
	private String queue_id;
	private String json_in;
	private String task_inst_object_json;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
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
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getQueue_id() {
		return queue_id;
	}
	public void setQueue_id(String queue_id) {
		this.queue_id = queue_id;
	}
	public String getJson_in() {
		return json_in;
	}
	public void setJson_in(String json_in) {
		this.json_in = json_in;
	}
	public String getTask_inst_object_json() {
		return task_inst_object_json;
	}
	public void setTask_inst_object_json(String task_inst_object_json) {
		this.task_inst_object_json = task_inst_object_json;
	}
}
