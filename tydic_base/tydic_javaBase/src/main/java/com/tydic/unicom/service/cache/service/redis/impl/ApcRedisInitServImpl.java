package com.tydic.unicom.service.cache.service.redis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tydic.uda.service.S;
import com.tydic.unicom.service.cache.service.redis.interfaces.ApcRedisInitServ;
import com.tydic.unicom.service.cache.service.redis.po.ApcCodeList;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;

public class ApcRedisInitServImpl implements ApcRedisInitServ{

	private static Logger logger = Logger.getLogger(ApcRedisInitServImpl.class);
	
	private String[] keys = {"apc_code_list"};
	@Override
	public boolean ugcInitCreateDataToRedis() {
		logger.info("===========初始化apc缓存数据到redis===========");
		List<ApcCodeList> codeListList = S.get(ApcCodeList.class).query(null);
		logger.info("=======================（apc）codeList表需要插入的数据条数为："+codeListList.size());
		Map<String,Object> codeListMap = new HashMap<String,Object>();
		codeListMap.put("apc_code_list", codeListList);
		writeDataToRedis(codeListMap,"apc_code_list");
		return true;
	}

	@Override
	public boolean ugcInitDeleteDataFromRedis() {
		int result = 1;
		for(int i=0;i<keys.length;i++){
			result = S.get(RedisData.class).remove(keys[i]);
			if(result == 0){
				logger.info("===========从redis删除"+keys[i]+"表数据成功");
			}
			else{
				logger.info("===========从redis删除"+keys[i]+"表数据失败");
			}
		}
		return true;
	}

	/**
	 * 内部方法，写入数据到redis
	 * */
	public boolean writeDataToRedis(Map<String,Object> map,String key){
		RedisData redisData = new RedisData();
		redisData.setId(key);
		redisData.setMap(map);
		String resultKey = (String) S.get(RedisData.class).create(redisData);
		if(resultKey != null && (!resultKey.equals(""))){
			logger.info("===================缓存"+key+"表数据到redis成功=================");
			return true;
		}
		else{
			logger.info("===================缓存"+key+"表数据到redis失败=================");
			return false;
		}
	}
}
