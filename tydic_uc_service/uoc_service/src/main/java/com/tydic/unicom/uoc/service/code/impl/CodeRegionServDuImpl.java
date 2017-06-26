package com.tydic.unicom.uoc.service.code.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.CodeRegion;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.service.code.interfaces.CodeRegionServDu;
import com.tydic.unicom.webUtil.UocMessage;
@Service("CodeRegionServDu")
public class CodeRegionServDuImpl implements CodeRegionServDu {

	private static Logger logger = Logger.getLogger(CodeRegionServDuImpl.class);
	
	@Autowired
	private RedisServiceServ redisServiceServ;
	
	@Override
	public CodeRegion getCodeRegionByRegionCode(CodeRegion codeRegion) throws Exception {
		UocMessage message = redisServiceServ.queryDataFromRedis("code_region");
		RedisData redisData = (RedisData) message.getArgs().get("RedisDataResult");
		Map<String,Object> dataMap = redisData.getMap();
		String region_code =codeRegion.getRegion_code();
		codeRegion =(CodeRegion)dataMap.get("RegionCode"+region_code);
		return codeRegion;
	}

	@Override
	public List<CodeRegion> getCodeRegionByUpperRegionIdAndRegionLevelFromRedis(String upperRegionId, String regionLevel) throws Exception {
		logger.info("===========根据传入条件取出code_region表(缓存里取)数据===========");
		UocMessage message = redisServiceServ.queryDataFromRedis("code_region");
		RedisData redisData = (RedisData) message.getArgs().get("RedisDataResult");
		Map<String,Object> dataMap = redisData.getMap();
		String key = "";
		if((!StringUtils.isEmpty(upperRegionId)) && (!StringUtils.isEmpty(regionLevel))){
			key = upperRegionId + "_" + regionLevel;
		}
		else if((!StringUtils.isEmpty(upperRegionId)) && (StringUtils.isEmpty(regionLevel))){
			key = "UpperRegionId" + upperRegionId;
		}
		else if((StringUtils.isEmpty(upperRegionId)) && (!StringUtils.isEmpty(regionLevel))){
			key = "RegionLevel" + regionLevel;
		}
		else{
			return null;
		}
		List<CodeRegion> codeRegionList = (List<CodeRegion>) dataMap.get(key);
		return codeRegionList;
	}

}
