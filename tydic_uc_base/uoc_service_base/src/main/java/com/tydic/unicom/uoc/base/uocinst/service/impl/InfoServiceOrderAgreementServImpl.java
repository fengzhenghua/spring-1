package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderAgreementPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderAgreementServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderAgreementServ")
public class InfoServiceOrderAgreementServImpl extends BaseServImpl<InfoServiceOrderAgreementPo> implements InfoServiceOrderAgreementServ{
	
	@Override
	public List<InfoServiceOrderAgreementPo> queryInfoServiceOrderAgreementByOrderNo(InfoServiceOrderAgreementPo po) throws Exception{
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
		Condition con = Condition. build("queryInfoServiceOrderAgreementByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderAgreementPo> list = query(InfoServiceOrderAgreementPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public boolean  deleteInfoServiceOrderAgreementBySaleOrderNo(InfoServiceOrderAgreementPo po) throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoServiceOrderAgreementBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoServiceOrderAgreementPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}

//	@Override
//	public List<InfoServiceOrderAgreementPo> queryInfoServiceOrderAgreementByOfrOrderNo(
//			InfoServiceOrderAgreementPo po) throws Exception {
//		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
//		po.setPart_month(strMap.get("part_month"));
//		po.setArea_code(strMap.get("area_code"));
//		Condition con = Condition. build("queryInfoServiceOrderAgreementByOfrOrderNo").filter(po.convertThis2Map());
//		List<InfoServiceOrderAgreementPo> list = query(InfoServiceOrderAgreementPo.class,con);      
//		if(list !=null&&list.size()>0){
//			return list ;
//		}
//		else{
//			return null ;
//		}
//	}

	@Override
	public InfoServiceOrderAgreementPo queryInfoServiceOrderAgreementByServOrderNo(
			InfoServiceOrderAgreementPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getServ_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition. build("queryInfoServiceOrderAgreementByServOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderAgreementPo> list = query(InfoServiceOrderAgreementPo.class,con);    
		return (list.isEmpty() || list==null)?null:list.get(0);
	}


}
