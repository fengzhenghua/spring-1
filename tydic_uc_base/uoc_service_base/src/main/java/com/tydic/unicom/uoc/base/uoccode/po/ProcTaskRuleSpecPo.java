package com.tydic.unicom.uoc.base.uoccode.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class ProcTaskRuleSpecPo extends BasePo {

	private static final long serialVersionUID = 1L;

	private String id;
	private String rule_id;
	private String target_oper_no;
	private String proportion;
	private String flag;

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

}
