package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uochis.po.ServOrderListHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ServOrderListHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;


@Service("ServOrderListHisServ")
public class ServOrderListHisServImpl extends BaseServImpl<ServOrderListHisPo> implements ServOrderListHisServ{
	
	@Override
	public List<ServOrderListHisPo> queryServOrderHisList(SaleOrderInPo po,int pageNo,int pageSize)throws Exception{
		Condition con = Condition. build("queryServOrderHisList" ).filter( po.convertThis2Map());
		List<ServOrderListHisPo> list = S.get(ServOrderListHisPo. class).page(con ,pageNo ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
		
	}
	
	@Override
	public int queryServOrderHisListCount(SaleOrderInPo po)throws Exception{
		Condition con = Condition. build("queryServOrderHisListCount" ).filter( po.convertThis2Map());
		List<ServOrderListHisPo> list=query(ServOrderListHisPo.class,con );
		return list.size();
	}
	
	@Override
	public List<ServOrderListHisPo> queryServOrderHisListBySaleTacheCode(SaleOrderInPo po,int pageNo,int pageSize)throws Exception{
		Condition con = Condition. build("queryServOrderHisListBySaleTacheCode" ).filter( po.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<ServOrderListHisPo> list = S.get(ServOrderListHisPo. class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
		
	}
	
	@Override
	public int queryServOrderHisListCountBySaleTacheCode(SaleOrderInPo po)throws Exception{
		Condition con = Condition. build("queryServOrderHisListCountBySaleTacheCode" ).filter( po.convertThis2Map());
		List<ServOrderListHisPo> list=query(ServOrderListHisPo.class,con );
		return list.size();
	}

}
