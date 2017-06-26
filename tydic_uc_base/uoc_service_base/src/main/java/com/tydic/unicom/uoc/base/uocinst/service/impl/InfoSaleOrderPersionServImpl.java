package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderPersionServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoSaleOrderPersionServ")
public class InfoSaleOrderPersionServImpl extends BaseServImpl<InfoSaleOrderPersionPo> implements InfoSaleOrderPersionServ {

	@Override
	public boolean deleteInfoSaleOrderPersionBySaleOrderNo(
			InfoSaleOrderPersionPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoSaleOrderPersionPo.class,po);
		return true;
	}
	
	@Override
	public InfoSaleOrderPersionPo getInfoSaleOrderPersionBySaleOrderNo(InfoSaleOrderPersionPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		return get(InfoSaleOrderPersionPo.class,po);
	}
	
}
