package com.tydic.unicom.service.workFlow.service.interfaces;

import java.util.List;

import com.tydic.unicom.service.workFlow.po.ProgressList;

public interface JbpmQueryServ {
	/**
	 * 
	 * @author yangwen 2014-8-7 上午10:31:27
	 * @Method: getProgressList 
	 * @Description: TODO 获取工单已完成和正在进行的状态动作（单独从jbpm4_hist_actinst表获取）
	 * @param procInstanceId 流程实例ID
	 * @return List<ProgressList> 主要返回当前步骤的订单状态，当前和已完成状态标识、状态起始时间等
	 * @throws
	 */
	public List<ProgressList> getProgressList(String procInstanceId);
	
	/**
	 * 
	 * @author yangwen 2014-8-28 下午4:40:30
	 * @Method: getDataSourceId 
	 * @Description: TODO 获取具体定位的数据源ID
	 * @param jbpmKey 如：WF_ORDER 订单流程定义KEY,WF_APPROVE 实名认证流程定义KEY 等
	 * @param keyId 各个流程定义的关键ID,如订单流程则为orderId,如客户实名认证流程则为custId等
	 * @return dataSourceId 返回对应的数据源ID
	 * @throws
	 */
	public String getDataSourceId(String jbpmKey,String keyId);
}
