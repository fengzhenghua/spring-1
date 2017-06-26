package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.RuleDefinePo;

public interface RuleDefineServ {

	/**
	 * 查询RuleDefine全部数据
	 * */
	public List<RuleDefinePo> queryRuleDefineAll() throws Exception;
}
