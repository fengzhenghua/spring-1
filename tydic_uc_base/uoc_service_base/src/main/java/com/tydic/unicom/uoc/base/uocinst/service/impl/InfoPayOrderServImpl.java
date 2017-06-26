package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoPayOrderServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoPayOrderServ")
public class InfoPayOrderServImpl extends BaseServImpl<InfoPayOrderPo> implements InfoPayOrderServ {

	@Override
	public InfoPayOrderPo getInfoPayOrderByPayOrderNo(InfoPayOrderPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getPay_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		return get(InfoPayOrderPo.class,po);
	}

	@Override
	public boolean updateInfoPayOrder(InfoPayOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getPay_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		update(InfoPayOrderPo.class,po);
		return true;
	}

	@Override
	public boolean createInfoPayOrder(InfoPayOrderPo po) throws Exception {
		create(InfoPayOrderPo.class,po);
		return true;
	}

	/*
	 * 根据销售订单号查支付订单表??  rela_order_no关联订单号   rela_order_type关联订单类型
	 */
	@Override
	public InfoPayOrderPo queryInfoPayOrderByRelaOrderNo(InfoPayOrderPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getRela_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("queryInfoPayOrderByRelaOrderNo").filter(po.convertThis2Map());
		List<InfoPayOrderPo> list = query(InfoPayOrderPo.class, condition);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

//	@Override
//	public List<InfoPayOrderPo> queryInfoPayOrderByRelaOrderNo(InfoPayOrderPo po)
//			throws Exception {
//		Condition con = Condition.build("queryInfoPayOrderByRelaOrderNo").filter(po.convertThis2Map());
//		List<InfoPayOrderPo> list = query(InfoPayOrderPo.class,con);
//		if(list!=null&&list.size()>0){
//			return list;
//		}
//		else{
//			return null;
//		}
//	}
	
	@Override
	public boolean deleteInfoPayOrderByPayOrderNo(InfoPayOrderPo po){
		remove(InfoPayOrderPo.class,po);
		return true;
	}
	
	@Override
	public List<InfoPayOrderPo> queryInfoPayOrderByPayType(InfoPayOrderPo po) throws Exception{
		
		Condition condition = Condition.build("queryInfoPayOrderByPayType").filter(po.convertThis2Map());
		List<InfoPayOrderPo> list = query(InfoPayOrderPo.class, condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
