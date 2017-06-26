package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoDeliverOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderExtPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderExtServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderExtServ")
public class InfoServiceOrderExtServImpl extends BaseServImpl<InfoServiceOrderExtPo> implements InfoServiceOrderExtServ{
	
	@Override
	public boolean deleteInfoServiceOrderExtBySaleOrderNo(InfoServiceOrderExtPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteInfoServiceOrderExtBySaleOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoServiceOrderExtPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
		
	}
	
	@Override
	public List<InfoServiceOrderExtPo> queryInfoServiceOrderExtByOrderNo(InfoServiceOrderExtPo po)throws Exception{
		String order_no="";
		if(po.getSale_order_no()!=null){
			order_no=po.getSale_order_no();
		}else if(po.getServ_order_no()!=null){
			order_no=po.getServ_order_no();
		}
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition. build("queryInfoServiceOrderExtByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderExtPo> list = query(InfoServiceOrderExtPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

	@Override
	public boolean insertInfoServiceOrderExt(InfoServiceOrderExtPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getServ_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		create(InfoServiceOrderExtPo.class,po);
		return true;
	}

	@Override
	public boolean updateInfoServiceOrderExt(InfoServiceOrderExtPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getServ_order_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		update(InfoServiceOrderExtPo.class,po);
		return true;
	}

}
