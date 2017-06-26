package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;


public interface InfoDeliverOrderServDu {

	public InfoDeliverOrderVo getInfoDeliverOrderByPayDeliverOrderNo(InfoDeliverOrderVo vo) throws Exception;
	
	public boolean updateInfoDeliverOrder(InfoDeliverOrderVo vo) throws Exception; 
	
	public List<InfoDeliverOrderVo> queryInfoDeliverOrderBySaleOrderNo(InfoDeliverOrderVo vo) throws Exception;
	
	public boolean create(InfoDeliverOrderVo infoDeliverOrderVo) throws Exception;
}
