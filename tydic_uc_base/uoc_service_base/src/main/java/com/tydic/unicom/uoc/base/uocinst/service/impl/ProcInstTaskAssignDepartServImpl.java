package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskAssignDepartPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignDepartServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcInstTaskAssignDepartServ")
public class ProcInstTaskAssignDepartServImpl extends BaseServImpl<ProcInstTaskAssignDepartPo> implements ProcInstTaskAssignDepartServ{

	@Override
	public boolean create(ProcInstTaskAssignDepartPo po) throws Exception {
		create(ProcInstTaskAssignDepartPo.class, po);
		return true;
	}

	@Override
	public boolean update(ProcInstTaskAssignDepartPo po) throws Exception {
		Map<String, String> strMap = StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		update(ProcInstTaskAssignDepartPo.class, po);
		return true;
	}

	@Override
	public boolean delete(ProcInstTaskAssignDepartPo po) throws Exception {
		Map<String, String> strMap = StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		remove(ProcInstTaskAssignDepartPo.class, po);
		return true;
	}

	@Override
	public ProcInstTaskAssignDepartPo queryProcInstTaskAssignDepartByPo(
			ProcInstTaskAssignDepartPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryProcInstTaskAssignDepartByPo").filter(po.convertThis2Map());
		List<ProcInstTaskAssignDepartPo> list = query(ProcInstTaskAssignDepartPo.class,con);
		return (list.isEmpty() || list == null)?null:list.get(0);
	}
}
