package com.tydic.unicom.uoc.service.mod.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.ProcModTacheService;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.ProcModTacheServicePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcModTacheServiceServ;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServiceServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheServiceVo;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ProcModTacheServiceServDu")
public class ProcModTacheServiceServDuImpl implements ProcModTacheServiceServDu{

	Logger logger = Logger.getLogger(ProcModTacheServiceServDuImpl.class);
	
	@Autowired
	private ProcModTacheServiceServ procModTacheServiceServ;
	@Autowired
	private RedisServiceServ redisServiceServ;
	@Override
	public boolean create(ProcModTacheServiceVo procModTacheServiceVo)
			throws Exception {
		ProcModTacheServicePo po = new ProcModTacheServicePo();
		BeanUtils.copyProperties(procModTacheServiceVo, po);
		boolean res = procModTacheServiceServ.create(po);
		return res;
	}

	@Override
	public boolean update(ProcModTacheServiceVo procModTacheServiceVo)
			throws Exception {
		ProcModTacheServicePo po = new ProcModTacheServicePo();
		BeanUtils.copyProperties(procModTacheServiceVo, po);
		boolean res = procModTacheServiceServ.update(po);
		return res;
	}

	@Override
	public boolean delete(ProcModTacheServiceVo procModTacheServiceVo)
			throws Exception {
		ProcModTacheServicePo po = new ProcModTacheServicePo();
		BeanUtils.copyProperties(procModTacheServiceVo, po);
		boolean res = procModTacheServiceServ.delete(po);
		return res;
	}


	
	@Override
	public int queryProcModTacheServiceListCount(
			ProcModTacheServiceVo procModTacheServiceVo) throws Exception {
		ProcModTacheServicePo po = new ProcModTacheServicePo();
		BeanUtils.copyProperties(procModTacheServiceVo, po);
		int res = procModTacheServiceServ.queryProcModTacheServiceListCount(po);
		return res;
	}


	@Override
	public List<ProcModTacheServiceVo> queryProcModTacheServiceByTacheCodeAndProvinceAndAreaAndOperCodeFromRedis(ProcModTacheServiceVo procModTacheServiceVo) throws Exception {
		logger.info("=================根据环节编码，地域和省份向redis查询ProcModTacheService================");
		String key = procModTacheServiceVo.getTache_code()+"_"+procModTacheServiceVo.getProvince_code()+"_"+procModTacheServiceVo.getArea_code()+"_"+procModTacheServiceVo.getOper_code();
		String keyOperCodeDefine = procModTacheServiceVo.getTache_code()+"_"+procModTacheServiceVo.getProvince_code()+"_"+procModTacheServiceVo.getArea_code()+"_*";
		String keyAreaDefine = procModTacheServiceVo.getTache_code()+"_"+procModTacheServiceVo.getProvince_code()+"_*_"+procModTacheServiceVo.getOper_code();
		String keyAreaOperCodeDefine = procModTacheServiceVo.getTache_code()+"_"+procModTacheServiceVo.getProvince_code()+"_*_*";
		String getValueKey = "";
		UocMessage queryRedisMsg = redisServiceServ.queryDataFromRedis("proc_tache_service");
		if(!"0000".equals(queryRedisMsg.getRespCode())){
			logger.info("=================查询redis错误================");
			return null;
		}
		else{
			Map<String,Object> redismap = queryRedisMsg.getArgs();
			RedisData redisDataResult = (RedisData) redismap.get("RedisDataResult");
			Map<String,Object> dataMap = redisDataResult.getMap();
			if(dataMap.containsKey(key)){
				getValueKey = key;
			}
			else if(dataMap.containsKey(keyOperCodeDefine)){
				getValueKey = keyOperCodeDefine;
			}
			else if(dataMap.containsKey(keyAreaDefine)){
				getValueKey = keyAreaDefine;
			}
			else if(dataMap.containsKey(keyAreaOperCodeDefine)){
				getValueKey = keyAreaOperCodeDefine;
			}
			else{
				getValueKey = "";
			}
			
			if("".equals(getValueKey)){
				logger.info("=================查询redis（proc_mod_tache_service表），不存在对应的数据================");
				return null;
			}
			else{
				logger.info("=================查询redis（proc_mod_tache_service表），存在对应的数据================");
				List<ProcModTacheService> procModTacheServiceDataList = (List<ProcModTacheService>) dataMap.get(getValueKey);
				List<ProcModTacheServiceVo> procModTacheServiceVoDataList = new ArrayList<ProcModTacheServiceVo>();
				if(procModTacheServiceDataList != null && procModTacheServiceDataList.size()>0){
					for(int i=0;i<procModTacheServiceDataList.size();i++){
						ProcModTacheServiceVo procModTacheServiceVoData = new ProcModTacheServiceVo();
						BeanUtils.copyProperties(procModTacheServiceDataList.get(i), procModTacheServiceVoData);
						procModTacheServiceVoDataList.add(procModTacheServiceVoData);
					}
					return procModTacheServiceVoDataList;
				}
				else{
					return null;
				}
			}
		}
	}

	@Override
	public List<ProcModTacheServiceVo> queryProcModTacheServiceAll() throws Exception {
		List<ProcModTacheServicePo> list = procModTacheServiceServ.queryProcModTacheServiceAll();
		if(list != null && list.size()>0){
			List<ProcModTacheServiceVo> listOut = new ArrayList<ProcModTacheServiceVo>();
			for(int i=0;i<list.size();i++){
				ProcModTacheServiceVo procModTacheServiceVo = new ProcModTacheServiceVo();
				BeanUtils.copyProperties(list.get(i), procModTacheServiceVo);
				listOut.add(procModTacheServiceVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

	@Override
	public List<ProcModTacheServiceVo> queryProcModTacheServiceList(
			ProcModTacheServiceVo procModTacheServiceVo, int pageNo,
			int pageSize) throws Exception {
		ProcModTacheServicePo po = new ProcModTacheServicePo();
		BeanUtils.copyProperties(procModTacheServiceVo, po);
		List<ProcModTacheServiceVo> listVo = new ArrayList<ProcModTacheServiceVo>();
		List<ProcModTacheServicePo> listPo = procModTacheServiceServ.queryProcModTacheServiceList(po, pageNo, pageSize);
		if(listPo != null && listPo.size()>0){
			for(ProcModTacheServicePo poTemp : listPo){
				ProcModTacheServiceVo ordVo = new ProcModTacheServiceVo();
				BeanUtils.copyProperties(poTemp, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}


}
