package com.tydic.unicom.uoc.service.mod.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.OrdModOperSplitRule;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModOperSplitRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModOperSplitRuleServ;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModOperSplitRuleServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModOperSplitRuleVo;
import com.tydic.unicom.webUtil.UocMessage;
@Service("OrdModOperSplitRuleServDu")
public class OrdModOperSplitRuleServDuImpl implements OrdModOperSplitRuleServDu {

	Logger logger = Logger.getLogger(OrdModOperSplitRuleServDuImpl.class);
	@Autowired
	private OrdModOperSplitRuleServ ordModOperSplitRuleServ;
	@Autowired
	private RedisServiceServ redisServiceServ;
	
	@Override
	public List<OrdModOperSplitRuleVo> queryOrdModOperSplitRuleByOperCode(
			OrdModOperSplitRuleVo vo) throws Exception {
		logger.info("----------------queryOrdModOperSplitRuleByOperCode-----------------------");
		RedisData redisPo = new RedisData();
		List<OrdModOperSplitRuleVo> listVo =new ArrayList<OrdModOperSplitRuleVo>();
		UocMessage res =redisServiceServ.queryDataFromRedis("queryOrdModOperSplitRuleByOperCode");
		if("0000".equals(res.getRespCode())){
			Map<String,Object> redisMap = res.getArgs();
			RedisData redisDataResult = (RedisData) redisMap.get("RedisDataResult");
			Map<String,Object> dataMap = redisDataResult.getMap();
			if(dataMap.containsKey(vo.getSource_oper_code())){
				List<OrdModOperSplitRule> redisList = new ArrayList<OrdModOperSplitRule>();
				redisList = (List<OrdModOperSplitRule>) dataMap.get(vo.getSource_oper_code());
				if(redisList!=null && redisList.size()>0){
					for(OrdModOperSplitRule omosr:redisList){
						OrdModOperSplitRuleVo omosrVo =new OrdModOperSplitRuleVo();
						BeanUtils.copyProperties(omosr, omosrVo);
						listVo.add(omosrVo);
					}
				}
				return listVo;
			}
			else{
				List<OrdModOperSplitRule> redisupdateList = getRedisList(vo.getSource_oper_code());
				if(redisupdateList != null && redisupdateList.size()>0){
					redisPo.setId("queryOrdModOperSplitRuleByOperCode");
					dataMap.put(vo.getSource_oper_code(), redisupdateList);
					redisPo.setMap(redisMap);
					UocMessage mres =redisServiceServ.updateDataToRedis(redisPo);
					if("0000".equals(mres.getRespCode())){
						if(redisupdateList!=null && redisupdateList.size()>0){
							for(OrdModOperSplitRule omosr:redisupdateList){
								OrdModOperSplitRuleVo omosrVo =new OrdModOperSplitRuleVo();
								BeanUtils.copyProperties(omosr, omosrVo);
								listVo.add(omosrVo);
							}
						}
						return listVo;
					}else{
						return null;
					}
				}
				else{
					return null;
				}
			}
		}else{
			List<OrdModOperSplitRule> redisCreateList = getRedisList(vo.getSource_oper_code());
			redisPo.setId("queryOrdModOperSplitRuleByOperCode");
			Map<String,Object> redisCreateMap = new HashMap<String,Object>();
			redisCreateMap.put(vo.getSource_oper_code(), redisCreateList);
			redisPo.setMap(redisCreateMap);
			UocMessage mres =redisServiceServ.createDataToRedis(redisPo);
			if("0000".equals(mres.getRespCode())){
				if(redisCreateList!=null && redisCreateList.size()>0){
					for(OrdModOperSplitRule omosr:redisCreateList){
						OrdModOperSplitRuleVo omosrVo =new OrdModOperSplitRuleVo();
						BeanUtils.copyProperties(omosr, omosrVo);
						listVo.add(omosrVo);
					}
				}
				return listVo;
			}else{
				return null;
			}
		}
	}
	
	private List<OrdModOperSplitRule> getRedisList(String oper_code) throws Exception{
		UocMessage redisRes = new UocMessage();
		redisRes =redisServiceServ.queryDataFromRedis("ord_oper_split_rule");
		if(!"0000".equals(redisRes.getRespCode())){
			return null;
		}
		else{
			RedisData redisPo =(RedisData)redisRes.getArgs().get("RedisDataResult");
			Map<String,Object> map =redisPo.getMap();
			List<OrdModOperSplitRule> list =new ArrayList<OrdModOperSplitRule>();
			list =(List<OrdModOperSplitRule>) map.get("ord_oper_split_rule");
			List<OrdModOperSplitRule> resultList = new ArrayList<OrdModOperSplitRule>();
			for(OrdModOperSplitRule ordModOperSplitRule:list){
				if(oper_code.endsWith(ordModOperSplitRule.getSource_oper_code())){
					resultList.add(ordModOperSplitRule);
				}
			}
			return resultList;
		}
		
	}

	@Override
	public List<OrdModOperSplitRuleVo> queryOrdModOperSplitRuleAll() throws Exception {
		List<OrdModOperSplitRulePo> list = ordModOperSplitRuleServ.queryOrdModOperSplitRuleAll();
		if(list != null && list.size()>0){
			List<OrdModOperSplitRuleVo> listOut = new ArrayList<OrdModOperSplitRuleVo>();
			for(int i=0;i<list.size();i++){
				OrdModOperSplitRuleVo ordModOperSplitRuleVo = new OrdModOperSplitRuleVo();
				BeanUtils.copyProperties(list.get(i), ordModOperSplitRuleVo);
				listOut.add(ordModOperSplitRuleVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}
