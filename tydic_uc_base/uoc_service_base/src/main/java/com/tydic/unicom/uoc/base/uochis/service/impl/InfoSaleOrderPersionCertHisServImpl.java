package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderPersionCertHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderPersionCertHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoSaleOrderPersionCertHisServ")
public class InfoSaleOrderPersionCertHisServImpl  extends BaseServImpl<InfoSaleOrderPersionCertHisPo> implements
		InfoSaleOrderPersionCertHisServ {

	@Override
	public boolean createInfoSaleOrderPersionCertHis(
			InfoSaleOrderPersionCertHisPo po) throws Exception {
		if(po==null){
			return false;
		}
		create(InfoSaleOrderPersionCertHisPo.class,po);
		return true;
	}

	@Override
	public InfoSaleOrderPersionCertHisPo getInfoSaleOrderPersionCertHis(
			InfoSaleOrderPersionCertHisPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		return get(InfoSaleOrderPersionCertHisPo.class,po);
	}

}
