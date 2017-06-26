package com.tydic.unicom.apc.service.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.tydic.uda.service.S;
import com.tydic.unicom.apc.business.pub.vo.ApAttrVo;
import com.tydic.unicom.apc.service.common.interfaces.ApcRedisServiceServDu;
import com.tydic.unicom.apc.service.common.vo.ApcRedisData;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ApcRedisServiceServDu")
public class ApcRedisServiceServDuImpl implements ApcRedisServiceServDu{
	
	private static Logger logger = Logger.getLogger(ApcRedisServiceServDuImpl.class);

	@Override
	public UocMessage addOrUpdateForApAttr(List<ApAttrVo> list) throws Exception {
		UocMessage uocMessage = new UocMessage();
		if(list != null && list.size()>0){
			String redisKey = "apc_ap_attr_" + list.get(0).getAp_id();
			//封装要添加/更新的数据
			ApcRedisData addOrUpdate = new ApcRedisData();
			addOrUpdate.setId(redisKey);
			Map<String,Object> redisDataMap = new HashMap<String,Object>();
			for(int i=0;i<list.size();i++){
				redisDataMap.put(list.get(i).getAttr_id(), list.get(i).getAttr_value());
			}
			addOrUpdate.setMap(redisDataMap);
			//查询redis中是否存在对应key的数据
			ApcRedisData redisQueryResult = S.get(ApcRedisData.class).get(redisKey);
			if(redisQueryResult != null){
				int updateResult = S.get(ApcRedisData.class).update(addOrUpdate);
				if(updateResult == 0){
					logger.info("================更新redis数据成功（触点属性使用）==============");
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					uocMessage.setContent("更新redis数据成功");
				}
				else{
					logger.info("================更新redis数据失败（触点属性使用）==============");
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("更新redis数据失败");
				}
			}
			else{
				String addResult = (String) S.get(ApcRedisData.class).create(addOrUpdate);
				if(addResult != null && (!"".equals(addResult))){
					logger.info("================添加redis数据成功（触点属性使用）==============");
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					uocMessage.setContent("添加redis数据成功");
					uocMessage.addArg("redisKey", addResult);
				}
				else{
					logger.info("================添加redis数据失败（触点属性使用）==============");
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("添加redis数据失败");
				}
			}
		}
		else{
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("传入的数据不能为空");
		}
		return uocMessage;
	}

	@Override
	public UocMessage queryForApAttr(String key) throws Exception {
		UocMessage uocMessage = new UocMessage();
		String redisKey = "apc_ap_attr_" + key;
		ApcRedisData result = S.get(ApcRedisData.class).get(redisKey);
		if(result != null){
			logger.info("================获取redis数据成功（触点属性使用）==============");
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("获取redis数据成功");
			uocMessage.addArg("apAttrInfo", result.getMap());
		}
		else{
			logger.info("================获取redis数据失败（触点属性使用）==============");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取redis数据失败");
		}
		return uocMessage;
	}

	@Override
	public UocMessage deleteForApAttr(String key) throws Exception {
		UocMessage uocMessage = new UocMessage();
		String redisKey = "apc_ap_attr_" + key;
		int result = S.get(ApcRedisData.class).remove(redisKey);
		if(result == 0){
			logger.info("================删除redis数据成功（触点属性使用）==============");
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("删除redis数据成功");
		}
		else{
			logger.info("================删除redis数据失败（触点属性使用）==============");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("删除redis数据失败");
		}
		return uocMessage;
	}

	@Override
	public UocMessage create(ApcRedisData apcRedisData) throws Exception {
		UocMessage uocMessage = new UocMessage();
		String result = (String) S.get(ApcRedisData.class).create(apcRedisData);
		if(result != null && (!"".equals(result))){
			logger.info("================存储redis数据成功（通用方法）==============");
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("存储redis数据成功");
			uocMessage.addArg("redisKey", result);
		}
		else{
			logger.info("================存储redis数据失败（通用方法）==============");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("存储redis数据失败");
		}
		return uocMessage;
	}

	@Override
	public UocMessage update(ApcRedisData apcRedisData) throws Exception {
		UocMessage uocMessage = new UocMessage();
		int result = S.get(ApcRedisData.class).update(apcRedisData);
		if(result == 0){
			logger.info("================更新redis数据成功（通用方法）==============");
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("更新redis数据成功");
		}
		else{
			logger.info("================更新redis数据失败（通用方法）==============");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("更新redis数据失败");
		}
		return uocMessage;
	}

	@Override
	public UocMessage delete(String key) throws Exception {
		UocMessage uocMessage = new UocMessage();
		int result = S.get(ApcRedisData.class).remove(key);
		if(result == 0){
			logger.info("================删除redis数据成功（通用方法）==============");
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("删除redis数据成功");
		}
		else{
			logger.info("================删除redis数据成功（通用方法）==============");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("删除redis数据失败");
		}
		return uocMessage;
	}

	@Override
	public UocMessage query(String key) throws Exception {
		UocMessage uocMessage = new UocMessage();
		ApcRedisData result = S.get(ApcRedisData.class).get(key);
		if(result != null){
			logger.info("================获取redis数据成功（通用方法）==============");
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("获取redis数据成功");
			uocMessage.addArg("redisData", result.getMap());
		}
		else{
			logger.info("================获取redis数据失败（通用方法）==============");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取redis数据失败");
		}
		return uocMessage;
	}
	
}
