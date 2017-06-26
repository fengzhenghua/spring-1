package com.tydic.unicom.uoc.base.uoccode.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class ProcTaskRuleDepartPo extends BasePo {

	private static final long serialVersionUID = 1L;

	private String depart_no;
	private String rule_id;
	private String proportion;

	public String getDepart_no() {
		return depart_no;
	}

	public void setDepart_no(String depart_no) {
		this.depart_no = depart_no;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

}
