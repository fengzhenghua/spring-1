package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoDeliverOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoDeliverOrderServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoDeliverOrderServ")
public class InfoDeliverOrderServImpl extends BaseServImpl<InfoDeliverOrderPo> implements InfoDeliverOrderServ {

	@Override
	public InfoDeliverOrderPo getInfoDeliverOrderByPayDeliverOrderNo(
			InfoDeliverOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getDeliver_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		return get(InfoDeliverOrderPo.class,po);
	}

	@Override
	public boolean updateInfoDeliverOrder(InfoDeliverOrderPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getDeliver_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		update(InfoDeliverOrderPo.class,po);
		return true;
	}

	@Override
	public List<InfoDeliverOrderPo> queryInfoDeliverOrderBySaleOrderNo(
			InfoDeliverOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryInfoDeliverOrderBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoDeliverOrderPo> list = query(InfoDeliverOrderPo.class,con);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}
	
	@Override
	public boolean deleteInfoDeliverOrderByPayDeliverOrderNo(InfoDeliverOrderPo po) throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoDeliverOrderPo.class,po);
		return true;
		
	}

	@Override
	public boolean create(InfoDeliverOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		create(InfoDeliverOrderPo.class,po);
		return true;
	}

	@Override
	public List<InfoDeliverOrderPo> queryInfoDeliverOrderAll(InfoDeliverOrderPo po) throws Exception{
		Condition con = Condition.build("queryInfoDeliverOrderAll").filter(po.convertThis2Map());
		List<InfoDeliverOrderPo> list = query(InfoDeliverOrderPo.class,con);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public List<InfoDeliverOrderPo> queryInfoDeliverOrderByContactTel(
			InfoDeliverOrderPo po) throws Exception {
		Condition con = Condition.build("queryInfoDeliverOrderByContactTel").filter(po.convertThis2Map());
		List<InfoDeliverOrderPo> list = query(InfoDeliverOrderPo.class,con);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
