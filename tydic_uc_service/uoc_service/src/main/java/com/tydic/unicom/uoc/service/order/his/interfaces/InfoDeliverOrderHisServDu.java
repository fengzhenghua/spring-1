package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoDeliverOrderHisVo;

public interface InfoDeliverOrderHisServDu {

		
	public List<InfoDeliverOrderHisVo> queryInfoDeliverOrderHisBySaleOrderNo(InfoDeliverOrderHisVo vo) throws Exception;
}
