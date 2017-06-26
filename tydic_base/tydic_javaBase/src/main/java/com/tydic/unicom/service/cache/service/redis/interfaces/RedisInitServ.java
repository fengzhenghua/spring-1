package com.tydic.unicom.service.cache.service.redis.interfaces;

public interface RedisInitServ {

	/**
	 * 初始化写入数据到redis
	 * */
	public boolean initCreateDataToRedis();
	
	/**
	 * 初始化删除数据从redis
	 * */
	public boolean initDeleteDataFromRedis();
	
	public boolean createStaticDataToRedis ();
	
	public boolean deleteStaticDataToRedis();
	
}
