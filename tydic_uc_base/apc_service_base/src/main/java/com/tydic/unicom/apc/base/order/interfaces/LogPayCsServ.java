package com.tydic.unicom.apc.base.order.interfaces;

import java.util.List;

import com.tydic.unicom.apc.base.order.po.LogPayCsPo;


/**
 * <p></p>
 * @author huangweixing 2014-9-19 下午4:25:18
 * @ClassName OrderServ
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-19
 * @modify by reason:{方法名}:{原因}
 */
public interface LogPayCsServ {
	
	/**
	 * @author huangweixing 2014-9-22 上午11:29:36
	 * @Method: queryInfoOrderByOrderId
	 * @Description: TODO 订单Id查询订单信息
	 * @param infoOrder
	 * @return 
	 * @throws 
	 */

	public List<LogPayCsPo> queryLogPayCs(LogPayCsPo logPayCs);
	
	public List<LogPayCsPo> queryLogPayCsPc(LogPayCsPo logPayCs);

	public Boolean insertLogPayCs(LogPayCsPo logPayCs);
	
	public Boolean updateLogPayCs(LogPayCsPo logPayCs);
	
	public List<LogPayCsPo> queryLogPayCsByPayCsId(LogPayCsPo logPayCs);

	List<LogPayCsPo> queryLogPayCsListByCsOrderId(LogPayCsPo logPayCs);
	
	public LogPayCsPo queryLogPayCsByCsOrderId(String cs_order_id) throws Exception;
	
	public LogPayCsPo queryLogPayCsByservOrderNo(String serv_order_id) throws Exception;
	
	public LogPayCsPo queryLogPayCsLikeCsOrderId(String cs_order_id) throws Exception;

	public void deleteLogPayCsByCsOrderId(String cs_order_id) throws Exception;
}
