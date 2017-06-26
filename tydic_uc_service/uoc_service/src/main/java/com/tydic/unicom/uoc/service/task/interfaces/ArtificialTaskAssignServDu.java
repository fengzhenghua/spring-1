package com.tydic.unicom.uoc.service.task.interfaces;

import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.webUtil.UocMessage;


public interface ArtificialTaskAssignServDu {

	/**
	 * BASE0024 人工任务默认分配
	 * @param order_id
	 * @param accept_oper
	 * @return
	 * @throws Exception
	 */
	public UocMessage taskDefaultAssignment(ProcInstTaskInstVo procInstTaskInstVo) throws Exception;

	/**
	 * BASE0028 任务分配
	 * @param order_id
	 * @param target_oper
	 * @param target_oper_depart
	 * @param target_depart
	 * @param accept_oper
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public UocMessage taskOperateAssignment(String order_id, String target_oper, String target_oper_depart, String target_depart,
			String accept_oper, String flag) throws Exception;
}
