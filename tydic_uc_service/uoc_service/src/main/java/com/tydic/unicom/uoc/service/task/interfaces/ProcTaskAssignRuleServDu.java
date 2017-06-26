package com.tydic.unicom.uoc.service.task.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleAssignVo;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.task.vo.ProcTaskAssignRuleVo;

public interface ProcTaskAssignRuleServDu {

	public ProcTaskAssignRuleVo queryTaskAssignRuleByTaskInstFromRedis(ProcInstTaskInstVo vo) throws Exception;

	/**
	 * 任务分配规则表		新增
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean create(ProcTaskRuleAssignVo vo) throws Exception;

	/**
	 * 任务分配规则表		删除
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean delete(ProcTaskRuleAssignVo vo) throws Exception;

	/**
	 * 任务分配规则表		修改
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean update(ProcTaskRuleAssignVo vo) throws Exception;

	/**
	 * 任务分配规则表		分页查询
	 * @param vo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<ProcTaskRuleAssignVo> queryProcTaskAssignRuleByPage(ProcTaskRuleAssignVo vo,int pageNo, int pageSize) throws Exception;

	/**
	 * 任务分配规则表		通过id查询
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public ProcTaskRuleAssignVo queryProcTaskAssignRuleById(ProcTaskRuleAssignVo vo) throws Exception;

	/**
	 * 任务分配规则表		获取总行数
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int queryProcTaskAssignRuleCount(ProcTaskRuleAssignVo vo) throws Exception;

	/**
	 * 任务分配规则表 通过tache_code查询
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public ProcTaskRuleAssignVo queryProcTaskAssignRuleByVo(ProcTaskRuleAssignVo vo) throws Exception;
}
