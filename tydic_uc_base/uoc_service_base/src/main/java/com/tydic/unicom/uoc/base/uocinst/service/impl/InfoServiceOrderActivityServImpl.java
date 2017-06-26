package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderActivityPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderActivityServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoServiceOrderActivityServ")
public class InfoServiceOrderActivityServImpl extends BaseServImpl<InfoServiceOrderActivityPo> implements InfoServiceOrderActivityServ{

//	@Override
//	public List<InfoServiceOrderActivityPo> queryInfoServiceOrderActivityByOfrOrderNo(
//			InfoServiceOrderActivityPo po) throws Exception {
//		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
//		po.setPart_month(strMap.get("part_month"));
//		po.setArea_code(strMap.get("area_code"));
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put( "ofr_order_no", po .getOfr_order_no());
//		map.put( "part_month", po .getPart_month());
//		map.put( "area_code", po .getArea_code());
//		Condition con = Condition. build("queryInfoServiceOrderActivityByOfrOrderNo").filter( map);
//		List<InfoServiceOrderActivityPo> list = query(InfoServiceOrderActivityPo.class ,con );
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
	public List<InfoServiceOrderActivityPo> queryInfoServiceOrderActivityByOrderNo(InfoServiceOrderActivityPo po)throws Exception{
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
		Condition con = Condition. build("queryInfoServiceOrderActivityByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderActivityPo> list = query(InfoServiceOrderActivityPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public boolean deleteInfoServiceOrderActivityBySaleOrderNo(InfoServiceOrderActivityPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoServiceOrderActivityBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoServiceOrderActivityPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}

	@Override
	public List<InfoServiceOrderActivityPo> queryInfoServiceOrderActivityByServOrderNo(
			InfoServiceOrderActivityPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getServ_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		/*Map<String,Object> map = new HashMap<String,Object>();		
		map.put( "part_month", po .getPart_month());
		map.put( "area_code", po .getArea_code());*/
		Condition con = Condition. build("queryInfoServiceOrderActivityByServOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderActivityPo> list = query(InfoServiceOrderActivityPo.class ,con );

		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
