package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.ProcViewInfoPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcViewInfoServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("ProcViewInfoServ")
public class ProcViewInfoServImpl extends BaseServImpl<ProcViewInfoPo> implements ProcViewInfoServ{

	@Override
	public List<ProcViewInfoPo> queryProcViewInfo(ProcViewInfoPo po)
			throws Exception {		
		Condition con = Condition.build("queryProcViewInfo");
		List<ProcViewInfoPo> list = query(ProcViewInfoPo.class,con);
		if(list != null && list.size()>0){
			return list;
		}else{
			return null;
		}
		
	}

}
