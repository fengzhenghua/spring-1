package com.tydic.unicom.uoc.base.uocinst.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderAttrPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderAttrServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoServiceOrderAttrServ")
public class InfoServiceOrderAttrServImpl extends BaseServImpl<InfoServiceOrderAttrPo> implements InfoServiceOrderAttrServ{

	
	@Override
	public boolean create(InfoServiceOrderAttrPo infoServiceOrderAttrPo) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean update(InfoServiceOrderAttrPo InfoServiceOrderAttrPo) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<InfoServiceOrderAttrPo> getInfoServiceOrderAttrPo(
			String serv_order_no) {
		Map<String,String> strMap = new HashMap<String,String>();
		try {
			 strMap =StrUtil.splitStringFromOrderNo(serv_order_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("serv_order_no", serv_order_no);
		map.put( "part_month", strMap.get("part_month"));
		map.put( "area_code", strMap.get("area_code"));
		Condition con = Condition.build("getInfoServiceOrderAttrPo").filter(map);
		List<InfoServiceOrderAttrPo> list = query(InfoServiceOrderAttrPo.class,con);
		if(list == null || list.size() <= 0){
			return null;
		}
	    return list;
	}
	
	@Override
    public Boolean addInfoServiceOrderAttr(InfoServiceOrderAttrPo infoServiceOrderAttrPo) {
	    if(null == infoServiceOrderAttrPo){
	    	return false;
	    }
	    create(InfoServiceOrderAttrPo.class,infoServiceOrderAttrPo);
		return true;
    }

//	@Override
//	public List<InfoServiceOrderAttrPo> queryInfoServiceOrderAttrByOfrOrderNo(
//			InfoServiceOrderAttrPo infoServiceOrderAttrPo) throws Exception {
//		Map<String,String> strMap = new HashMap<String,String>();		
//		try {
//			 strMap =StrUtil.splitStringFromOrderNo(infoServiceOrderAttrPo.getOfr_order_no());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Map<String,Object> map = new HashMap<String,Object>();
//        map.put( "ofr_order_no", infoServiceOrderAttrPo .getOfr_order_no());
//        map.put( "part_month", strMap.get("part_month"));
//		map.put( "area_code", strMap.get("area_code"));
//       Condition con = Condition. build("queryInfoServiceOrderAttrByOfrOrderNo").filter( map);
//       List<InfoServiceOrderAttrPo> list = query(InfoServiceOrderAttrPo.class ,con );
//
//       if(list !=null&&list.size()>0){
//    	   return list ;
//       }
//       else{
//    	   return null ;
//       }
//
//	}
	
	@Override
	public List<InfoServiceOrderAttrPo> queryInfoServiceOrderAttrByOrderNo(InfoServiceOrderAttrPo po) throws Exception{
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
		Condition con = Condition. build("queryInfoServiceOrderAttrByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderAttrPo> list = query(InfoServiceOrderAttrPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public boolean  deleteInfoServiceOrderAttrBySaleOrderNo(InfoServiceOrderAttrPo po) throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoServiceOrderAttrBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoServiceOrderAttrPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}

}
