package com.tydic.unicom.uoc.service.code.interfaces;

import java.util.List;

import com.tydic.unicom.service.cache.service.redis.po.CodeList;
import com.tydic.unicom.uoc.service.code.vo.CodeListVo;

public interface CodeListServDu {

	public CodeListVo queryCodeListByTypeCode(CodeListVo codeListVo) throws Exception;
	
	public List<CodeListVo> queryCodeListAll() throws Exception;
	
	public List<CodeList> queryCodeListByTypeCodeFromRedis(CodeList codeList) throws Exception;
	
	public CodeList queryCodeListByCodeIdFromRedis(CodeList codeList) throws Exception;

}
