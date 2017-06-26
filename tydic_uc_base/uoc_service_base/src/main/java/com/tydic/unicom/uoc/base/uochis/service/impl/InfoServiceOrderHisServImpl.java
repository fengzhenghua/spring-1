package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderHisServ")
public class InfoServiceOrderHisServImpl extends BaseServImpl<InfoServiceOrderHisPo> implements InfoServiceOrderHisServ{

	@Override
	public boolean createInfoServiceOrderHisPo(InfoServiceOrderHisPo po)throws Exception{
		if(po==null){
			return false;
		}
		create(InfoServiceOrderHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoServiceOrderHisPo> queryInfoServiceOrderHisByOrderNo(
			InfoServiceOrderHisPo po) throws Exception {
		String order_no="";
		if(po.getSale_order_no()!=null){
			order_no=po.getSale_order_no();
		}else if(po.getOfr_order_no()!=null){
			order_no=po.getOfr_order_no();
		}else if(po.getServ_order_no()!=null){
			order_no=po.getServ_order_no();
		}
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}
		Condition con = Condition. build("queryInfoServiceOrderHisByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderHisPo> list = query(InfoServiceOrderHisPo.class,con);
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

	@Override
	public List<InfoServiceOrderHisPo> queryInfoServiceOrderHisByPo(InfoServiceOrderHisPo po)
			throws Exception {
		Condition condition = Condition.build("queryInfoServiceOrderHisByPo").filter(po.convertThis2Map());
		List<InfoServiceOrderHisPo> list =query(InfoServiceOrderHisPo.class,condition);
		return (list!=null&&list.size()>0)?list:null;
	}
}
