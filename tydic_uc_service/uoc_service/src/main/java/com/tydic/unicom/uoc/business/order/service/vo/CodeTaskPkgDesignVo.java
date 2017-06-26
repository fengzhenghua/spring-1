package com.tydic.unicom.uoc.business.order.service.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class CodeTaskPkgDesignVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String id;
	private String pkg_id;
	private String oper_code;
	private String product_id;
	private String tache_code;
	private String tache_name;
	private String num;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPkg_id() {
		return pkg_id;
	}

	public void setPkg_id(String pkg_id) {
		this.pkg_id = pkg_id;
	}

	public String getOper_code() {
		return oper_code;
	}

	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getTache_code() {
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}

	public String getTache_name() {
		return tache_name;
	}

	public void setTache_name(String tache_name) {
		this.tache_name = tache_name;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
}
