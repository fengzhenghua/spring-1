package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uocinst.po.SalesOrderListPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.SalesOrderListServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;


@Service("SalesOrderListServ")
public class SalesOrderListServImpl extends BaseServImpl<SalesOrderListPo> implements SalesOrderListServ{
	Logger logger = Logger.getLogger(SalesOrderListServImpl.class);
	
		

	@Override
	public int querySalesOrderListCount(SaleOrderInPo po) throws Exception {
		// TODO Auto-generated method stub
		Condition con = Condition. build("querySalesOrderListCount" ).filter( po.convertThis2Map());
		List<SalesOrderListPo> list=query(SalesOrderListPo.class,con );
		return list.size();
	}
	
	@Override
	public List<SalesOrderListPo> querySalesOrderList(SaleOrderInPo po,int pageNo,int pageSize)throws Exception{
		Condition con = Condition. build("querySalesOrderList" ).filter( po.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<SalesOrderListPo> list = S.get(SalesOrderListPo. class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
	}

}
