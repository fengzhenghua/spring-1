package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.CodeListTabFieldPo;

public interface CodeListTabFieldServ {

	/**
	 * 查询CodeListTabField全数据
	 * */
	public List<CodeListTabFieldPo> queryCodeListTabFieldAll() throws Exception;
}
