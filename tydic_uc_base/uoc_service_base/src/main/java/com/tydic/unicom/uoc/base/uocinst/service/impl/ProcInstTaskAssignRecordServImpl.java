package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskAssignRecordPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignRecordServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcInstTaskAssignRecordServ")
public class ProcInstTaskAssignRecordServImpl extends BaseServImpl<ProcInstTaskAssignRecordPo> implements ProcInstTaskAssignRecordServ{

	@Override
	public boolean create(ProcInstTaskAssignRecordPo procInstTaskAssignRecordPo) throws Exception {
		create(ProcInstTaskAssignRecordPo.class,procInstTaskAssignRecordPo);
		return true;
	}

	@Override
	public List<ProcInstTaskAssignRecordPo> queryProcInstTaskAssignRecordByOrderNo(
			ProcInstTaskAssignRecordPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition con = Condition.build("queryProcInstTaskAssignRecordByOrderNo").filter(po.convertThis2Map());
		List<ProcInstTaskAssignRecordPo> list = query(ProcInstTaskAssignRecordPo.class,con);
		return (list!=null&&list.size()>0)?list:null;
	}
	@Override
	public boolean deleteProcTaskAssignRecordByOrderNo(ProcInstTaskAssignRecordPo po) throws Exception {
		Map<String, String> strMap = StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));
		Condition condition = Condition.build("deleteTaskAssignRecordByOrderNo").filter(po.convertThis2Map());
		int i=S.get(ProcInstTaskAssignRecordPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	

}
