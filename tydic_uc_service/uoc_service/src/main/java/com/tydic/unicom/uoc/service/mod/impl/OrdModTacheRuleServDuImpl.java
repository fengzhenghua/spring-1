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
import com.tydic.unicom.service.cache.service.redis.po.OrdModTacheRule;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModTacheRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModTacheRuleServ;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModTacheRuleServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModTacheRuleVo;
import com.tydic.unicom.webUtil.UocMessage;

@Service("OrdModTacheRuleServDu")
public class OrdModTacheRuleServDuImpl implements OrdModTacheRuleServDu{

	Logger logger = Logger.getLogger(OrdModStateRuleServDuImpl.class);	
	@Autowired
	private OrdModTacheRuleServ ordModTacheRuleServ;
	@Autowired
	private RedisServiceServ redisServiceServ;

	
	@Override
	public boolean create(OrdModTacheRuleVo ordModTacheRuleVo) throws Exception{
		OrdModTacheRulePo ordPo = new OrdModTacheRulePo();
		BeanUtils.copyProperties(ordModTacheRuleVo, ordPo);
		boolean res = ordModTacheRuleServ.create(ordPo);
		return res;
	}
	@Override
	public boolean update(OrdModTacheRuleVo ordModTacheRuleVo) throws Exception{
		OrdModTacheRulePo ordPo = new OrdModTacheRulePo();
		BeanUtils.copyProperties(ordModTacheRuleVo, ordPo);
		boolean res = ordModTacheRuleServ.update(ordPo);
		return res;
	}
	@Override
	public boolean delete(OrdModTacheRuleVo ordModTacheRuleVo) throws Exception{
		OrdModTacheRulePo ordPo = new OrdModTacheRulePo();
		BeanUtils.copyProperties(ordModTacheRuleVo, ordPo);
		boolean res = ordModTacheRuleServ.delete(ordPo);
		return res;
	}
	@Override
	public OrdModTacheRuleVo queryOrdModTacheRuleByOperCodeAndTacheCode(
			OrdModTacheRuleVo vo) throws Exception {
		logger.info("----------------queryOrdModTacheRuleByOperCodeAndTacheCode-----------------------");
		RedisData redisPo = new RedisData();
		UocMessage res =redisServiceServ.queryDataFromRedis("queryOrdModTacheRuleByOperCodeAndTacheCode");
		if("0000".equals(res.getRespCode())){
			Map<String,Object> redisMap = res.getArgs();
			RedisData redisDataResult = (RedisData) redisMap.get("RedisDataResult");
			Map<String,Object> dataMap = redisDataResult.getMap();
			if(dataMap.containsKey(vo.getOper_code_a()+"_"+vo.getTache_code_a())){
				OrdModTacheRule resPo =  (OrdModTacheRule) dataMap.get(vo.getOper_code_a()+"_"+vo.getTache_code_a());
				BeanUtils.copyProperties(resPo, vo);
				return vo;
			}else{
				OrdModTacheRule redisOrdModTacheRule = getRedis(vo.getOper_code_a(),vo.getTache_code_a());
				if(redisOrdModTacheRule != null){
					redisPo.setId("queryOrdModTacheRuleByOperCodeAndTacheCode");
					dataMap.put(vo.getOper_code_a()+"_"+vo.getTache_code_a(), redisOrdModTacheRule);
					redisPo.setMap(dataMap);
					UocMessage mres =redisServiceServ.updateDataToRedis(redisPo);
					if("0000".equals(mres.getRespCode())){
						BeanUtils.copyProperties(redisOrdModTacheRule, vo);
						return vo;
					}else{
						return null;
					}
				}
				else{
					return null;
				}
			}
		}else{
			OrdModTacheRule redisOrdModTacheRule = getRedis(vo.getOper_code_a(),vo.getTache_code_a());
			redisPo.setId("queryOrdModTacheRuleByOperCodeAndTacheCode");
			Map<String,Object> redisCreateMap = new HashMap<String,Object>();
			redisCreateMap.put(vo.getOper_code_a()+"_"+vo.getTache_code_a(), redisOrdModTacheRule);
			redisPo.setMap(redisCreateMap);
			UocMessage mres =redisServiceServ.createDataToRedis(redisPo);
			if("0000".equals(mres.getRespCode())){
				BeanUtils.copyProperties(redisOrdModTacheRule, vo);
				return vo;
			}else{
				return null;
			}
		}
	}
	
	private OrdModTacheRule getRedis(String oper_code,String tache_code) throws Exception{
		UocMessage redisRes = new UocMessage();
		redisRes =redisServiceServ.queryDataFromRedis("ord_mod_tache_rule");
		if(!"0000".equals(redisRes.getRespCode())){
			return null;
		}
		else{
			RedisData redisPo =(RedisData)redisRes.getArgs().get("RedisDataResult");
			Map<String,Object> map =redisPo.getMap();
			List<OrdModTacheRule> list =new ArrayList<OrdModTacheRule>();
			list =(List<OrdModTacheRule>) map.get("ord_mod_tache_rule");
			for(OrdModTacheRule ordModTacheRule:list){
				if(oper_code.equals(ordModTacheRule.getOper_code_a()) && tache_code.equals(ordModTacheRule.getTache_code_a())){
					return ordModTacheRule;
				}
			}
			return null;
		}
		
	}
	
	@Override
	public int queryOrdModTacheRuleListConut(OrdModTacheRuleVo vo)
			throws Exception {
		OrdModTacheRulePo ordModTacheRulePo =new OrdModTacheRulePo();
		BeanUtils.copyProperties(vo,ordModTacheRulePo);
		int res= ordModTacheRuleServ.queryOrdModTacheRuleListConut(ordModTacheRulePo);
		return res;
	}
	
	@Override
	public List<OrdModTacheRuleVo> queryOrdModTacheRuleAll() throws Exception {
		List<OrdModTacheRulePo> list = ordModTacheRuleServ.queryOrdModTacheRuleAll();
		if(list != null && list.size()>0){
			List<OrdModTacheRuleVo> listOut = new ArrayList<OrdModTacheRuleVo>();
			for(int i=0;i<list.size();i++){
				OrdModTacheRuleVo ordModTacheRuleVo = new OrdModTacheRuleVo();
				BeanUtils.copyProperties(list.get(i), ordModTacheRuleVo);
				listOut.add(ordModTacheRuleVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

	@Override
	public List<OrdModTacheRuleVo> queryOrdModTacheRuleList(
			OrdModTacheRuleVo vo, int pageNo, int pageSize) throws Exception {
		OrdModTacheRulePo ordModTacheRulePo =new OrdModTacheRulePo();			
		BeanUtils.copyProperties(vo,ordModTacheRulePo);
		List<OrdModTacheRuleVo> listVo = new ArrayList<OrdModTacheRuleVo>();
		List<OrdModTacheRulePo> listPo= ordModTacheRuleServ.queryOrdModTacheRuleList(ordModTacheRulePo,pageNo,pageSize);
		if(listPo != null && listPo.size() > 0){
			for(OrdModTacheRulePo po : listPo){
				OrdModTacheRuleVo voTemp = new OrdModTacheRuleVo();
				BeanUtils.copyProperties(po, voTemp);
				listVo.add(voTemp);
			}
			return listVo;
		}else{
			return null;
		}			
	}

}
