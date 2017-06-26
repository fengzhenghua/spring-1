package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.ProcTaskRuleSpecPo;

public interface ProcTaskRuleSpecServ {

	/**
	 * 通过PO target_oper_no(rule_id)查询
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<ProcTaskRuleSpecPo> queryProcTaskRuleSpecByPo(ProcTaskRuleSpecPo po) throws Exception;

	/**
	 * 任务特殊分配规则表 新增记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean create(ProcTaskRuleSpecPo po) throws Exception;

	/**
	 * 任务特殊分配规则表 删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean delete(ProcTaskRuleSpecPo po) throws Exception;

	/**
	 * 任务特殊分配规则表 修改记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean update(ProcTaskRuleSpecPo po) throws Exception;

	/**
	 * 通过id查询任务特殊分配规则表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public ProcTaskRuleSpecPo queryProcTaskRuleSpecById(ProcTaskRuleSpecPo po) throws Exception;

	/**
	 * 分页查询任务特殊分配规则表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<ProcTaskRuleSpecPo> queryProcTaskRuleSpecByWeb(ProcTaskRuleSpecPo po) throws Exception;

	/**
	 * 获得总行数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryProcTaskRuleSpecCount(ProcTaskRuleSpecPo po) throws Exception;

	/**
	 * 通过rule_id查询工号组
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<ProcTaskRuleSpecPo> queryTaskRuleSpecGroupByRuleId(ProcTaskRuleSpecPo po) throws Exception;
}
