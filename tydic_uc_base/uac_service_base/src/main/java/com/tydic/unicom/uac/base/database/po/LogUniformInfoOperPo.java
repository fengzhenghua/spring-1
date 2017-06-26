package com.tydic.unicom.uac.base.database.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class LogUniformInfoOperPo extends BasePo{

	private static final long serialVersionUID = 1L;
	private String log_id;
	private String uniform_info_oper;// 统一工号
	private String operate_time;
	private String log_flag;
	private String oper_no;// 子工号
	private String jsessionid;
	private String log_ip;
	private String mac_type;
	private String comments;
	private String log_type;
	private String log_password;//记录修改密码成功后的新密码密文
	
	public String getLog_password() {
	    return log_password;
	}

	public void setLog_password(String log_password) {
	    this.log_password = log_password;
	}

	public String getLog_id() {
		return log_id;
	}
	
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	
	public String getUniform_info_oper() {
		return uniform_info_oper;
	}
	
	public void setUniform_info_oper(String uniform_info_oper) {
		this.uniform_info_oper = uniform_info_oper;
	}
	
	public String getOperate_time() {
		return operate_time;
	}
	
	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}
	
	public String getLog_flag() {
		return log_flag;
	}
	
	public void setLog_flag(String log_flag) {
		this.log_flag = log_flag;
	}
	
	public String getOper_no() {
		return oper_no;
	}
	
	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}
	
	public String getJsessionid() {
		return jsessionid;
	}
	
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}

	public String getLog_ip() {
		return log_ip;
	}

	public void setLog_ip(String log_ip) {
		this.log_ip = log_ip;
	}

	public String getMac_type() {
		return mac_type;
	}

	public void setMac_type(String mac_type) {
		this.mac_type = mac_type;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getLog_type() {
		return log_type;
	}

	public void setLog_type(String log_type) {
		this.log_type = log_type;
	}
}
