package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoPayOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderDistributionHisVo;

public interface InfoSaleOrderDistributionHisServDu {
	
	public InfoSaleOrderDistributionHisVo getInfoSaleOrderDistributionHisBySaleOrderNo(InfoSaleOrderDistributionHisVo vo)throws Exception;
	
	public List<InfoSaleOrderDistributionHisVo> queryInfoSaleOrderDistributionHisByPayOrderNo(InfoPayOrderHisVo vo)throws Exception;

}
