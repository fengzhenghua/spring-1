package com.tydic.unicom.service.cache.service.redis.impl;

import org.apache.log4j.Logger;

import com.tydic.uda.service.S;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class RedisServiceServImpl implements RedisServiceServ{

	private static Logger logger = Logger.getLogger(RedisServiceServImpl.class);
	
	@Override
	public UocMessage createDataToRedis(RedisData redisData) throws Exception {
		logger.info("================redisData createDataToRedis=================");
		String key = (String) S.get(RedisData.class).create(redisData);
		UocMessage uocMessage = new UocMessage();
		if(key != null && (!key.equals(""))){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.addArg("key", key);
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("写入数据到redis出错");
		}
		return uocMessage;
	}

	@Override
	public UocMessage queryDataFromRedis(String key) throws Exception {
		RedisData redisDataResult = S.get(RedisData.class).get(key);
		UocMessage uocMessage = new UocMessage();
		if(redisDataResult != null){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.addArg("RedisDataResult", redisDataResult);
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("读取redis数据出错");
		}
		return uocMessage;
	}

	@Override
	public UocMessage updateDataToRedis(RedisData redisData) throws Exception {
		int result = S.get(RedisData.class).update(redisData);
		UocMessage uocMessage = new UocMessage();
		if(result == 0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("更新redis数据出错");
		}
		return uocMessage;
	}

	@Override
	public UocMessage deleteDataFromRedis(String key) throws Exception {
		int result = S.get(RedisData.class).remove(key);
		UocMessage uocMessage = new UocMessage();
		if(result == 0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("删除redis数据出错");
		}
		return uocMessage;
	}

}
