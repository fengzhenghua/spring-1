package com.tydic.unicom.ugc.service.code.interfaces;

import java.util.List;

import com.tydic.unicom.service.cache.service.redis.po.UgcCodeList;
import com.tydic.unicom.ugc.service.code.vo.CodeListVo;


public interface CodeListServDu {

	public CodeListVo queryCodeListByTypeCode(CodeListVo codeListVo) throws Exception;

	public List<UgcCodeList> queryCodeListByTypeCodeFromRedis(UgcCodeList codeList) throws Exception;

	public UgcCodeList queryCodeListByCodeIdFromRedis(UgcCodeList codeList) throws Exception;

}
