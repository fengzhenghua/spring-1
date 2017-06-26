package com.tydic.unicom.service.cache.service.redis.interfaces;

public interface ApcRedisInitServ {

	/**
	 * 初始化写入数据到redis
	 * */
	public boolean ugcInitCreateDataToRedis();
	
	/**
	 * 初始化删除数据从redis
	 * */
	public boolean ugcInitDeleteDataFromRedis();
}
