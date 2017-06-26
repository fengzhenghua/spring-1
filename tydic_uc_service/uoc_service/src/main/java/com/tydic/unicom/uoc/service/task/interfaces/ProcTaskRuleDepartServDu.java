package com.tydic.unicom.uoc.service.task.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleDepartVo;

public interface ProcTaskRuleDepartServDu {
	/**
	 * 部门任务规则表 新增记录
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean create(ProcTaskRuleDepartVo vo) throws Exception;

	/**
	 * 部门任务规则表 删除记录
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean delete(ProcTaskRuleDepartVo vo) throws Exception;

	/**
	 * 部门任务规则表 修改记录
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean update(ProcTaskRuleDepartVo vo) throws Exception;

	/**
	 * 分页查询部门任务规则表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<ProcTaskRuleDepartVo> queryProcTaskRuleDepartByWeb(ProcTaskRuleDepartVo vo) throws Exception;

	/**
	 * 获得总行数
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int queryProcTaskRuleDepartCount(ProcTaskRuleDepartVo vo) throws Exception;

	/**
	 * 通过vo查询部门任务规则表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<ProcTaskRuleDepartVo> queryProcTaskRuleDepartByVo(ProcTaskRuleDepartVo vo) throws Exception;
}
