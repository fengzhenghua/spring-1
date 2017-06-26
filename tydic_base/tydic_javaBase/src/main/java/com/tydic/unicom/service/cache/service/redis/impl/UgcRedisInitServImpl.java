package com.tydic.unicom.service.cache.service.redis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tydic.uda.service.S;
import com.tydic.unicom.service.cache.service.redis.interfaces.UgcRedisInitServ;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.service.cache.service.redis.po.UgcCodeList;

public class UgcRedisInitServImpl implements UgcRedisInitServ{

	private static Logger logger = Logger.getLogger(UgcRedisInitServImpl.class);

	private String[] keys = {"ugc_code_list"};

	@SuppressWarnings("unchecked")
	@Override
	public boolean ugcInitCreateDataToRedis() {
		logger.info("===========初始化ugc缓存数据到redis===========");
		List<UgcCodeList> codeListList = S.get(UgcCodeList.class).query(null);
		if (codeListList != null && codeListList.size() > 0) {
			logger.info("=======================（ugc）codeList表需要插入的数据条数为：" + codeListList.size());
			Map<String, Object> codeListMap = new HashMap<String, Object>();
			codeListMap.put("ugc_code_list", codeListList);
			for (int i = 0; i < codeListList.size(); i++) {
				codeListMap.put(codeListList.get(i).getCode_id(), codeListList.get(i));
				if (codeListMap.containsKey(codeListList.get(i).getType_code())) {
					List<UgcCodeList> codeListTypeCodeUpdateList = (List<UgcCodeList>) codeListMap.get(codeListList.get(i).getType_code());
					codeListTypeCodeUpdateList.add(codeListList.get(i));
					codeListMap.put(codeListList.get(i).getType_code(), codeListTypeCodeUpdateList);
				} else {
					List<UgcCodeList> codeListTypeCodeAddList = new ArrayList<UgcCodeList>();
					codeListTypeCodeAddList.add(codeListList.get(i));
					codeListMap.put(codeListList.get(i).getType_code(), codeListTypeCodeAddList);
				}

				if (codeListMap.containsKey(codeListList.get(i).getType_code() + "_" + codeListList.get(i).getParent_code_id())) {

					List<UgcCodeList> codeListTypeCodeUpdateList = (List<UgcCodeList>) codeListMap.get(codeListList.get(i).getType_code() + "_"
							+ codeListList.get(i).getParent_code_id());
					codeListTypeCodeUpdateList.add(codeListList.get(i));
					codeListMap.put(codeListList.get(i).getType_code() + "_" + codeListList.get(i).getParent_code_id(), codeListTypeCodeUpdateList);
				} else {
					List<UgcCodeList> codeListTypeCodeAddList = new ArrayList<UgcCodeList>();
					codeListTypeCodeAddList.add(codeListList.get(i));
					codeListMap.put(codeListList.get(i).getType_code() + "_" + codeListList.get(i).getParent_code_id(), codeListTypeCodeAddList);
				}
			}
			writeDataToRedis(codeListMap, "ugc_code_list");
		} else {
			logger.info("=================没有查询到（ugc）codelist中的数据============");
		}

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
