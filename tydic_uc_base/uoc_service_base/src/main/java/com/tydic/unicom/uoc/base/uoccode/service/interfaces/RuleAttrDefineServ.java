package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.RuleAttrDefinePo;

public interface RuleAttrDefineServ {

	/**
	 * 查询RuleAttrDefine全部数据
	 * */
	public List<RuleAttrDefinePo> queryRuleAttrDefineAll() throws Exception;
}
