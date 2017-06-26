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
import com.tydic.unicom.service.cache.service.redis.po.OrdModStateRule;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModStateRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModStateRuleServ;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModStateRuleServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModStateRuleVo;
import com.tydic.unicom.webUtil.UocMessage;

@Service("OrdModStateRuleServDu")
public class OrdModStateRuleServDuImpl implements OrdModStateRuleServDu{

	Logger logger = Logger.getLogger(OrdModStateRuleServDuImpl.class);	

	@Autowired
	private OrdModStateRuleServ ordModStateRuleServ;
	@Autowired
	private RedisServiceServ redisServiceServ;

	@Override
	public boolean create(OrdModStateRuleVo ordModStateRuleVo) throws Exception{		
		OrdModStateRulePo ordPo = new OrdModStateRulePo();		
		BeanUtils.copyProperties(ordModStateRuleVo, ordPo);	
		boolean res = ordModStateRuleServ.create(ordPo);
		return res;
	}


	@Override
	public boolean update(OrdModStateRuleVo ordModStateRuleVo) throws Exception{
		OrdModStateRulePo ordPo = new OrdModStateRulePo();
		BeanUtils.copyProperties(ordModStateRuleVo, ordPo);	
		boolean res = ordModStateRuleServ.update(ordPo);
		return res;
	}

	@Override
	public OrdModStateRuleVo getOrdModStateRuleById(
			OrdModStateRuleVo ordModStateRuleVo) throws Exception{
		OrdModStateRulePo ordPo = new OrdModStateRulePo();		
		BeanUtils.copyProperties(ordModStateRuleVo, ordPo);	
		ordPo = ordModStateRuleServ.getModOrderStateRuleById(ordPo);
		BeanUtils.copyProperties(ordPo, ordModStateRuleVo);
		return ordModStateRuleVo;
	}

	
	@Override
	public boolean delete(OrdModStateRuleVo ordModStateRuleVo) throws Exception{
		OrdModStateRulePo ordPo = new OrdModStateRulePo();
		BeanUtils.copyProperties(ordModStateRuleVo, ordPo);	
		boolean res = ordModStateRuleServ.delete(ordPo);
		return res;
	}
	@Override
	public OrdModStateRuleVo queryOrdModStateRuleByOperCodeAndStateCode(
			OrdModStateRuleVo vo) throws Exception {
		logger.info("----------------queryOrdModStateRuleByOperCodeAndStateCode-----------------------");
		RedisData redisPo = new RedisData();
		UocMessage res =redisServiceServ.queryDataFromRedis("queryOrdModStateRuleByOperCodeAndStateCode");
		if("0000".equals(res.getRespCode())){
			Map<String,Object> redisMap = res.getArgs();
			RedisData redisDataResult = (RedisData) redisMap.get("RedisDataResult");
			Map<String,Object> dataMap = redisDataResult.getMap();
			if(dataMap.containsKey(vo.getOper_code_a()+"_"+vo.getState_code_a())){
				OrdModStateRule resPo =  (OrdModStateRule) dataMap.get(vo.getOper_code_a()+"_"+vo.getState_code_a());
				BeanUtils.copyProperties(resPo, vo);
				return vo;
			}else{
				OrdModStateRule redisOrdModStateRule = getRedis(vo.getOper_code_a(),vo.getState_code_a());
				if(redisOrdModStateRule != null){
					redisPo.setId("queryOrdModStateRuleByOperCodeAndStateCode");
					dataMap.put(vo.getOper_code_a()+"_"+vo.getState_code_a(), redisOrdModStateRule);
					redisPo.setMap(redisMap);
					UocMessage mres =redisServiceServ.updateDataToRedis(redisPo);
					if("0000".equals(mres.getRespCode())){
						BeanUtils.copyProperties(redisOrdModStateRule, vo);
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
			OrdModStateRule redisOrdModStateRule = getRedis(vo.getOper_code_a(),vo.getState_code_a());
			redisPo.setId("queryOrdModStateRuleByOperCodeAndStateCode");
			Map<String,Object> redisCreateMap = new HashMap<String,Object>();
			redisCreateMap.put(vo.getOper_code_a()+"_"+vo.getState_code_a(), redisOrdModStateRule);
			redisPo.setMap(redisCreateMap);
			UocMessage mres =redisServiceServ.createDataToRedis(redisPo);
			if("0000".equals(mres.getRespCode())){
				BeanUtils.copyProperties(redisOrdModStateRule, vo);
				return vo;
			}else{
				return null;
			}
		}

	}

	private OrdModStateRule getRedis(String oper_code,String state_code) throws Exception{
		UocMessage redisRes = new UocMessage();
		redisRes =redisServiceServ.queryDataFromRedis("ord_mod_state_rule");
		if(!"0000".equals(redisRes.getRespCode())){
			return null;
		}
		else{
			RedisData redisPo =(RedisData)redisRes.getArgs().get("RedisDataResult");
			Map<String,Object> map =redisPo.getMap();
			List<OrdModStateRule> list =new ArrayList<OrdModStateRule>();
			list =(List<OrdModStateRule>) map.get("ord_mod_state_rule");
			for(OrdModStateRule ordModStateRule:list){
				if(oper_code.equals(ordModStateRule.getOper_code_a()) && state_code.equals(ordModStateRule.getState_code_a())){
					return ordModStateRule;
				}
			}
			return null;
		}
		
	}

	@Override
	public int queryOrdModStateRuleCount(OrdModStateRuleVo modOrderStateRule)
			throws Exception {
		OrdModStateRulePo ordPo = new OrdModStateRulePo();
		BeanUtils.copyProperties(modOrderStateRule, ordPo);
		List<OrdModStateRuleVo> listVo = new ArrayList<OrdModStateRuleVo>();
		int res = ordModStateRuleServ.queryOrdModStateRuleCount(ordPo);
		return res;
	}

	@Override
	public List<OrdModStateRuleVo> queryOrdModStateRuleAll() throws Exception {
		List<OrdModStateRulePo> list = ordModStateRuleServ.queryOrdModStateRuleAll();
		if(list != null && list.size()>0){
			List<OrdModStateRuleVo> listOut = new ArrayList<OrdModStateRuleVo>();
			for(int i=0;i<list.size();i++){
				OrdModStateRuleVo ordModStateRuleVo = new OrdModStateRuleVo();
				BeanUtils.copyProperties(list.get(i), ordModStateRuleVo);
				listOut.add(ordModStateRuleVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

	@Override
	public List<OrdModStateRuleVo> getOrdModStateRuleList(
			OrdModStateRuleVo vo, int pageNo, int pageSize)
			throws Exception {
		List<OrdModStateRuleVo> listVo = new ArrayList<OrdModStateRuleVo>();
		OrdModStateRulePo ordPo = new OrdModStateRulePo();
		BeanUtils.copyProperties(vo, ordPo);		
		List<OrdModStateRulePo> listPo =ordModStateRuleServ.getModOrderStateRuleList(ordPo,pageNo,pageSize);
		if(listPo != null && listPo.size()>0){
			for(OrdModStateRulePo po : listPo){
				OrdModStateRuleVo ordVo = new OrdModStateRuleVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
		
	}

}
