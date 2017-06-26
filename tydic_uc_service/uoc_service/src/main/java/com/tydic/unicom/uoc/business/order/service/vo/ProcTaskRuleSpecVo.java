package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ProcTaskRuleSpecVo extends BaseVo {

	private static final long serialVersionUID = 7105323393958877602L;

	private String id;
	private String rule_id;
	private String target_oper_no;
	private String proportion;
	private String flag;

	private String departProportion;
	private String belong_depart_no;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getTarget_oper_no() {
		return target_oper_no;
	}
	public void setTarget_oper_no(String target_oper_no) {
		this.target_oper_no = target_oper_no;
	}
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDepartProportion() {
		return departProportion;
	}

	public void setDepartProportion(String departProportion) {
		this.departProportion = departProportion;
	}

	public String getBelong_depart_no() {
		return belong_depart_no;
	}

	public void setBelong_depart_no(String belong_depart_no) {
		this.belong_depart_no = belong_depart_no;
	}
	@Override
	public String toString() {
		return "ProcTaskRuleSpecVo [id=" + id + ", rule_id=" + rule_id
				+ ", target_oper_no=" + target_oper_no + ", proportion="
				+ proportion + ", flag=" + flag + ", toString()="
				+ super.toString() + "]";
	}

}
