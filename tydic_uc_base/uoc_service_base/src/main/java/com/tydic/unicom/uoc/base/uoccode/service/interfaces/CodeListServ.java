package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.CodeListPo;

public interface CodeListServ {

	/**
	 * 根据typeCode查询
	 * */
	public CodeListPo queryCodeListByTypeCode(CodeListPo codeListPo) throws Exception;
	
	/**
	 * 查询CodeList全部数据
	 * */
	public List<CodeListPo> queryCodeListAll() throws Exception;
	/**
	 * 更新CodeList数据中时间戳
	 */
	public boolean update(CodeListPo codeListPo) throws Exception;
}
