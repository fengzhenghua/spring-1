package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderProdMapHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderProdMapHisServ;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderProdMapPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderProdMapHisServ")
public class InfoServiceOrderProdMapHisServImpl  extends BaseServImpl<InfoServiceOrderProdMapHisPo> implements InfoServiceOrderProdMapHisServ{

	@Override
	public boolean createInfoServiceOrderProdMapHisPo(InfoServiceOrderProdMapHisPo po)throws Exception{
		if(po==null){
			return false;
		}		
		create(InfoServiceOrderProdMapHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoServiceOrderProdMapHisPo> queryInfoServiceOrderProdMapHisByOrderNo(
			InfoServiceOrderProdMapHisPo po) throws Exception {
		String order_no="";
		if(po.getSale_order_no()!=null){
			order_no=po.getSale_order_no();
		}else if(po.getOfr_order_no()!=null){
			order_no=po.getOfr_order_no();
		}else if(po.getServ_order_no()!=null){
			order_no=po.getServ_order_no();
		}
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoServiceOrderProdMapHisByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderProdMapHisPo> list = query(InfoServiceOrderProdMapHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public List<InfoServiceOrderProdMapHisPo> queryInfoServiceOrderProdMapHisByProdCode(InfoServiceOrderProdMapHisPo po)throws Exception{
		Condition con = Condition. build("queryInfoServiceOrderProdMapHisByProdCode").filter(po.convertThis2Map());
		List<InfoServiceOrderProdMapHisPo> list = query(InfoServiceOrderProdMapHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
		
	}
}
