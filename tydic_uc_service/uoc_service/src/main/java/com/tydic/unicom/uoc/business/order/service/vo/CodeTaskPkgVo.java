package com.tydic.unicom.uoc.business.order.service.vo;

import java.util.List;

import com.tydic.unicom.webUtil.BaseVo;

public class CodeTaskPkgVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String pkg_id;
	private String pkg_name;
	private String create_time;
	private List<CodeTaskPkgAppVo> app_list;
	private List<CodeTaskPkgDesignVo> design_list;

	public String getPkg_id() {
		return pkg_id;
	}

	public void setPkg_id(String pkg_id) {
		this.pkg_id = pkg_id;
	}

	public String getPkg_name() {
		return pkg_name;
	}

	public void setPkg_name(String pkg_name) {
		this.pkg_name = pkg_name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public List<CodeTaskPkgAppVo> getApp_list() {
		return app_list;
	}

	public void setApp_list(List<CodeTaskPkgAppVo> app_list) {
		this.app_list = app_list;
	}

	public List<CodeTaskPkgDesignVo> getDesign_list() {
		return design_list;
	}

	public void setDesign_list(List<CodeTaskPkgDesignVo> design_list) {
		this.design_list = design_list;
	}

}
