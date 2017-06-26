package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderPo;

public interface InfoOfrOrderServ {
	/*
	 * 根据OfrOrderNo查询商品订单表
	 */
	public InfoOfrOrderPo getInfoOfrOrderByOfrOrderNo(InfoOfrOrderPo po) throws Exception;
	/*
	 * 创建商品订单
	 */
	public boolean createInfoOfrOrder(InfoOfrOrderPo po) throws Exception;
	/*
	 * 修改商品订单
	 */
	
	public boolean updateInfoOfrOrder(InfoOfrOrderPo po) throws Exception;
	
	/*
	 * 通过销售订单查询订单下的所以商品订单（多条）
	 */
	public List<InfoOfrOrderPo> queryInfoOfrOrderBySaleOrderNo(InfoOfrOrderPo po) throws Exception;
	
	/**
	 * 通过销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoOfrOrderBySaleOrderNo(InfoOfrOrderPo po) throws Exception;
	
}
