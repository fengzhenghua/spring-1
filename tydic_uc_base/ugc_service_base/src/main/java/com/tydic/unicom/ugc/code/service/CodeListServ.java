package com.tydic.unicom.ugc.code.service;

import com.tydic.unicom.ugc.code.po.CodeListPo;



public interface CodeListServ {
	
	public CodeListPo queryCodeListByTypeCode(CodeListPo codeListPo) throws Exception;
}
