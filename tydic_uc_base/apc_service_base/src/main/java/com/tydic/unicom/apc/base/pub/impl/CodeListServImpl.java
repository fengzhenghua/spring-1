package com.tydic.unicom.apc.base.pub.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.apc.base.pub.interfaces.CodeListServ;
import com.tydic.unicom.apc.base.pub.po.CodeListPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("CodeListServ")
public class CodeListServImpl extends BaseServImpl<CodeListPo> implements CodeListServ {

	private static Logger logger = Logger.getLogger(CodeListServImpl.class);

	@Override
	public List<CodeListPo> queryCodeListByTypeCode(String typeCode) {
		Condition con = Condition.build("queryCodeListByTypeCode").filter("type_code",typeCode);
		List<CodeListPo> list = S.get(CodeListPo.class).query(con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}
	
}
