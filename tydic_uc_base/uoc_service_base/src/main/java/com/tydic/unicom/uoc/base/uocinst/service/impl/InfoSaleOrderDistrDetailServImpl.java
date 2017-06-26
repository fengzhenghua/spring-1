package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderDistrDetailPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderDistrDetailServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoSaleOrderDistrDetailServ")
public class InfoSaleOrderDistrDetailServImpl extends BaseServImpl<InfoSaleOrderDistrDetailPo> implements
InfoSaleOrderDistrDetailServ {

	@Override
	public boolean deleteInfoSaleOrderDistrDetailBySaleOrderNo(
			InfoSaleOrderDistrDetailPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoSaleOrderDistrDetailPo.class,po);
		return true;
	}

	@Override
	public List<InfoSaleOrderDistrDetailPo> queryInfoSaleOrderDistrDetailBySaleOrderNo(InfoSaleOrderDistrDetailPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition. build("queryInfoSaleOrderDistrDetailBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderDistrDetailPo> list = query(InfoSaleOrderDistrDetailPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

	@Override
	public List<InfoSaleOrderDistrDetailPo> queryInfoSaleOrderDistrDetailByPayOrderNo(
			InfoPayOrderPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getRela_order_no());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("part_month", strMap.get("part_month"));
		map.put("area_code", strMap.get("area_code"));
		map.put("sale_order_no", po.getRela_order_no());
		map.put("rela_order_type", po.getRela_order_type());
		Condition con = Condition. build("queryInfoSaleOrderDistrDetailByPayOrderNo").filter(map);
		List<InfoSaleOrderDistrDetailPo> list = query(InfoSaleOrderDistrDetailPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}
