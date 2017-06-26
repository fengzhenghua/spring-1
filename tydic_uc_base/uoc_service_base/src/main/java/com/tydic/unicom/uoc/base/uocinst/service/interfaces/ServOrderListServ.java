package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uocinst.po.ServOrderListPo;

public interface ServOrderListServ {


	public List<ServOrderListPo> queryServOrderList(SaleOrderInPo po,int pageNo,int pageSize)throws Exception;


	public int queryServOrderListCount(SaleOrderInPo po)throws Exception;

	public List<ServOrderListPo> queryServOrderListBySaleTacheCode(SaleOrderInPo po,int pageNo,int pageSize)throws Exception;


	public int queryServOrderListCountBySaleTacheCode(SaleOrderInPo po)throws Exception;

}
