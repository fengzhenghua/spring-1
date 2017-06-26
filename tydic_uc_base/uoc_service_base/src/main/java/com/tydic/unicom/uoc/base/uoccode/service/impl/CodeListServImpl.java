package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.CodeListPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeListServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("CodeListServ")
public class CodeListServImpl extends BaseServImpl<CodeListPo> implements CodeListServ{

	@Override
	public CodeListPo queryCodeListByTypeCode(CodeListPo codeListPo) throws Exception {
		
		Condition con = Condition.build("queryCodeListByTypeCode").filter( codeListPo.convertThis2Map());
		List<CodeListPo> list= query(CodeListPo.class,con);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public List<CodeListPo> queryCodeListAll() throws Exception {
		Condition condition = Condition.build("queryCodeListAll");
		List<CodeListPo> list = query(CodeListPo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean update(CodeListPo codeListPo) throws Exception {
		update(CodeListPo.class,codeListPo);
		return true;
	}

}
