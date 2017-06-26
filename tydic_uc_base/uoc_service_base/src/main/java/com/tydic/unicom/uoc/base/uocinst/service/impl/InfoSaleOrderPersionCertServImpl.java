package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionCertPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderPersionCertServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoSaleOrderPersionCertServ")
public class InfoSaleOrderPersionCertServImpl extends BaseServImpl<InfoSaleOrderPersionCertPo> implements
		InfoSaleOrderPersionCertServ {

	@Override
	public InfoSaleOrderPersionCertPo getInfoSaleOrderPersionCertBySaleOrderNo(
			InfoSaleOrderPersionCertPo po) throws Exception {
		
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));	
		return get(InfoSaleOrderPersionCertPo.class,po);
	}

	@Override
	public boolean deleteInfoSaleOrderPersionCertBySaleOrderNo(
			InfoSaleOrderPersionCertPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoSaleOrderPersionCertPo.class,po);
		return true;
	}

	@Override
	public boolean createInfoSaleOrderPersionCert(InfoSaleOrderPersionCertPo po)
			throws Exception {
		create(InfoSaleOrderPersionCertPo.class,po);
		return true;
	}

	@Override
	public boolean updateInfoSaleOrderPersionCert(InfoSaleOrderPersionCertPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		update(InfoSaleOrderPersionCertPo.class,po);
		return true;
	}

}
