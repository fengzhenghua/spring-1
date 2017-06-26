package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderProdMapPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderProdMapServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoServiceOrderProdMapServ")
public class InfoServiceOrderProdMapServImpl extends BaseServImpl<InfoServiceOrderProdMapPo> implements InfoServiceOrderProdMapServ{

//	@Override
//	public List<InfoServiceOrderProdMapPo> queryInfoServiceOrderProdMapByOfrOrderNo(
//			InfoServiceOrderProdMapPo po) throws Exception {
//		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOfr_order_no());
//		po.setPart_month(strMap.get("part_month"));
//		po.setArea_code(strMap.get("area_code"));
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put( "ofr_order_no", po .getOfr_order_no());
//		map.put( "part_month", po .getPart_month());
//		map.put( "area_code", po .getArea_code());
//		Condition con = Condition. build("queryInfoServiceOrderProdMapByOfrOrderNo").filter( map);
//		List<InfoServiceOrderProdMapPo> list = query(InfoServiceOrderProdMapPo.class ,con );
//
//		if(list !=null&&list.size()>0){
//			return list ;
//		}
//		else{
//			return null ;
//		}
//
//	}
	
	@Override
	public List<InfoServiceOrderProdMapPo> queryInfoServiceOrderProdMapByOrderNo(InfoServiceOrderProdMapPo po)throws Exception{
		String order_no="";
		if(po.getSale_order_no()!=null){
			order_no=po.getSale_order_no();
		}else if(po.getServ_order_no()!=null){
			order_no=po.getServ_order_no();
		}
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition. build("queryInfoServiceOrderProdMapByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderProdMapPo> list = query(InfoServiceOrderProdMapPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public boolean deleteInfoServiceOrderProdMapBySaleOrderNo(InfoServiceOrderProdMapPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoServiceOrderProdMapBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoServiceOrderProdMapPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}
	
	@Override
	public List<InfoServiceOrderProdMapPo> queryInfoServiceOrderProdMapByProdCode(InfoServiceOrderProdMapPo po)throws Exception{
		Condition con = Condition. build("queryInfoServiceOrderProdMapByProdCode").filter(po.convertThis2Map());
		List<InfoServiceOrderProdMapPo> list = query(InfoServiceOrderProdMapPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
