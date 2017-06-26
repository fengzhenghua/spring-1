package com.tydic.unicom.crawler.dao.po;

import com.tydic.unicom.crawler.common.BaseVoPo;

public class BasePo extends BaseVoPo{
	private static final long serialVersionUID = 1L;
	private String sql;
	//区分普通用户和管理用户使用，主要用在查询方面，指定数据范围
	private int admin=0;

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
