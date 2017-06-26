package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderEditPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderEditServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoSaleOrderEditServ")
public class InfoSaleOrderEditServImpl extends BaseServImpl<InfoSaleOrderEditPo> implements InfoSaleOrderEditServ {

	@Override
	public InfoSaleOrderEditPo getInfoSaleOrderEditPoBySaleOrderNo(
			InfoSaleOrderEditPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		return get(InfoSaleOrderEditPo.class,po);
	}

	@Override
	public boolean createInfoSaleOrderEditPo(InfoSaleOrderEditPo po)
			throws Exception {
		
		create(InfoSaleOrderEditPo.class,po);
		return true;
	}
	
	@Override
	public boolean delete(InfoSaleOrderEditPo po){
		Map<String, String> strMap =new HashMap<String,String>();
		try {
			strMap = StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(InfoSaleOrderEditPo.class,po);
		return true;
	}

	@Override
	public List<InfoSaleOrderEditPo> queryInfoSaleOrderEditPoBySaleOrderNo(
			InfoSaleOrderEditPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition. build("queryInfoSaleOrderEditPoBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderEditPo> list = query(InfoSaleOrderEditPo.class,con);
		if(list !=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
		
	}

}
