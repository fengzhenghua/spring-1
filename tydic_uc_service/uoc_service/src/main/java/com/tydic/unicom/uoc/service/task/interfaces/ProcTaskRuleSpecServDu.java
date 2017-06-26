package com.tydic.unicom.uoc.service.task.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleSpecVo;

public interface ProcTaskRuleSpecServDu {

	/**
	 * 任务特殊分配规则表 新增记录
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean create(ProcTaskRuleSpecVo vo) throws Exception;

	/**
	 * 任务特殊分配规则表 删除记录
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean delete(ProcTaskRuleSpecVo vo) throws Exception;

	/**
	 * 任务特殊分配规则表 修改记录
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean update(ProcTaskRuleSpecVo vo) throws Exception;

	/**
	 * 通过id查询任务特殊分配规则表
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public ProcTaskRuleSpecVo queryProcTaskRuleSpecById(ProcTaskRuleSpecVo vo) throws Exception;

	/**
	 * 分页查询任务特殊分配规则表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<ProcTaskRuleSpecVo> queryProcTaskRuleSpecByWeb(ProcTaskRuleSpecVo vo) throws Exception;

	/**
	 * 获得总行数
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int queryProcTaskRuleSpecCount(ProcTaskRuleSpecVo vo) throws Exception;

	/**
	 * 通过rule_id模糊查询工号组
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<ProcTaskRuleSpecVo> queryTaskRuleSpecGroupByRuleId(String vo) throws Exception;
}
