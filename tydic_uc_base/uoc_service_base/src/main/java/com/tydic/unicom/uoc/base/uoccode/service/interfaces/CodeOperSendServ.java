package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import com.tydic.unicom.uoc.base.uoccode.po.CodeOperSendPo;

public interface CodeOperSendServ {

	public boolean saveDefaultSendInfo(CodeOperSendPo po) throws Exception;
	
	public CodeOperSendPo getdefaultSendInfo(CodeOperSendPo po) throws Exception;
}
