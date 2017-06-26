package com.tydic.unicom.uoc.service.task.interfaces;

import java.util.Map;

import com.tydic.unicom.webUtil.UocMessage;

public interface CheckArtificialTaskServDu {

	/**
	 * BASE0025 人工任务处理校验 1、通过订单号人工任务实例表，没有状态为100、101的数据则报错，取出环节；
	 * 2、有数据则再根据传入的工号权限信息，以及上面查出的环节查询环节对应角色表，判断当前工号是否有执行当前任务权限，没有权限直接报错
	 * 3、最后还要根据传入的工号信息判断任务分配工号表，是否可处理此任务，若此表查不到数据则直接报错
	 * @param order_no
	 * @param tache_code
	 * @param oper_info
	 * @return
	 * @throws Exception
	 */
	public UocMessage checkArtificialTaskProcess(String order_no, String tache_code, Map<String, Object> oper_info) throws Exception;
}
