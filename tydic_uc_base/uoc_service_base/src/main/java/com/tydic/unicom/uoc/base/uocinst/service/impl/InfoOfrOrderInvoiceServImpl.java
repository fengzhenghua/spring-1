package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderInvoicePo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderInvoiceServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoOfrOrderInvoiceServ")
public class InfoOfrOrderInvoiceServImpl extends BaseServImpl<InfoOfrOrderInvoicePo> implements InfoOfrOrderInvoiceServ{

	@Override
	public List<InfoOfrOrderInvoicePo> queryInfoOfrOrderInvoiceByOrderNo(InfoOfrOrderInvoicePo po) throws Exception{
		String order_no="";
		if(po.getSale_order_no()!=null){
			order_no=po.getSale_order_no();
		}else if(po.getOfr_order_no()!=null){
			order_no=po.getOfr_order_no();
		}
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition. build("queryInfoOfrOrderInvoiceByOrderNo").filter(po.convertThis2Map());
		List<InfoOfrOrderInvoicePo> list = query(InfoOfrOrderInvoicePo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public boolean  deleteInfoOfrOrderInvoiceBySaleOrderNo(InfoOfrOrderInvoicePo po) throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoOfrOrderInvoiceBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoOfrOrderInvoicePo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}

}
