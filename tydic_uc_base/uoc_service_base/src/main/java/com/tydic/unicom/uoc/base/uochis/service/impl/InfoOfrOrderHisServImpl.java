package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOfrOrderHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoOfrOrderHisServ")
public class InfoOfrOrderHisServImpl extends BaseServImpl<InfoOfrOrderHisPo> implements InfoOfrOrderHisServ{

	@Override
	public boolean createInfoOfrOrderHisPo(InfoOfrOrderHisPo po)throws Exception{
		if(po==null){
			return false;
		}		
		create(InfoOfrOrderHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoOfrOrderHisPo> queryInfoOfrOrderHisByOrderNo(
			InfoOfrOrderHisPo po) throws Exception {
		String order_no="";
		if(po.getSale_order_no()!=null){
			order_no=po.getSale_order_no();
		}else if(po.getOfr_order_no()!=null){
			order_no=po.getOfr_order_no();
		}
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoOfrOrderHisByOrderNo").filter(po.convertThis2Map());
		List<InfoOfrOrderHisPo> list = query(InfoOfrOrderHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
