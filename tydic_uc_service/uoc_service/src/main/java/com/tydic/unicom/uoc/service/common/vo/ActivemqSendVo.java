package com.tydic.unicom.uoc.service.common.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ActivemqSendVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String json_in;
	private String service_code;
	private String order_type;
	private String order_id;
	
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
}
