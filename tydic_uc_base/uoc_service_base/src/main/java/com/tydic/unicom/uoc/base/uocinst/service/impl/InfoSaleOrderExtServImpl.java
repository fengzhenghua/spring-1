package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderExtPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderExtServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoSaleOrderExtServ")
public class InfoSaleOrderExtServImpl extends BaseServImpl<InfoSaleOrderExtPo> implements InfoSaleOrderExtServ {

	@Override
	public boolean deleteInfoSaleOrderExtBySaleOrderNo(InfoSaleOrderExtPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoSaleOrderExtPo.class,po);
		return true;
	}
	
	@Override
	public InfoSaleOrderExtPo getInfoSaleOrderExtBySaleOrderNo(InfoSaleOrderExtPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		return get(InfoSaleOrderExtPo.class,po);
		
	}

}
