package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderServMapPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderServMapServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoSaleOrderServMapServ")
public class InfoSaleOrderServMapServImpl extends BaseServImpl<InfoSaleOrderServMapPo> implements InfoSaleOrderServMapServ {

	@Override
	public List<InfoSaleOrderServMapPo> queryInfoSaleOrderServMapBySaleOrderNo(
			InfoSaleOrderServMapPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("queryInfoSaleOrderServMapBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderServMapPo> list = S.get(InfoSaleOrderServMapPo.class).query(condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean deleteInfoSaleOrderServMapBySaleOrderNo(
			InfoSaleOrderServMapPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoSaleOrderServMapPo.class,po);
		return true;
	}
	
	@Override
	public List<InfoSaleOrderServMapPo> queryInfoSaleOrderServMapByAccNbr(InfoSaleOrderServMapPo po)throws Exception{
		Condition condition = Condition.build("queryInfoSaleOrderServMapByAccNbr").filter(po.convertThis2Map());
		List<InfoSaleOrderServMapPo> list = S.get(InfoSaleOrderServMapPo.class).query(condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean createInfoSaleOrderServMap(InfoSaleOrderServMapPo po)
			throws Exception {
		create(InfoSaleOrderServMapPo.class,po);
		return true;
	}

}
