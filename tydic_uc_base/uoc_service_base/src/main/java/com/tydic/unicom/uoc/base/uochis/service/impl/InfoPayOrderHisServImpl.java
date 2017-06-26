package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoPayOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoPayOrderHisServ;
import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoPayOrderHisServ")
public class InfoPayOrderHisServImpl extends BaseServImpl<InfoPayOrderHisPo> implements InfoPayOrderHisServ{
	
	@Override
	public boolean createInfoPayOrderHisPo(InfoPayOrderHisPo po)throws Exception{
		
		if(po==null){
			return false;
		}		
		create(InfoPayOrderHisPo.class,po);
		return true;
		
		
	}

	@Override
	public List<InfoPayOrderHisPo> queryInfoPayOrderHisByOrderNo(
			InfoPayOrderHisPo po) throws Exception {
		String order_no="";
		if(po.getPay_order_no()!=null){
			order_no=po.getPay_order_no();
		}else if(po.getRela_order_no()!=null){
			order_no=po.getRela_order_no();
		}
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoPayOrderHisByOrderNo").filter(po.convertThis2Map());
		List<InfoPayOrderHisPo> list = query(InfoPayOrderHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	
	@Override
	public List<InfoPayOrderHisPo> queryInfoPayOrderHisByPayType(InfoPayOrderHisPo po) throws Exception{
		Condition condition = Condition.build("queryInfoPayOrderHisByPayType").filter(po.convertThis2Map());
		List<InfoPayOrderHisPo> list = query(InfoPayOrderHisPo.class, condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
		
	}
	

}
