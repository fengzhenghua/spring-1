package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderAttrHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderAttrHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderAttrHisServ")
public class InfoServiceOrderAttrHisServImpl  extends BaseServImpl<InfoServiceOrderAttrHisPo> implements InfoServiceOrderAttrHisServ{
	@Override
	public boolean createInfoSaleOrderAttrHisPo(InfoServiceOrderAttrHisPo po)throws Exception{
		
		if(po==null){
			return false;
		}		
		create(InfoServiceOrderAttrHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoServiceOrderAttrHisPo> queryInfoServiceOrderAttrHisByOrderNo(
			InfoServiceOrderAttrHisPo po) throws Exception {
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
		Condition con = Condition. build("queryInfoServiceOrderAttrHisByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderAttrHisPo> list = query(InfoServiceOrderAttrHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
}
