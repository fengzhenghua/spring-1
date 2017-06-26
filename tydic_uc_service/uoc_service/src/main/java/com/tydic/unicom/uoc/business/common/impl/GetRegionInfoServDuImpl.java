package com.tydic.unicom.uoc.business.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.CodeList;
import com.tydic.unicom.service.cache.service.redis.po.CodeRegion;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.business.common.interfaces.GetRegionInfoServDu;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.uoc.service.code.interfaces.CodeRegionServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class GetRegionInfoServDuImpl implements GetRegionInfoServDu{

	private static Logger logger = Logger.getLogger(GetRegionInfoServDuImpl.class);
	
	@Autowired
	private OperServDu operServDu;
	
	@Autowired
	private CodeRegionServDu codeRegionServDu;
	
	@Autowired
	private CodeListServDu codeListServDu;
	
	@Autowired
	private RedisServiceServ redisServiceServ;
	
	@Override
	public UocMessage getRegionInfo(String jsessionId, String regionLevel,String upperRegionId) throws Exception {
		logger.info("===============获取地区信息服务================");
		logger.info("==============校验是否登陆================");
		UocMessage uocMessage = new UocMessage();
		//获取登陆信息服务，校验是否登陆
		UocMessage uocMessageLogin = operServDu.isLogin(jsessionId);
		if(!"0000".equals(uocMessageLogin.getRespCode())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent(uocMessageLogin.getContent());
			return uocMessage;
		}
		Map<String,Object> oper_info =(Map<String, Object>) uocMessageLogin.getArgs().get("oper_info");

		
		List<CodeRegion> list = codeRegionServDu.getCodeRegionByUpperRegionIdAndRegionLevelFromRedis(upperRegionId, regionLevel);
		if(list != null && list.size()>0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.addArg("RegionList", list);
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取地区信息失败");
		}
		return uocMessage;
	}

	@Override
	public UocMessage getDefaultProvinceCityAreaInfo(String jsession_id) throws Exception {
		logger.info("==================获取默认省份，城市和区域信息====================");
		logger.info("==============校验是否登陆================");
		UocMessage uocMessage = new UocMessage();
		//获取登陆信息服务，校验是否登陆
		UocMessage uocMessageLogin = operServDu.isLogin(jsession_id);
		if(!"0000".equals(uocMessageLogin.getRespCode())){
			return uocMessageLogin;
		}
		//获取工号信息
		Map<String,Object> oper_info =(Map<String, Object>) uocMessageLogin.getArgs().get("oper_info");
		
		
		UocMessage messageRedis = redisServiceServ.queryDataFromRedis("default_region_info");
		if(!"0000".equals(messageRedis.getRespCode())){
			return messageRedis;
		}
		else{
			RedisData redisData = (RedisData) messageRedis.getArgs().get("RedisDataResult");
			Map<String,Object> dataMap = redisData.getMap();
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.addArg("default_region_info", dataMap);
			return uocMessage;
		}
	}

}
