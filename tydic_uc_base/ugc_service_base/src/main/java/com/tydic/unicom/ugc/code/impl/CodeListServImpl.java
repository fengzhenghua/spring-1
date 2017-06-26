package com.tydic.unicom.ugc.code.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.ugc.code.po.CodeListPo;
import com.tydic.unicom.ugc.code.service.CodeListServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("CodeListServ")
public class CodeListServImpl extends  BaseServImpl<CodeListPo> implements CodeListServ{

	@Override
	public CodeListPo queryCodeListByTypeCode(CodeListPo codeListPo)
			throws Exception {
		Condition con = Condition.build("queryCodeListByTypeCode").filter( codeListPo.convertThis2Map());
		List<CodeListPo> list= query(CodeListPo.class,con);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}
	
	
}
