package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.ProcTaskRuleDepartPo;

public interface ProcTaskRuleDepartServ {
	/**
	 * 通过PO查询
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<ProcTaskRuleDepartPo> queryProcTaskRuleDepartByPo(ProcTaskRuleDepartPo po) throws Exception;

	/**
	 * 部门任务规则表 新增记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean create(ProcTaskRuleDepartPo po) throws Exception;

	/**
	 * 部门任务规则表 删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean delete(ProcTaskRuleDepartPo po) throws Exception;

	/**
	 * 部门任务规则表 修改记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean update(ProcTaskRuleDepartPo po) throws Exception;

	/**
	 * 分页查询部门任务规则表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<ProcTaskRuleDepartPo> queryProcTaskRuleDepartByWeb(ProcTaskRuleDepartPo po) throws Exception;

	/**
	 * 获得总行数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryProcTaskRuleDepartCount(ProcTaskRuleDepartPo po) throws Exception;
}
