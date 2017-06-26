package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoOfrOrderServ")
public class InfoOfrOrderServImpl extends BaseServImpl<InfoOfrOrderPo> implements InfoOfrOrderServ {

	Logger logger = Logger.getLogger(InfoOfrOrderServImpl.class);
	
	@Override
	public InfoOfrOrderPo getInfoOfrOrderByOfrOrderNo(InfoOfrOrderPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOfr_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		return get(InfoOfrOrderPo.class,po);
	}

	@Override
	public boolean createInfoOfrOrder(InfoOfrOrderPo po)
			throws Exception {
		
		create(InfoOfrOrderPo.class,po);
		return true;
	}

	@Override
	public boolean updateInfoOfrOrder(InfoOfrOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOfr_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		update(InfoOfrOrderPo.class,po);
		return true;
	}

	@Override
	public List<InfoOfrOrderPo> queryInfoOfrOrderBySaleOrderNo(InfoOfrOrderPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("queryInfoOfrOrderBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoOfrOrderPo> list =query(InfoOfrOrderPo.class,condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}
	
	@Override
	public boolean deleteInfoOfrOrderBySaleOrderNo(InfoOfrOrderPo po) throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoOfrOrderBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoOfrOrderPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}

}
