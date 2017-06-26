package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderAttrPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderAttrServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoSaleOrderAttrServ")
public class InfoSaleOrderAttrServImpl extends BaseServImpl<InfoSaleOrderAttrPo> implements InfoSaleOrderAttrServ {

	@Override
	public boolean deleteInfoSaleOrderAttrBySaleOrderNo(InfoSaleOrderAttrPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoSaleOrderAttrPo.class,po);
		return true;
	}
	
	@Override
	public List<InfoSaleOrderAttrPo> queryInfoSaleOrderAttrBySaleOrderNo(InfoSaleOrderAttrPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("queryInfoSaleOrderAttrBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderAttrPo> list = S.get(InfoSaleOrderAttrPo.class).query(condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}


}
