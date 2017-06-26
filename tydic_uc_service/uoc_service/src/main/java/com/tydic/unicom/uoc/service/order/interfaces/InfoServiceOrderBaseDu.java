package com.tydic.unicom.uoc.service.order.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface InfoServiceOrderBaseDu {
	/**
	 * 1服务订单状态关系校验  1.1BASE0001
	 * @param serv_order_no
	 * @param oper_code
	 * @param state_code_new
	 * @return
	 */
	public UocMessage checkServiceOrderState(String serv_order_no,String oper_code,String state_code_new);
	/**
	 * 2服务订单环节关系校验  2.1BASE0002
	 * @param serv_order_no
	 * @param oper_code
	 * @param tache_code_new
	 * @return
	 */
	public UocMessage checkServiceOrderTache(String serv_order_no,String oper_code,String tache_code_new);
	/**15订单竣工服务 15.1BASE0015
	 * @param order_no
	 * @param oper_type
	 * @return
	 */
	public UocMessage createServiceOrderComplete(String order_no,String oper_type);
	
	/**
	 * BASE003230  订单中间数据生成
	 * @return
	 * @throws Exception
	 */
	public UocMessage createOrderIntermediateData(String serv_order_no,String order_type,String jsession_id) throws Exception;
}
