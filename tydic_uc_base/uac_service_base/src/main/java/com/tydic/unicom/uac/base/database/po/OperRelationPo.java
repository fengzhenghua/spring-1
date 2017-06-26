package com.tydic.unicom.uac.base.database.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class OperRelationPo extends BasePo{

	private static final long serialVersionUID = 1L;

	private String uniform_info_oper;// 统一工号

	private String info_oper;// 原工号
	private String login_date;// 最近生效时间
	private String local_net;// 最近生效时间
	
	private String old_uniform_info_oper;
	private String old_info_oper;
	
	private String relation_oper_id;
	private String jsessionid;
	private String key_id;
	private String bss_flag;//1:从BSS查询工号，
	
	public String getUniform_info_oper() {
		return uniform_info_oper;
	}
	
	public void setUniform_info_oper(String uniform_info_oper) {
		this.uniform_info_oper = uniform_info_oper;
	}
	
	public String getInfo_oper() {
		return info_oper;
	}
	
	public void setInfo_oper(String info_oper) {
		this.info_oper = info_oper;
	}
	
	public String getLogin_date() {
		return login_date;
	}
	
	public void setLogin_date(String login_date) {
		this.login_date = login_date;
	}
	
	public String getOld_uniform_info_oper() {
		return old_uniform_info_oper;
	}
	
	public void setOld_uniform_info_oper(String old_uniform_info_oper) {
		this.old_uniform_info_oper = old_uniform_info_oper;
	}
	
	public String getOld_info_oper() {
		return old_info_oper;
	}
	
	public void setOld_info_oper(String old_info_oper) {
		this.old_info_oper = old_info_oper;
	}

	public String getLocal_net() {
		return local_net;
	}

	public void setLocal_net(String local_net) {
		this.local_net = local_net;
	}

	public String getRelation_oper_id() {
		return relation_oper_id;
	}

	public void setRelation_oper_id(String relation_oper_id) {
		this.relation_oper_id = relation_oper_id;
	}

	public String getJsessionid() {
		return jsessionid;
	}

	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}
	
	public String getKey_id() {
		return key_id;
	}

	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}

	public String getBss_flag() {
		return bss_flag;
	}

	public void setBss_flag(String bss_flag) {
		this.bss_flag = bss_flag;
	}
}
