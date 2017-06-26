package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoOrderCancelHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOrderCancelHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoOrderCancelHisServ")
public class InfoOrderCancelHisServImpl extends BaseServImpl<InfoOrderCancelHisPo> implements InfoOrderCancelHisServ{


	@Override
	public boolean create(InfoOrderCancelHisPo po) throws Exception {
		Map<String,String> strMap=StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setArea_code(strMap.get("area_code"));
		po.setPart_month(strMap.get("part_month"));
		create(InfoOrderCancelHisPo.class,po);
		return true;
	}
		
	
	@Override
	public List<InfoOrderCancelHisPo> queryInfoOrderCancelList(InfoOrderCancelHisPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("queryInfoOrderCancelHisByPo").filter(po.convertThis2Map());
		List<InfoOrderCancelHisPo> list = query(InfoOrderCancelHisPo.class, condition);
		return list;
	}
}
