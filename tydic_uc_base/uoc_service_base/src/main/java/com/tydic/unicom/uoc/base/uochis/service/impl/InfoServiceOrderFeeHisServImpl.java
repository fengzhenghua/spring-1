package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderFeeHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderFeeHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoServiceOrderFeeHisServ")
public class InfoServiceOrderFeeHisServImpl extends BaseServImpl<InfoServiceOrderFeeHisPo> implements InfoServiceOrderFeeHisServ{
	@Override
	public boolean createInfoServiceOrderFeeHisPo(InfoServiceOrderFeeHisPo po)throws Exception{
		if(po==null){
			return false;
		}		
		create(InfoServiceOrderFeeHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoServiceOrderFeeHisPo> queryInfoServiceOrderFeeHisByOrderNo(
			InfoServiceOrderFeeHisPo po) throws Exception {
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
		Condition con = Condition. build("queryInfoServiceOrderFeeHisByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderFeeHisPo> list = query(InfoServiceOrderFeeHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
