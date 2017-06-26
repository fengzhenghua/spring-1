package com.tydic.unicom.uoc.service.common.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class SequencesVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String seq_code;
	private String province_code;
	private String area_code;
	private String seq_desc;
	private String seq_head;
	private String seq_time_str;
	private String seq_tail_type;
	private String seq_tail_lenth;
	private String seq_tail_begin;
	private String seq_tail_curr;
	private String seq_tail_step;
	private String seq_tail_end;
	private String seq_tail_cir_type;
	private String seq_tail_rightjustify;
	private int stepUsed = 0;
	private int currNumber = 0;
	private String resetFlag = "";
	
	public int getStepUsed() {
		return stepUsed;
	}
	public void setStepUsed(int stepUsed) {
		this.stepUsed = stepUsed;
	}
	public int getCurrNumber() {
		return currNumber;
	}
	public void setCurrNumber(int currNumber) {
		this.currNumber = currNumber;
	}
	public String getResetFlag() {
		return resetFlag;
	}
	public void setResetFlag(String resetFlag) {
		this.resetFlag = resetFlag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(String seq_code) {
		this.seq_code = seq_code;
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
	public String getSeq_desc() {
		return seq_desc;
	}
	public void setSeq_desc(String seq_desc) {
		this.seq_desc = seq_desc;
	}
	public String getSeq_head() {
		return seq_head;
	}
	public void setSeq_head(String seq_head) {
		this.seq_head = seq_head;
	}
	public String getSeq_time_str() {
		return seq_time_str;
	}
	public void setSeq_time_str(String seq_time_str) {
		this.seq_time_str = seq_time_str;
	}
	public String getSeq_tail_type() {
		return seq_tail_type;
	}
	public void setSeq_tail_type(String seq_tail_type) {
		this.seq_tail_type = seq_tail_type;
	}
	public String getSeq_tail_lenth() {
		return seq_tail_lenth;
	}
	public void setSeq_tail_lenth(String seq_tail_lenth) {
		this.seq_tail_lenth = seq_tail_lenth;
	}
	public String getSeq_tail_begin() {
		return seq_tail_begin;
	}
	public void setSeq_tail_begin(String seq_tail_begin) {
		this.seq_tail_begin = seq_tail_begin;
	}
	public String getSeq_tail_curr() {
		return seq_tail_curr;
	}
	public void setSeq_tail_curr(String seq_tail_curr) {
		this.seq_tail_curr = seq_tail_curr;
	}
	public String getSeq_tail_step() {
		return seq_tail_step;
	}
	public void setSeq_tail_step(String seq_tail_step) {
		this.seq_tail_step = seq_tail_step;
	}
	public String getSeq_tail_end() {
		return seq_tail_end;
	}
	public void setSeq_tail_end(String seq_tail_end) {
		this.seq_tail_end = seq_tail_end;
	}
	public String getSeq_tail_cir_type() {
		return seq_tail_cir_type;
	}
	public void setSeq_tail_cir_type(String seq_tail_cir_type) {
		this.seq_tail_cir_type = seq_tail_cir_type;
	}
	public String getSeq_tail_rightjustify() {
		return seq_tail_rightjustify;
	}
	public void setSeq_tail_rightjustify(String seq_tail_rightjustify) {
		this.seq_tail_rightjustify = seq_tail_rightjustify;
	}
	
}
