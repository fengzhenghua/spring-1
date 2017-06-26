package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.uoc.business.common.vo.LogisticsReportVo;
import com.tydic.unicom.uoc.business.common.vo.ServOrderListESInVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface EsReportDataServDu {
	/**
	 * 物流明细报表中间数据生成  UOC0067
	 */
	public UocMessage createLogisticsDetailData() throws Exception;
	/**
	 * 物流明细报表查询 UOC0068
	 */
	public UocMessage queryLogisticsDetailData(LogisticsReportVo vo) throws Exception;
	/**
	 * 服务订单列表查询 (ES版) UOC0074
	 */
	public UocMessage queryServOrderListES(ServOrderListESInVo vo) throws Exception;
	/**
	 * 服务订单详情查询(ES版) UOC0075
	 */
	public UocMessage queryOrderDetailES(String jsession_id,String serv_order_no) throws Exception;
	/**
	 * 订单明细报表查询  UOC0076
	 */
	public UocMessage queryOrderDetailReport(LogisticsReportVo vo) throws Exception;
	/**
	 * 调用BASE32服务(ES)
	 */
	public UocMessage createOrderIntermediateData(String serv_order_no,String order_type,String jsession_id) throws Exception;
	/**
	 * 
	 */
}
