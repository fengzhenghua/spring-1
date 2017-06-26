package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.CodeTypePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeTypeServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("CodeTypeServ")
public class CodeTypeServImpl extends BaseServImpl<CodeTypePo> implements CodeTypeServ{

	@Override
	public List<CodeTypePo> queryCodeTypeAll() throws Exception {
		Condition condition = Condition.build("queryCodeTypeAll");
		List<CodeTypePo> list = query(CodeTypePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
