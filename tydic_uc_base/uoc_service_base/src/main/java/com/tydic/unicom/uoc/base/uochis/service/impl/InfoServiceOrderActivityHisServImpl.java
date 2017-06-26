package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderActivityHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderActivityHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;


@Service("InfoServiceOrderActivityHisServ")
public class InfoServiceOrderActivityHisServImpl extends BaseServImpl<InfoServiceOrderActivityHisPo> implements InfoServiceOrderActivityHisServ{

	@Override
	public boolean createInfoServiceOrderActivityHisPo(InfoServiceOrderActivityHisPo po)throws Exception{

		if(po==null){
			return false;
		}		
		create(InfoServiceOrderActivityHisPo.class,po);
		return true;

	}

	@Override
	public List<InfoServiceOrderActivityHisPo> queryInfoServiceOrderActivityHisByOrderNo(
			InfoServiceOrderActivityHisPo po) throws Exception {
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
		Condition con = Condition. build("queryInfoServiceOrderActivityHisByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderActivityHisPo> list = query(InfoServiceOrderActivityHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
