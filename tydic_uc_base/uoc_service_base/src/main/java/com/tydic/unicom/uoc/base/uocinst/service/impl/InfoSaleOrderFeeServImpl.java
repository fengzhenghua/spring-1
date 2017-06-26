package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderFeePo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderFeeServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoSaleOrderFeeServ")
public class InfoSaleOrderFeeServImpl extends BaseServImpl<InfoSaleOrderFeePo> implements InfoSaleOrderFeeServ{

	@Override
	public InfoSaleOrderFeePo getInfoSaleOrderFeePoBySaleOrderNo(InfoSaleOrderFeePo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		return get(InfoSaleOrderFeePo.class,po);
	}

	@Override
	public boolean createInfoSaleOrderFee(InfoSaleOrderFeePo po)
			throws Exception {
		create(InfoSaleOrderFeePo.class,po);
		return true;
	}
	
	@Override
	public boolean delete(InfoSaleOrderFeePo po){
		Map<String, String> strMap =new HashMap<String,String>();
		try {
			strMap = StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoSaleOrderFeePo.class,po);
		return true;
	}

	@Override
	public List<InfoSaleOrderFeePo> queryInfoSaleOrderFeeByPayOrder(
			InfoPayOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getRela_order_no());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("part_month", strMap.get("part_month"));
		map.put("area_code", strMap.get("area_code"));
		map.put("sale_order_no", po.getRela_order_no());
		map.put("rela_order_type", po.getRela_order_type());
		Condition con = Condition.build("queryInfoSaleOrderFeeByPayOrder").filter(map);
		 List<InfoSaleOrderFeePo> list = query(InfoSaleOrderFeePo.class,con);
		if(list != null && list.size()>0){
			return list;
		}else{
			return null;
		}
		 
	}

}
