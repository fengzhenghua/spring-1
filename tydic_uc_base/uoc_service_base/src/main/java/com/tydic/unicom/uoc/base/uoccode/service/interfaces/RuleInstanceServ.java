package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.RuleInstancePo;

public interface RuleInstanceServ {

	/**
	 * 查询RuleInstance全部数据
	 * */
	public List<RuleInstancePo> queryRuleInstanceAll() throws Exception;
}
