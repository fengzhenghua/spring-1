package com.tydic.unicom.apc.business.order.interfaces;

import com.tydic.unicom.apc.business.order.vo.OppoOrderUpdateResVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface OppoOrderServiceServDu {

	/**
	 * （oppo）创建订单，注：包含向订单属性表和订单表插入数据
	 * */
	public UocMessage createOppoOrderInfo(String oper_no,String tele_type,String order_source) throws Exception;
	
	/**
	 * (oppo)更新订单信息 注：包含订单属性表
	 * */
	public UocMessage updateOppoOrderInfo(OppoOrderUpdateResVo oppoOrderUpdateResVo,String order_id) throws Exception;
	
	/**
	 * (oppo)查询中台订单信息
	 * */
	public UocMessage queryOrderInfoFromUoc(String sim_id, String acc_nbr,String contact_tel,String oper_no) throws Exception;
	
	/**
	 * (oppo)更新中台订单信息
	 * */
	public UocMessage updateOrderInfoFromUoc(String order_id) throws Exception;
	
	/**
	 * 创建触点订单（注：包含向订单属性表插入数据）
	 * */
	public UocMessage createApOrder(OppoOrderUpdateResVo oppoOrderUpdateResVo) throws Exception;
	
	/**
	 * 获取触点订单属性值
	 * */
	public UocMessage getApOrderAttrInfo(String apOrderId) throws Exception;
	
}
