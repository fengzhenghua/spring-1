package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderFeeHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOfrOrderFeeHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoOfrOrderFeeHisServ")
public class InfoOfrOrderFeeHisServImpl extends BaseServImpl<InfoOfrOrderFeeHisPo> implements InfoOfrOrderFeeHisServ{
	@Override
	public boolean createInfoOfrOrderFeeHisPo(InfoOfrOrderFeeHisPo po)throws Exception{

		if(po==null){
			return false;
		}		
		create(InfoOfrOrderFeeHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoOfrOrderFeeHisPo> queryInfoOfrOrderFeeHisByOrderNo(
			InfoOfrOrderFeeHisPo po) throws Exception {
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
		Condition con = Condition. build("queryInfoOfrOrderFeeHisByOrderNo").filter(po.convertThis2Map());
		List<InfoOfrOrderFeeHisPo> list = query(InfoOfrOrderFeeHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
