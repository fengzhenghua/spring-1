package com.tydic.unicom.uoc.business.order.service.interfaces;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.UocMessage;


public interface OrderModServDu {

	/**
	 * UOC0001	服务订单状态关系维护
	 */
	public UocMessage syncOrdModStateRule(ParaVo paraVo) throws Exception;

	/**
	 * UOC0002	服务订单环节关系维护
	 */
	public UocMessage syncOrdModTacheRule(ParaVo paraVo)throws Exception;

	/**
	 * UOC0012 订单模板维护
	 */
	public UocMessage syncOrdMod(ParaVo paraVo)throws Exception;

	/**
	 * UOC0013 订单模板应用表维护
	 */
	public UocMessage syncOrdModApp(ParaVo paraVo) throws Exception;


	/**
	 * UOC0026	服务订单状态关系查询，分页
	 */
	public UocMessage searchOrdModStateRuleList(ParaVo paraVo) throws Exception;

	/**
	 * UOC0027	服务订单环节关系查询，分页
	 */
	public UocMessage searchOrdModTacheRuleList(ParaVo paraVo) throws Exception;

	/**
	 * UOC0028	订单模板查询查询，分页
	 */
	public UocMessage searchOrdMod(ParaVo paraVo)throws Exception;

	/**
	 * UOC0061	任务包维护
	 */
	public UocMessage syncCodeTaskPkg(ParaVo paraVo)throws Exception;

	/**
	 * UOC00XX	任务包查询 分页
	 */
	public UocMessage searchCodeTaskPkg(ParaVo paraVo)throws Exception;

	/**
	 * UOCXXXX	订单参数与数据库表字段关系表查询，分页
	 */
	public UocMessage searchOrdModParaFieldRelationList(ParaVo paraVo) throws Exception;

	/**
	 * UOCXXXX	订单模板应用表查询，分页
	 */
	public UocMessage searchOrdModAppList(ParaVo paraVo) throws Exception;

	/**
	 * UOCXXXX	订单模板参数校验规则表查询，分页
	 */
	public UocMessage searchOrdModAttrCheckRuleList(ParaVo paraVo) throws Exception;

	/**
	 * UOCXXXX	模板参数定义表查询，分页
	 */
	public UocMessage searchOrdModAttrDefineList(ParaVo paraVo) throws Exception;

	/**
	 * UOCXXXX	订单模板校验规则表查询，分页
	 */
	public UocMessage searchOrdModCheckRuleList(ParaVo paraVo) throws Exception;

	/**
	 * UOC0065	任务默认分配规则维护
	 */
	public UocMessage syncTaskAssignRule(ParaVo paraVo)throws Exception;

	/**
	 *  UOC0066	任务默认分配规则查询
	 */
	public UocMessage searchTaskAssignRule(ParaVo paraVo)throws Exception;

	/**
	 * 分配规则页面查询工号组
	 */
	public UocMessage queryOperNoGroup(String rule_id) throws Exception;

}
