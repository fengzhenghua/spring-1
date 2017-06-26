package com.tydic.unicom.apc.base.pub.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class ApDeveloperPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String ap_id;
	private String developer_no;
	private String developer_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAp_id() {
		return ap_id;
	}
	public void setAp_id(String ap_id) {
		this.ap_id = ap_id;
	}
	public String getDeveloper_no() {
		return developer_no;
	}
	public void setDeveloper_no(String developer_no) {
		this.developer_no = developer_no;
	}
	public String getDeveloper_name() {
		return developer_name;
	}
	public void setDeveloper_name(String developer_name) {
		this.developer_name = developer_name;
	}
	
	
}
