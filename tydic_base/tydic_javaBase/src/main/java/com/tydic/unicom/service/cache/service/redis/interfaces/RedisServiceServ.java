package com.tydic.unicom.service.cache.service.redis.interfaces;

import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.webUtil.UocMessage;

public interface RedisServiceServ {

	/**
	 * 向redis写入缓存数据
	 * */
	public UocMessage createDataToRedis(RedisData redisData) throws Exception;
	
	/**
	 * 从redis读取缓存数据
	 * */
	public UocMessage queryDataFromRedis(String key) throws Exception;
	
	/**
	 * 更新redis缓存数据
	 * */
	public UocMessage updateDataToRedis(RedisData redisData) throws Exception;
	
	/**
	 * 删除redis缓存数据
	 * */
	public UocMessage deleteDataFromRedis(String key) throws Exception;
}
