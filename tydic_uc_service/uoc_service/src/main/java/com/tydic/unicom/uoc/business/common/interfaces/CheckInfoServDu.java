package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.uoc.business.common.vo.InfoIDCardVo;
import com.tydic.unicom.uoc.business.common.vo.LivingCheckVo;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface CheckInfoServDu {
	/**
	 * 75	国政通接口(重庆) UOC0049
	 * @param vo
	 * @param session
	 * @return
	 */
	public UocMessage createGztValid(InfoIDCardVo vo,String path) throws Exception;
	/**
	 * 	活体自动审核结果通知  UOC0051
	 * @param vo
	 * @return
	 */
	public UocMessage createLivingChecKResult(LivingCheckVo vo) throws Exception;
	/**
	 * 获取证件信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage getCertInfo(ParaVo vo) throws Exception;
	/**
	 * 获取工号信息UOC0056
	 * @return
	 */
	public UocMessage getOperInfo(String jsession_id) throws Exception;
	/**
	 * 106	活体认证请求UOC0077
	 * @return
	 */
	public UocMessage createNewLivingChecK(String jsession_id,String serv_order_no,String json_info,String flow_flag,String threshold_low,String threshold_high)throws Exception;
	/**
	 * 	国政通活体认证UOC0083
	 * @return
	 */
	public UocMessage createNewLivingCheckGzt(String jsession_id,String serv_order_no,String json_info,String flow_flag,String threshold_low,String threshold_high)throws Exception;
}
