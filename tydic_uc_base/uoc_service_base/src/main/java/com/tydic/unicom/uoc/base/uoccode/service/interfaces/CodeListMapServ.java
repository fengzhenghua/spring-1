package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.CodeListMapPo;

public interface CodeListMapServ {

	/**
	 * 查询全部CodeListMap数据
	 * */
	public List<CodeListMapPo> queryCodeListMapAll() throws Exception;
}
