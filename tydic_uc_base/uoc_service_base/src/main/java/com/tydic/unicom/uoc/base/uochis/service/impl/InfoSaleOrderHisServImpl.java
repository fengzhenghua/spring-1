package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderHisServ;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoSaleOrderHisServ")
public class InfoSaleOrderHisServImpl extends BaseServImpl<InfoSaleOrderHisPo> implements InfoSaleOrderHisServ{
	
	@Override
	public boolean createInfoSaleOrderHisPo(InfoSaleOrderHisPo po)throws Exception{
		if(po==null){
			return false;
		}		
		create(InfoSaleOrderHisPo.class,po);
		return true;
	}

	@Override
	public InfoSaleOrderHisPo queryInfoSaleOrderHisBySaleOrderNo(
			InfoSaleOrderHisPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoSaleOrderHisBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderHisPo> list = query(InfoSaleOrderHisPo.class,con); 
		return (list.isEmpty()||list==null)?null:list.get(0);
	}
	
	
	@Override
	public List<InfoSaleOrderHisPo> queryInfoSaleOrderPoHisList(SaleOrderInPo po,int pageNo,int pageSize)throws Exception{
		Condition condition = Condition.build("queryInfoSaleOrderPoHisList").filter(po.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<InfoSaleOrderHisPo> list = S.get(InfoSaleOrderHisPo. class).page(condition ,number ,pageSize );
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
		
		
	}
	
	@Override
	public int queryInfoSaleOrderPoHisListCount(SaleOrderInPo po)throws Exception{
		
		Condition condition = Condition.build("queryInfoSaleOrderHis").filter(po.convertThis2Map());
		List<InfoSaleOrderHisPo> list = query(InfoSaleOrderHisPo.class, condition);
		return list.size();
		
	}
	
	@Override
	public InfoSaleOrderHisPo queryInfoSaleOrderHis(SaleOrderInPo po)throws Exception{
		Condition condition = Condition.build("queryInfoSaleOrderHis").filter(po.convertThis2Map());
		List<InfoSaleOrderHisPo> list = query(InfoSaleOrderHisPo.class, condition);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}
	

}
