package com.tydic.unicom.uoc.base.uoccode.service.impl;

import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.CodeOperSendPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeOperSendServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("CodeOperSendServ")
public class CodeOperSendServImpl  extends BaseServImpl<CodeOperSendPo> implements CodeOperSendServ {

	@Override
	public boolean saveDefaultSendInfo(CodeOperSendPo po) throws Exception {
		CodeOperSendPo CodeOperSendPoResult = new CodeOperSendPo();
		CodeOperSendPoResult = get(CodeOperSendPo.class,po);
		if(CodeOperSendPoResult != null){
			update(CodeOperSendPo.class,po);
		}
		else{
			create(CodeOperSendPo.class,po);
		}
		return true;
	}

	@Override
	public CodeOperSendPo getdefaultSendInfo(CodeOperSendPo po)
			throws Exception {
		
		return get(CodeOperSendPo.class,po);
	}

}
