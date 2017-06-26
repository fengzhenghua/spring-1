package com.tydic.unicom.uac.business.common.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.base.database.po.RuleOperRolePo;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;
import com.tydic.unicom.uac.business.common.vo.RuleOperRoleVo;

public interface RuleOperRoleServDu {

	public List<RuleOperRoleVo> queryRuleOperRoleByInfoOper(InfoOperVo infoOperVo) throws Exception;
	
	public List<RuleOperRoleVo> queryRuleOperRoleByOperNoOrRoleId(RuleOperRoleVo ruleOperRoleVo)throws Exception;;
	
	public boolean createRuleOperRole(RuleOperRoleVo vo) throws Exception;
	
	public boolean deleteRuleOperRole(RuleOperRoleVo vo) throws Exception;
	
	public boolean updateRuleOperRole(RuleOperRoleVo vo) throws Exception;
}
