package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.GetServOrderNoPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderSimCardPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderSimCardServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoServiceOrderSimCardServ")
public class InfoServiceOrderSimCardServImpl extends BaseServImpl<InfoServiceOrderSimCardPo> implements InfoServiceOrderSimCardServ{

//	@Override
//	public List<InfoServiceOrderSimCardPo> queryInfoServiceOrderSimCardByOfrOrderNo(
//			InfoServiceOrderSimCardPo po) throws Exception {
//		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOfr_order_no());
//		po.setPart_month(strMap.get("part_month"));
//		po.setArea_code(strMap.get("area_code"));
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("ofr_order_no", po.getOfr_order_no());
//	    map.put( "part_month", po .getPart_month());
//		map.put( "area_code", po .getArea_code());
//		Condition con = Condition.build("queryInfoServiceOrderSimCardByOfrOrderNo").filter(map);
//		List<InfoServiceOrderSimCardPo> listPo = query(InfoServiceOrderSimCardPo.class,con);
//		if(listPo.size() == 0){
//			return null;
//		}else{
//			return listPo;
//		}		
//	}
	
	@Override
	public List<InfoServiceOrderSimCardPo> queryInfoServiceOrderSimCardByOrderNo(InfoServiceOrderSimCardPo po)throws Exception{
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
		Condition con = Condition. build("queryInfoServiceOrderSimCardByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderSimCardPo> list = query(InfoServiceOrderSimCardPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public boolean  deleteInfoServiceOrderSimCardBySaleOrderNo(InfoServiceOrderSimCardPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoServiceOrderSimCardBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoServiceOrderSimCardPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}

	@Override
	public List<InfoServiceOrderSimCardPo> queryInfoServiceOrderSimCardAllSimId(GetServOrderNoPo po) throws Exception{
		Condition con = Condition. build("queryInfoServiceOrderSimCardAllSimId").filter(po.convertThis2Map());
		List<InfoServiceOrderSimCardPo> list = query(InfoServiceOrderSimCardPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
