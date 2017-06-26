package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uocinst.po.ServOrderListPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ServOrderListServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ServOrderListServ")
public class ServOrderListServImpl extends BaseServImpl<ServOrderListPo> implements ServOrderListServ{
	
	@Override
	public List<ServOrderListPo> queryServOrderList(SaleOrderInPo po,int pageNo,int pageSize)throws Exception{
		Condition con = Condition. build("queryServOrderList" ).filter( po.convertThis2Map());
		List<ServOrderListPo> list = S.get(ServOrderListPo. class).page(con ,pageNo ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
		
	}
	
	@Override
	public int queryServOrderListCount(SaleOrderInPo po)throws Exception{
		Condition con = Condition. build("queryServOrderListCount" ).filter( po.convertThis2Map());
		List<ServOrderListPo> list=query(ServOrderListPo.class,con );
		return list.size();
	}
	
	@Override
	public List<ServOrderListPo> queryServOrderListBySaleTacheCode(SaleOrderInPo po,int pageNo,int pageSize)throws Exception{
		Condition con = Condition. build("queryServOrderListBySaleTacheCode" ).filter( po.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<ServOrderListPo> list = S.get(ServOrderListPo. class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
		
	}
	
	@Override
	public int queryServOrderListCountBySaleTacheCode(SaleOrderInPo po)throws Exception{
		Condition con = Condition. build("queryServOrderListCountBySaleTacheCode" ).filter( po.convertThis2Map());
		List<ServOrderListPo> list=query(ServOrderListPo.class,con );
		return list.size();
	}
	

}
