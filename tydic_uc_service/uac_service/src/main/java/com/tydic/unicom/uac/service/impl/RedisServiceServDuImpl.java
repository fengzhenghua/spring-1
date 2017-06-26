package com.tydic.unicom.uac.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.tydic.uda.service.S;
import com.tydic.unicom.uac.service.interfaces.RedisServiceServDu;
import com.tydic.unicom.uac.service.vo.RedisDataVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("RedisServiceServDu")
public class RedisServiceServDuImpl implements RedisServiceServDu{

	private static Logger logger = Logger.getLogger(RedisServiceServDuImpl.class);
	
	@Override
	public UocMessage createDataToRedis(RedisDataVo redisDataVo) throws Exception {
		logger.info("==================向redis写入缓存数据=================");
		String id = (String) S.get(RedisDataVo.class).create(redisDataVo);
		UocMessage uocMessage = new UocMessage();
		if(id != null && (!id.equals(""))){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("写入数据到redis缓存成功");
			uocMessage.addArg("id", id);
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("写入数据到redis出错");
		}
		return uocMessage;
	}

	@Override
	public UocMessage queryDataFromRedis(String key) throws Exception {
		logger.info("==================从redis读取缓存数据=================");
		RedisDataVo redisDataVoResult = S.get(RedisDataVo.class).get(key);
		UocMessage uocMessage = new UocMessage();
		if(redisDataVoResult != null){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("从redis读取缓存数据成功");
			uocMessage.addArg("RedisData", redisDataVoResult);
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("读取redis数据失败");
		}
		return uocMessage;
	}

	@Override
	public UocMessage updateDataToRedis(RedisDataVo redisDataVo) throws Exception {
		logger.info("==============更新redis缓存数据==============");
		int result = S.get(RedisDataVo.class).update(redisDataVo);
		UocMessage uocMessage = new UocMessage();
		if(result == 0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("更新redis数据成功");
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("更新redis数据失败");
		}
		return uocMessage;
	}

	@Override
	public UocMessage deleteDataFromRedis(String key) throws Exception {
		logger.info("==============删除redis缓存数据==============");
		int result = S.get(RedisDataVo.class).remove(key);
		UocMessage uocMessage = new UocMessage();
		if(result == 0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("删除redis数据成功");
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("删除redis数据失败");
		}
		return uocMessage;
	}

}
