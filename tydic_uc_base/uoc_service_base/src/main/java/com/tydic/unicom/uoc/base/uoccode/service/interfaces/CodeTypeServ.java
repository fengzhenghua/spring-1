package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.CodeTypePo;

public interface CodeTypeServ {

	/**
	 * 查询CodeType全数据
	 * */
	public List<CodeTypePo> queryCodeTypeAll() throws Exception;
}
