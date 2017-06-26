package com.tydic.unicom.apc.business.pub.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ApParaVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String jsession_id;
	private String json_info;
	private String oper_type;
	
	@Override
	public String toString() {
		return "ApParaVo [jsession_id=" + jsession_id + ", json_info=" + json_info + ", oper_type=" + oper_type + "]";
	}
	public String getJsession_id() {
		return jsession_id;
	}
	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}
	public String getJson_info() {
		return json_info;
	}
	public void setJson_info(String json_info) {
		this.json_info = json_info;
	}
	public String getOper_type() {
		return oper_type;
	}
	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}
	
	
}
