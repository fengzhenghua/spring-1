package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.base.database.po.RuleOperRolePo;

public interface RuleOperRoleServ {
	
	/**
	 * 获取角色ID和角色名称
	 * @param infoOperPo
	 * @return
	 */
	public List<RuleOperRolePo> queryRuleOperRoleNameByInfoOper(InfoOperPo infoOperPo);
	
	/**
	 * 获取 中台角色 
	 * @param createOperatorId 创建者ID，默认传 UOC
	 * @return 角色ID 和 角色名称
	 */
	public List<RuleOperRolePo> queryRuleOperRoleByCreateOperatorId(String createOperatorId);
	
	public List<RuleOperRolePo> queryRuleOperRoleByInfoOper(InfoOperPo infoOperPo);
	
	public List<RuleOperRolePo> queryRuleOperRoleByOperNoOrRoleId(RuleOperRolePo po);
	
	public boolean createRuleOperRole(RuleOperRolePo po);
	
	public boolean deleteRuleOperRole(RuleOperRolePo po);
	
	public boolean updateRuleOperRole(RuleOperRolePo po);
}
