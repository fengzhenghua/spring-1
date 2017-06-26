package com.tydic.unicom.apc.business.pub.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class BusiSystemParaVo  extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String local_net;
	private String para_type;
	private String para_code;
	private String para_name;
	private String para_value1;
	private String para_value2;
	private String para_value3;
	private String comments;
	private String oper_no;
	private String uniform_oper_id;
	public String getLocal_net() {
		return local_net;
	}
	public void setLocal_net(String local_net) {
		this.local_net = local_net;
	}
	public String getPara_type() {
		return para_type;
	}
	public void setPara_type(String para_type) {
		this.para_type = para_type;
	}
	public String getPara_code() {
		return para_code;
	}
	public void setPara_code(String para_code) {
		this.para_code = para_code;
	}
	public String getPara_name() {
		return para_name;
	}
	public void setPara_name(String para_name) {
		this.para_name = para_name;
	}
	public String getPara_value1() {
		return para_value1;
	}
	public void setPara_value1(String para_value1) {
		this.para_value1 = para_value1;
	}
	public String getPara_value2() {
		return para_value2;
	}
	public void setPara_value2(String para_value2) {
		this.para_value2 = para_value2;
	}
	public String getPara_value3() {
		return para_value3;
	}
	public void setPara_value3(String para_value3) {
		this.para_value3 = para_value3;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getOper_no() {
		return oper_no;
	}
	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}
	public String getUniform_oper_id() {
		return uniform_oper_id;
	}
	public void setUniform_oper_id(String uniform_oper_id) {
		this.uniform_oper_id = uniform_oper_id;
	}

}
