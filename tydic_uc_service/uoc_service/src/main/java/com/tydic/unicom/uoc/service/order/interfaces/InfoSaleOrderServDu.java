package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;

public interface InfoSaleOrderServDu {

	public InfoSaleOrderVo getInfoSaleOrderPoBySaleOrderNo(InfoSaleOrderVo vo)throws Exception;

	public InfoSaleOrderVo queryInfoSaleOrder(InfoSaleOrderVo vo) throws Exception;

	public boolean createInfoSaleOrder(InfoSaleOrderVo vo)throws Exception;

	public boolean updateInfoSaleOrder(InfoSaleOrderVo vo)throws Exception;

	public List<InfoSaleOrderVo> queryInfoSaleOrderByOrderState(InfoSaleOrderVo vo) throws Exception;

}
