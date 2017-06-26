package com.tydic.unicom.uoc.business.order.service.interfaces;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.UocMessage;
import com.tydic.unicom.uoc.business.order.service.vo.GetServOrderNoVo;

public interface ServiceOrderServDu {

	/**
	 * UOC0008 服务订单处理 ,同步方法
	 */
	public UocMessage syncServiceOrder(String all_json_info)throws Exception;

	/**
	 * UOC0008 服务订单处理 ,异步方法
	 */
	public UocMessage syncNoneServiceOrder(ParaVo paraVo)throws Exception;

	/**
	 * UOC0009 服务订单更改
	 */
	public UocMessage changeServiceOrder(ParaVo paraVo) throws Exception;

	/**
	 * UOC0038 服务订单竣工率统计
	 * @param depart_no,oper_no,oper_code,prod_code,start_time,end_time
	 * @return
	 * @throws Exception
	 */
	public UocMessage getServiceOrderCompletionRate(ParaVo paraVo) throws Exception;

	/**
	 * UOC0052 环节接口调用(支持重发)
	 * @param jsession_id
	 * @param serv_order_no
	 * @param tache_code
	 * @return
	 * @throws Exception
	 */
	public UocMessage getOrderTacheCall(String jsession_id, String serv_order_no, String tache_code) throws Exception;

	/**
	 * UOC0053 服务订单撤单率统计
	 * @param depart_no,oper_no,oper_code,prod_code,start_time,end_time
	 * @return
	 * @throws Exception
	 */
	public UocMessage getServiceOrderCancelRate(ParaVo paraVo) throws Exception;
	/**
	 * UOC0060获取服务订单号
	 * @param jsession_id,sim_id,acc_nbr
	 * @return
	 * @throws Exception
	 */
	public UocMessage getServiceOrderNo(GetServOrderNoVo vo) throws Exception;

}
