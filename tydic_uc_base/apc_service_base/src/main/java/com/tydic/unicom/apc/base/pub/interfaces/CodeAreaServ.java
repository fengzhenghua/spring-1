package com.tydic.unicom.apc.base.pub.interfaces;

import java.util.List;

import com.tydic.unicom.apc.base.pub.po.CodeAreaPo;

public interface CodeAreaServ {

	/**
	 * 查询地区数据
	 * @param codeArea
	 * @return
	 */
	public List<CodeAreaPo> queryCodeAreaByParentAreaCode(CodeAreaPo codeArea) throws Exception;
	
	/**
	 * 查询所有省份数据
	 * @param
	 * @return
	 */
	public List<CodeAreaPo> queryCodeAreaAllProvinceInfo(CodeAreaPo codeArea) throws Exception;
}
