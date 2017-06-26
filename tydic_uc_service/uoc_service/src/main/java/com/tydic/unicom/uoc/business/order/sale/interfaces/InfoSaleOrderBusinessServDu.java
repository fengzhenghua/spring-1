package com.tydic.unicom.uoc.business.order.sale.interfaces;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface InfoSaleOrderBusinessServDu {
	/**
	 * 销售订单创建 UOC0003
	 * @param jsonStr
	 * @return
	 */
	public UocMessage createSaleOrder(ParaVo paraVo) throws Exception; 
	/**
	 * 销售订单修改UOC0004
	 * @param jsonStr
	 * @return
	 */
	public UocMessage updateSaleOrder(ParaVo paraVo) throws Exception;
	/**
	 * 销售订单确认UOC0005
	 * @param jsonStr
	 * @return
	 */
	public UocMessage confirmSaleOrder(ParaVo paraVo) throws Exception;
	/**
	 * 销售订单提交UOC0019
	 * @param jsonStr
	 * @return
	 */
	public UocMessage submitSaleOrder(ParaVo paraVo) throws Exception;
	/**
	 * 销售订单处理UOC0020
	 * @param jsonStr
	 * @return
	 */
	public UocMessage dealSaleOrder(ParaVo paraVo) throws Exception;
	
	/**
	 * 销售订批量修改信息UOC0021
	 * @param jsonStr
	 * @return
	 */
	public UocMessage updateSaleOrderBatch(ParaVo paraVo) throws Exception;
	/**
	 * 生成支付订单UOC0022
	 * @param jsonStr
	 * @return
	 */
	public UocMessage createInfoPayOrder(ParaVo paraVo) throws Exception;
	/**
	 * 销售订单收费结果通知 UOC0023
	 * @param jsonStr
	 * @return
	 */
	public UocMessage payForNotifyInfoSaleOrder(ParaVo paraVo) throws Exception;
	/**
	 * 订单撤单 UOC0007
	 * @param jsonStr
	 * @return
	 */
	public UocMessage cancelOrder(ParaVo paraVo) throws Exception;
	
	/**
	 * 订单数据备份  UOC0069
	 * @return
	 */
	public UocMessage createOrderBackUp(String total_num,String remainder) throws Exception;
	
	/**
	 * 后台按月赠款 UOC0082
	 * 每月定时自动赠款
	 */
	public UocMessage createGrantFeeTimer() throws Exception;
	
}
