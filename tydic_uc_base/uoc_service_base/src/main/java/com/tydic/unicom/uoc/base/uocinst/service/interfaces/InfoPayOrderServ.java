package com.tydic.unicom.uoc.base.uocinst.service.interfaces;


import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;

public interface InfoPayOrderServ {
	
	public InfoPayOrderPo getInfoPayOrderByPayOrderNo(InfoPayOrderPo po) throws Exception;
	
	public boolean updateInfoPayOrder(InfoPayOrderPo po) throws Exception;
	
	public boolean createInfoPayOrder(InfoPayOrderPo po) throws Exception;
	
//	public List<InfoPayOrderPo> queryInfoPayOrderByRelaOrderNo(InfoPayOrderPo po) throws Exception;

	/*
	 * 根据销售订单号查支付订单表??  rela_order_no关联订单号
	 */
	public InfoPayOrderPo queryInfoPayOrderByRelaOrderNo(InfoPayOrderPo po) throws Exception;
	
	/**
	 * 根据支付订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoPayOrderByPayOrderNo(InfoPayOrderPo po);
	
	/**
	 * 根据支付类型查询支付订单表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoPayOrderPo> queryInfoPayOrderByPayType(InfoPayOrderPo po) throws Exception;
}
