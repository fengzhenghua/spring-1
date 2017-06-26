package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoDeliverOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoDeliverOrderHisServ;
import com.tydic.unicom.uoc.base.uocinst.po.InfoDeliverOrderPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoDeliverOrderHisServ")
public class InfoDeliverOrderHisServImpl extends BaseServImpl<InfoDeliverOrderHisPo> implements InfoDeliverOrderHisServ{
	
	@Override
	public boolean createInfoDeliverOrderHisPo(InfoDeliverOrderHisPo po)throws Exception{
		if(po==null){
			return false;
		}		
		create(InfoDeliverOrderHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoDeliverOrderHisPo> queryInfoDeliverOrderHisByOrderNo(
			InfoDeliverOrderHisPo po) throws Exception {
		String order_no="";
		if(po.getSale_order_no()!=null){
			order_no=po.getSale_order_no();
		}else if(po.getDeliver_order_no()!=null){
			order_no = po.getDeliver_order_no();
		}
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoDeliverOrderHisByOrderNo").filter(po.convertThis2Map());
		List<InfoDeliverOrderHisPo> list = query(InfoDeliverOrderHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

	@Override
	public List<InfoDeliverOrderHisPo> queryInfoDeliverOrderHisAll(
			InfoDeliverOrderHisPo po) throws Exception {
		Condition con = Condition.build("queryInfoDeliverOrderHisAll").filter(po.convertThis2Map());
		List<InfoDeliverOrderHisPo> list = query(InfoDeliverOrderHisPo.class,con);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
