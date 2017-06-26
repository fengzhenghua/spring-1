package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskAssignOperPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignOperServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcInstTaskAssignOperServ")
public class ProcInstTaskAssignOperServImpl extends BaseServImpl<ProcInstTaskAssignOperPo> implements ProcInstTaskAssignOperServ{

	@Override
	public boolean create(ProcInstTaskAssignOperPo procInstTaskAssignOperPo) throws Exception {
		create(ProcInstTaskAssignOperPo.class,procInstTaskAssignOperPo);
		return true;
	}

	@Override
	public boolean update(ProcInstTaskAssignOperPo po) throws Exception {
		Map<String, String> strMap = StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		update(ProcInstTaskAssignOperPo.class, po);
		return true;
	}

	@Override
	public boolean delete(ProcInstTaskAssignOperPo po) throws Exception {
		Map<String, String> strMap = StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(ProcInstTaskAssignOperPo.class, po);
		return true;
	}

	@Override
	public ProcInstTaskAssignOperPo queryProcInstTaskAssignOperByPo(
			ProcInstTaskAssignOperPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryProcInstTaskAssignOperByPo").filter(po.convertThis2Map());
		List<ProcInstTaskAssignOperPo> list = query(ProcInstTaskAssignOperPo.class,con);
		return (list.isEmpty() || list == null)?null:list.get(0);
	}
}
