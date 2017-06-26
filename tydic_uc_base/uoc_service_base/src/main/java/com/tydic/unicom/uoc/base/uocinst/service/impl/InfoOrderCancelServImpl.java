package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOrderCancelPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOrderCancelServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoOrderCancelServ")
public class InfoOrderCancelServImpl extends BaseServImpl<InfoOrderCancelPo> implements InfoOrderCancelServ{

	@Override
	public boolean updateInfoOrderCancel(InfoOrderCancelPo po) throws Exception {
		Map<String, String> strMap = StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		update(InfoOrderCancelPo.class, po);
		return true;
	}
	@Override
	public boolean create(InfoOrderCancelPo po) throws Exception {
		Map<String,String> strMap=StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setArea_code(strMap.get("area_code"));
		po.setPart_month(strMap.get("part_month"));
		create(InfoOrderCancelPo.class,po);
		return true;
	}
	@Override
	public InfoOrderCancelPo queryInfoOrderCancel(InfoOrderCancelPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("queryInfoOrderCancelByPo").filter(po.convertThis2Map());
		List<InfoOrderCancelPo> list = query(InfoOrderCancelPo.class, condition);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	@Override
	public List<InfoOrderCancelPo> queryInfoOrderCancelList(InfoOrderCancelPo po)
			throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("queryInfoOrderCancelByPo").filter(po.convertThis2Map());
		List<InfoOrderCancelPo> list = query(InfoOrderCancelPo.class, condition);
		if(list!=null){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public boolean deleteByOrderNo(InfoOrderCancelPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));	
		Condition condition = Condition.build("deleteByOrderNo").filter(po.convertThis2Map());
		int i=S.get(InfoOrderCancelPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}	
	}

}
