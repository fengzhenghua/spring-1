package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.CodeListTabFieldPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeListTabFieldServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("CodeListTabFieldServ")
public class CodeListTabFieldServImpl extends BaseServImpl<CodeListTabFieldPo> implements CodeListTabFieldServ{

	@Override
	public List<CodeListTabFieldPo> queryCodeListTabFieldAll() throws Exception {
		Condition condition = Condition.build("queryCodeListTabFieldAll");
		List<CodeListTabFieldPo> list = query(CodeListTabFieldPo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
