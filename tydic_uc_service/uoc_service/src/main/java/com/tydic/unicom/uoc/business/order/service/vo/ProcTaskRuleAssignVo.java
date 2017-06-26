package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ProcTaskRuleAssignVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String id;
	private String province_code;
	private String area_code;
	private String target_depart_no;
	private String target_oper_no;
	private String tache_code;
	private String oper_code;
	private String accept_oper_no;
	private String accept_depart_no;
	private String ext_cond1;
	private String ext_cond2;
	private String ext_cond3;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTarget_depart_no() {
		return target_depart_no;
	}

	public void setTarget_depart_no(String target_depart_no) {
		this.target_depart_no = target_depart_no;
	}

	public String getTarget_oper_no() {
		return target_oper_no;
	}

	public void setTarget_oper_no(String target_oper_no) {
		this.target_oper_no = target_oper_no;
	}

	public String getTache_code() {
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}

	public String getOper_code() {
		return oper_code;
	}

	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}

	public String getAccept_oper_no() {
		return accept_oper_no;
	}

	public void setAccept_oper_no(String accept_oper_no) {
		this.accept_oper_no = accept_oper_no;
	}

	public String getAccept_depart_no() {
		return accept_depart_no;
	}

	public void setAccept_depart_no(String accept_depart_no) {
		this.accept_depart_no = accept_depart_no;
	}

	public String getExt_cond1() {
		return ext_cond1;
	}

	public void setExt_cond1(String ext_cond1) {
		this.ext_cond1 = ext_cond1;
	}

	public String getExt_cond2() {
		return ext_cond2;
	}

	public void setExt_cond2(String ext_cond2) {
		this.ext_cond2 = ext_cond2;
	}

	public String getExt_cond3() {
		return ext_cond3;
	}

	public void setExt_cond3(String ext_cond3) {
		this.ext_cond3 = ext_cond3;
	}

}
