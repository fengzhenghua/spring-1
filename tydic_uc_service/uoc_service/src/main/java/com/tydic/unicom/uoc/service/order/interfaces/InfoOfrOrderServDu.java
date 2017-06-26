package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderVo;

public interface InfoOfrOrderServDu {
	/*
	 * 创建商品订单
	 */
	public boolean createInfoOfrOrder(InfoOfrOrderVo vo) throws Exception;
	/*
	 * 根据OfrOrderNo查询商品订单表
	 */
	public InfoOfrOrderVo getInfoOfrOrderByOfrOrderNo(InfoOfrOrderVo vo) throws Exception;
	/*
	 * 修改商品订单
	 */
	public boolean updateInfoOfrOrder(InfoOfrOrderVo vo) throws Exception;
	/*
	 * 通过销售订单查询订单下的所以商品订单（多条）
	 */
	public List<InfoOfrOrderVo> queryInfoOfrOrderBySaleOrderNo(InfoOfrOrderVo vo) throws Exception;
	
}
