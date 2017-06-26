package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderM165HisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderM165HisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderM165HisServ")
public class InfoServiceOrderM165HisServImpl extends BaseServImpl<InfoServiceOrderM165HisPo> implements InfoServiceOrderM165HisServ{

	@Override
	public boolean createInfoServiceOrderM165HisPo(InfoServiceOrderM165HisPo po)throws Exception{

		if(po==null){
			return false;
		}		
		create(InfoServiceOrderM165HisPo.class,po);
		return true;

	}

	@Override
	public List<InfoServiceOrderM165HisPo> queryInfoServiceOrderM165HisByOrderNo(
			InfoServiceOrderM165HisPo po) throws Exception {
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
		Condition con = Condition. build("queryInfoServiceOrderM165HisByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderM165HisPo> list = query(InfoServiceOrderM165HisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
