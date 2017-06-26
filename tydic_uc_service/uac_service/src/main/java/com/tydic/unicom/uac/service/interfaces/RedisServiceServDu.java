package com.tydic.unicom.uac.service.interfaces;

import com.tydic.unicom.uac.service.vo.RedisDataVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface RedisServiceServDu {

	/**
	 * 向redis写入缓存数据
	 * */
	public UocMessage createDataToRedis(RedisDataVo redisDataVo) throws Exception;
	
	/**
	 * 从redis读取缓存数据
	 * */
	public UocMessage queryDataFromRedis(String key) throws Exception;
	
	/**
	 * 更新redis缓存数据
	 * */
	public UocMessage updateDataToRedis(RedisDataVo redisDataVo) throws Exception;
	
	/**
	 * 删除redis缓存数据
	 * */
	public UocMessage deleteDataFromRedis(String key) throws Exception;
}
