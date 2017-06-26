package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderM165Po;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderM165Serv;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderM165Serv")
public class InfoServiceOrderM165ServImpl extends BaseServImpl<InfoServiceOrderM165Po> implements InfoServiceOrderM165Serv{
	
	@Override
	public List<InfoServiceOrderM165Po> queryInfoServiceOrderM165ByOrderNo(InfoServiceOrderM165Po po)throws Exception{
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
		Condition con = Condition. build("queryInfoServiceOrderM165ByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderM165Po> list = query(InfoServiceOrderM165Po.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public boolean deleteInfoServiceOrderM165BySaleOrderNo(InfoServiceOrderM165Po po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoServiceOrderM165BySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoServiceOrderM165Po.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}

//	@Override
//	public List<InfoServiceOrderM165Po> queryInfoServiceOrderM165ByOfrOrderNo(
//			InfoServiceOrderM165Po po) throws Exception {
//		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOfr_order_no());
//		po.setPart_month(strMap.get("part_month"));
//		po.setArea_code(strMap.get("area_code"));
//		Condition con = Condition. build("queryInfoServiceOrderM165ByOfrOrderNo").filter(po.convertThis2Map());
//		List<InfoServiceOrderM165Po> list = query(InfoServiceOrderM165Po.class,con);      
//		if(list !=null&&list.size()>0){
//			return list ;
//		}
//		else{
//			return null ;
//		}
//	}

}
