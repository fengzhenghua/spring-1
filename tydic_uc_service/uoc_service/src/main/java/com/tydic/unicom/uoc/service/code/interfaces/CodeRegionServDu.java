package com.tydic.unicom.uoc.service.code.interfaces;

import java.util.List;

import com.tydic.unicom.service.cache.service.redis.po.CodeRegion;

public interface CodeRegionServDu {

	public CodeRegion getCodeRegionByRegionCode(CodeRegion codeRegion) throws Exception;
	
	public List<CodeRegion> getCodeRegionByUpperRegionIdAndRegionLevelFromRedis(String upperRegionId,String regionLevel) throws Exception;
}
