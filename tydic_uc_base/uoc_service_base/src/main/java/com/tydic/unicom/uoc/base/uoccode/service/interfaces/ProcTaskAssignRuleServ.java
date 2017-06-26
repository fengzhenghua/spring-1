package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.ProcTaskAssignRulePo;

public interface ProcTaskAssignRuleServ {

	/**
	 * 通过PROC_TASK_ASSIGN_RULE查询任务分配规则
	 * @param ProcTaskAssignRulePo
	 * @return
	 * @throws Exception
	 */
	public ProcTaskAssignRulePo queryByProcTaskAssignRulePo(ProcTaskAssignRulePo po) throws Exception;

	/**
	 * 对任务分配规则表	PROC_TASK_ASSIGN_RULE 进行新增操作
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean create(ProcTaskAssignRulePo po) throws Exception;

	/**
	 * 对任务分配规则表	PROC_TASK_ASSIGN_RULE 进行删除操作
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean delete(ProcTaskAssignRulePo po)throws Exception;

	/**
	 * 对任务分配规则表	PROC_TASK_ASSIGN_RULE 进行更新操作
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean update(ProcTaskAssignRulePo po) throws Exception;

	/**
	 * 查询任务分配规则表	PROC_TASK_ASSIGN_RULE	分页
	 * @param procInstTaskInstPo
	 * @return
	 * @throws Exception
	 */
	public List<ProcTaskAssignRulePo> queryProcTaskAssignRuleByPage(ProcTaskAssignRulePo po,int pageNo, int pageSize) throws Exception;

	/**
	 * 通过id查询任务分配规则表单条数据	PROC_TASK_ASSIGN_RULE
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public ProcTaskAssignRulePo queryProcTaskAssignRuleById(ProcTaskAssignRulePo po) throws Exception;

	/**
	 * 得到数据总行数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryProcTaskAssignRuleCount(ProcTaskAssignRulePo po) throws Exception;
}
