package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderDeveloperPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderDeveloperServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderDeveloperServ")
public class InfoServiceOrderDeveloperServImpl extends BaseServImpl<InfoServiceOrderDeveloperPo> implements InfoServiceOrderDeveloperServ{
	
	@Override
	public List<InfoServiceOrderDeveloperPo> queryInfoServiceOrderDeveloperByOrderNo(InfoServiceOrderDeveloperPo po)throws Exception{
		String order_no="";
		if(po.getSale_order_no()!=null){
			order_no=po.getSale_order_no();
		}else if(po.getServ_order_no()!=null){
			order_no=po.getServ_order_no();
		}else if(po.getOfr_order_no()!=null){		
			order_no=po.getOfr_order_no();
		}
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition. build("queryInfoServiceOrderDeveloperByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderDeveloperPo> list = query(InfoServiceOrderDeveloperPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public boolean deleteInfoServiceOrderDeveloperBySaleOrderNo(InfoServiceOrderDeveloperPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoServiceOrderDeveloperBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoServiceOrderDeveloperPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}

	@Override
	public List<InfoServiceOrderDeveloperPo> queryInfoServiceOrderDeveloperByServOrderNo(
			InfoServiceOrderDeveloperPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getServ_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition. build("queryInfoServiceOrderDeveloperByServOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderDeveloperPo> list = query(InfoServiceOrderDeveloperPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
