package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderAgreementHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderAgreementHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderAgreementHisServ")
public class InfoServiceOrderAgreementHisServImpl extends BaseServImpl<InfoServiceOrderAgreementHisPo> implements InfoServiceOrderAgreementHisServ{
	
	@Override
	public boolean createInfoServiceOrderAgreementHisPo(InfoServiceOrderAgreementHisPo po)throws Exception{
		if(po==null){
			return false;
		}		
		create(InfoServiceOrderAgreementHisPo.class,po);
		return true;
		
	}

	@Override
	public List<InfoServiceOrderAgreementHisPo> queryInfoServiceOrderAgreementHisByOrderNo(
			InfoServiceOrderAgreementHisPo po) throws Exception {
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
		Condition con = Condition. build("queryInfoServiceOrderAgreementHisByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderAgreementHisPo> list = query(InfoServiceOrderAgreementHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
