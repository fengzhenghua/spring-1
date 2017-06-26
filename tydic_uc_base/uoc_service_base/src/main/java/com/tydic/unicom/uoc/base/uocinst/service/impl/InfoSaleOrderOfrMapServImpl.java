package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderOfrMapPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderOfrMapServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoSaleOrderOfrMapServ")
public class InfoSaleOrderOfrMapServImpl extends BaseServImpl<InfoSaleOrderOfrMapPo> implements InfoSaleOrderOfrMapServ {

	@Override
	public List<InfoSaleOrderOfrMapPo> queryInfoSaleOrderOfrMapBySaleOrderNo(
			InfoSaleOrderOfrMapPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("queryInfoSaleOrderOfrMapBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderOfrMapPo> list = S.get(InfoSaleOrderOfrMapPo.class).query(condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean deleteInfoSaleOrderOfrMapBySaleOrderNo(
			InfoSaleOrderOfrMapPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoSaleOrderOfrMapPo.class,po);
		return true;
	}
	
	@Override
	public List<InfoSaleOrderOfrMapPo> queryInfoSaleOrderOfrMapByAccNbr(InfoSaleOrderOfrMapPo po)throws Exception{
		Condition condition = Condition.build("queryInfoSaleOrderOfrMapByAccNbr").filter(po.convertThis2Map());
		List<InfoSaleOrderOfrMapPo> list = S.get(InfoSaleOrderOfrMapPo.class).query(condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}
}
