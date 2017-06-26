package com.tydic.unicom.upc.vo.code;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoPayParaVo extends BaseVo {

	private static final long serialVersionUID = -8686401702853112863L;

	private String pay_para_id;
	private String para_name;
	private Date create_time;
	private Date update_time;
	private String state;
	private String busi_id;
	
	private List<InfoPayParaAttrVo> attrList = new ArrayList<>();

	public String getPay_para_id() {
		return pay_para_id;
	}

	public void setPay_para_id(String pay_para_id) {
		this.pay_para_id = pay_para_id;
	}

	public String getPara_name() {
		return para_name;
	}

	public void setPara_name(String para_name) {
		this.para_name = para_name;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBusi_id() {
		return busi_id;
	}

	public void setBusi_id(String busi_id) {
		this.busi_id = busi_id;
	}

	public List<InfoPayParaAttrVo> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<InfoPayParaAttrVo> attrList) {
		this.attrList = attrList;
	}
	
}
