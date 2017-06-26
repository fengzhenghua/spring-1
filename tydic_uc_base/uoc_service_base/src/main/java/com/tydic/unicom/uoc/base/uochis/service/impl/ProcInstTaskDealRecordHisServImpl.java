package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskDealRecordHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskDealRecordHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcInstTaskDealRecordHisServ")
public class ProcInstTaskDealRecordHisServImpl extends BaseServImpl<ProcInstTaskDealRecordHisPo> implements ProcInstTaskDealRecordHisServ{

	@Override
	public boolean createProcInstTaskDealRecordHisPo(ProcInstTaskDealRecordHisPo po)throws Exception{
		if(po==null){
			return false;
		}
		create(ProcInstTaskDealRecordHisPo.class,po);
		return true;
	}

	@Override
	public List<ProcInstTaskDealRecordHisPo> queryTaskDealRecordHisByOrderNo(ProcInstTaskDealRecordHisPo po) throws Exception {
		Map<String, String> strMap = StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryTaskDealRecordHisByOrderNo").filter(po.convertThis2Map());
		List<ProcInstTaskDealRecordHisPo> list = query(ProcInstTaskDealRecordHisPo.class, con);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
}
