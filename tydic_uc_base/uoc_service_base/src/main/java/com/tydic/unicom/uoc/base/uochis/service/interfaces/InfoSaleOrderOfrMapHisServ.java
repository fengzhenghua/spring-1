package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderOfrMapHisPo;

public interface InfoSaleOrderOfrMapHisServ {
	
	public boolean createInfoSaleOrderOfrMapHisPo(InfoSaleOrderOfrMapHisPo po)throws Exception;

	public List<InfoSaleOrderOfrMapHisPo> queryInfoSaleOrderOfrMapHisBySaleOrderNo(InfoSaleOrderOfrMapHisPo po)throws Exception;
	
	public List<InfoSaleOrderOfrMapHisPo> queryInfoSaleOrderOfrMapHisByAccNbr(InfoSaleOrderOfrMapHisPo po)throws Exception;
}
