package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import com.tydic.uda.core.Condition;

import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderFixHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderFixHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderFixHisServ")
public class InfoServiceOrderFixHisServImpl extends BaseServImpl<InfoServiceOrderFixHisPo> implements InfoServiceOrderFixHisServ{
	
	@Override
	public boolean createInfoServiceOrderFixHisPo(InfoServiceOrderFixHisPo po)throws Exception{
		
		if(po==null){
			return false;
		}		
		create(InfoServiceOrderFixHisPo.class,po);
		return true;
	}

	@Override
	public  List<InfoServiceOrderFixHisPo> queryInfoServiceOrderFixHisByOrderNo(
			InfoServiceOrderFixHisPo po) throws Exception {
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
		Condition con = Condition. build("queryInfoServiceOrderFixHisByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderFixHisPo> list = query(InfoServiceOrderFixHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
