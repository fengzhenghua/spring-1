package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uochis.po.SalesOrderListHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.SalesOrderListHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("SalesOrderListHisServ")
public class SalesOrderListHisServImpl extends BaseServImpl<SalesOrderListHisPo> implements SalesOrderListHisServ{
	Logger logger = Logger.getLogger(SalesOrderListHisServImpl.class);

	@Override
	public int querySalesOrderListHisCount(SaleOrderInPo po)
			throws Exception {
		Condition con = Condition. build("querySalesOrderListHisCount" ).filter( po.convertThis2Map());
		List<SalesOrderListHisPo> list=query(SalesOrderListHisPo.class,con );
		return list.size();
	}
	
	
	@Override
	public List<SalesOrderListHisPo> querySalesOrderListHis(SaleOrderInPo po,int pageNo,int pageSize)throws Exception{
		Condition con = Condition. build("querySalesOrderListHis" ).filter( po.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<SalesOrderListHisPo> list = S.get(SalesOrderListHisPo. class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
	}
	
	

}
