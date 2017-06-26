package com.tydic.unicom.uoc.base.uoccode.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class CodeTaskPkgAppPo extends BasePo {

	private static final long serialVersionUID = 1L;

	private String id;
	private String pkg_id;
	private String role_id;

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

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
}
