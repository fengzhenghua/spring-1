package com.tydic.unicom.apc.service.order.interfaces;

import com.tydic.unicom.apc.business.order.vo.LogPayCsVo;

/**
 * <p>
 * </p>
 * 
 * @author JinXue 2014-9-15 下午7:58:55
 * @ClassName OperServ
 * @Description TODO	对用户的增加、删除、查询、更新和登录操作
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-15
 * @modify by reason:{方法名}:{原因}
 */
public interface LogPayCsServDu {

	public LogPayCsVo insertLogPayCs(LogPayCsVo logPayCsVo) throws Exception;
	
	public boolean updateLogPayCs(LogPayCsVo logPayCsVo) throws Exception;
	
	public LogPayCsVo queryLogPayCsByCsOrderId(String cs_order_id) throws Exception;
	
	public LogPayCsVo queryLogPayCsByPayCsId(String cs_order_id) throws Exception;
	
	public LogPayCsVo queryLogPayCsLikeCsOrderId(String cs_order_id) throws Exception;
	
	public void deleteLogPayCsByCsOrderId(String cs_order_id) throws Exception;
	
}
