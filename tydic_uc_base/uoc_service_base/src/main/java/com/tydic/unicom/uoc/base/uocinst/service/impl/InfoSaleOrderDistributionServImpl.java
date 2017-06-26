package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderDistributionPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderDistributionServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoSaleOrderDistributionServ")
public class InfoSaleOrderDistributionServImpl extends BaseServImpl<InfoSaleOrderDistributionPo> implements
		InfoSaleOrderDistributionServ {

	@Override
	public boolean deleteInfoSaleOrderDistributionBySaleOrderNo(
			InfoSaleOrderDistributionPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoSaleOrderDistributionPo.class,po);
		return true;
	}
	
	@Override
	public InfoSaleOrderDistributionPo getInfoSaleOrderDistributionBySaleOrderNo(InfoSaleOrderDistributionPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		return get(InfoSaleOrderDistributionPo.class,po);
	}

	@Override
	public List<InfoSaleOrderDistributionPo> queryInfoSaleOrderDistributionByPayOrderNo(
			InfoPayOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getRela_order_no());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("part_month", strMap.get("part_month"));
		map.put("area_code", strMap.get("area_code"));
		map.put("sale_order_no", po.getRela_order_no());
		map.put("rela_order_type", po.getRela_order_type());
		Condition con = Condition.build("queryInfoSaleOrderDistributionByPayOrderNo").filter(map);
		List<InfoSaleOrderDistributionPo> list = query(InfoSaleOrderDistributionPo.class,con);
		if(list != null && list.size()>0){
			return list;
		}else{
			return null;
		}
	}

}
