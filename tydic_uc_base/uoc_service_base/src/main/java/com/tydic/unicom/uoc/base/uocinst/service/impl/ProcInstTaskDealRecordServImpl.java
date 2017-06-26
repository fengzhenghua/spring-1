package com.tydic.unicom.uoc.base.uocinst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskDealRecordPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskDealRecordServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcInstTaskDealRecordServ")
public class ProcInstTaskDealRecordServImpl extends BaseServImpl<ProcInstTaskDealRecordPo> implements ProcInstTaskDealRecordServ{
	
	@Override
	public List<ProcInstTaskDealRecordPo> queryProcInstTaskDealRecordByOrderNo(ProcInstTaskDealRecordPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));	
		Condition con = Condition.build("queryProcInstTaskDealRecordByOrderNo").filter(po.convertThis2Map());
		List<ProcInstTaskDealRecordPo> list= query(ProcInstTaskDealRecordPo.class,con);
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public boolean deleteProcInstTaskDealRecordByOrderNo(ProcInstTaskDealRecordPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getOrder_no());
		po.setPart_month(strMap.get("part_month"));
		po.setArea_code(strMap.get("area_code"));		
		Condition condition = Condition.build("deleteProcInstTaskDealRecordByOrderNo").filter(po.convertThis2Map());
		int i=S.get(ProcInstTaskDealRecordPo.class).remove(condition);
		if(i>0){
			return true;
		}else{
			return false;
		}		
	}
	
	@Override
	public boolean create(ProcInstTaskDealRecordPo po)throws Exception{
		if(po==null){
			return false;
		}		
		create(ProcInstTaskDealRecordPo.class,po);
		return true;
	}

}
