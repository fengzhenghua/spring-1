package com.tydic.unicom.apc.business.ofr.interfaces;

import com.tydic.unicom.apc.business.ofr.vo.CodeAreaVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface OppoCommonServiceServDu {

	/**
	 * (oppo)获取省份编码
	 * */
	public UocMessage getProvinceCode() throws Exception;
	
	/**
	 * 获取所有省份信息
	 * */
	public UocMessage getAllProvinceInfo() throws Exception;
	
	/**
	 * 获取区域信息
	 * */
	public UocMessage getAreaInfo(CodeAreaVo codeAreaVo) throws Exception;
	
	/**
	 * （oppo）根据订单中心服务订单号查询触点订单号
	 * */
	public UocMessage getApcOrderId(String serv_order_no) throws Exception;
	
	/**
	 * 中台回调更新触点订单信息
	 * */
	public UocMessage updateCallBackApcOrder(String json_info) throws Exception;
	
	/**
	 * 根据卡号号码查询触点信息
	 * @param sim_id
	 * @param acc_nbr
	 * @param contact_phone
	 * @return
	 * @throws Exception
	 */
	public UocMessage getApcInfoBySimAndDeviceNumber(String sim_id,String acc_nbr,String contact_phone) throws Exception;
}
