package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.RuleTypePo;

public interface RuleTypeServ {

	/**
	 * 查询RuleType全部数据
	 * */
	public List<RuleTypePo> queryRuleTypeAll() throws Exception;
}
