package com.tydic.unicom.apc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.apc.base.order.po.InfoOrderAttrPo;
import com.tydic.unicom.webUtil.UocMessage;

public interface InfoOrderAttrServDu {

	/**
	 * 插入订单属性表（多条数据）
	 * */
	public UocMessage updateInfoOrderAttrList(List<InfoOrderAttrPo> list) throws Exception;
	
	/**
	 * 更新订单属性表（多条数据）
	 * */
	public UocMessage addInfoOrderAttrList(List<InfoOrderAttrPo> list) throws Exception;
	
	public UocMessage getApOrderAttrInfo(String apOrderId) throws Exception;
}
