package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uochis.po.ServOrderListHisPo;

public interface ServOrderListHisServ {

	public List<ServOrderListHisPo> queryServOrderHisList(SaleOrderInPo po,int pageNo,int pageSize)throws Exception;


	public int queryServOrderHisListCount(SaleOrderInPo po)throws Exception;
	
	public List<ServOrderListHisPo> queryServOrderHisListBySaleTacheCode(SaleOrderInPo po,int pageNo,int pageSize)throws Exception;


	public int queryServOrderHisListCountBySaleTacheCode(SaleOrderInPo po)throws Exception;

}
