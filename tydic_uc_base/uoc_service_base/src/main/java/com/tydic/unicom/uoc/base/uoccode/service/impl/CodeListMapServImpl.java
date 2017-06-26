package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.CodeListMapPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeListMapServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("CodeListMapServ")
public class CodeListMapServImpl extends BaseServImpl<CodeListMapPo> implements CodeListMapServ{

	@Override
	public List<CodeListMapPo> queryCodeListMapAll() throws Exception {
		Condition condition = Condition.build("queryCodeListMapAll");
		List<CodeListMapPo> list = query(CodeListMapPo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
