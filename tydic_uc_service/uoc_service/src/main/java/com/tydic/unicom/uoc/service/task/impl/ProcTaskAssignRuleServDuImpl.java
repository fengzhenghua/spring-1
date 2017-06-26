package com.tydic.unicom.uoc.service.task.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.ProcTaskAssignRule;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.ProcTaskAssignRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcTaskAssignRuleServ;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleAssignVo;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskAssignRuleServDu;
import com.tydic.unicom.uoc.service.task.vo.ProcTaskAssignRuleVo;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ProcTaskAssignRuleServDu")
public class ProcTaskAssignRuleServDuImpl implements ProcTaskAssignRuleServDu {
	Logger logger = Logger.getLogger(ProcTaskAssignRuleServDuImpl.class);

	@Autowired
	RedisServiceServ redisServiceServ;

	@Autowired
	private ProcTaskAssignRuleServ procTaskAssignRuleServ;

	@Override
	public ProcTaskAssignRuleVo queryTaskAssignRuleByTaskInstFromRedis(ProcInstTaskInstVo vo) throws Exception {
		logger.info("=================向redis查询proc_task_assign_rule================");
		String acceptOperNo = "";
		String acceptDepartNo = "";
		String prodCode = "";
		//参数处理
		if(StringUtils.isEmpty(vo.getAccept_oper_no())){
			acceptOperNo = "*";
		}
		else{
			acceptOperNo = vo.getAccept_oper_no();
		}

		if(StringUtils.isEmpty(vo.getAccept_depart_no())){
			acceptDepartNo = "*";
		}
		else{
			acceptDepartNo = vo.getAccept_depart_no();
		}

		if(StringUtils.isEmpty(vo.getProd_code())){
			prodCode = "*";
		}
		else{
			prodCode = vo.getProd_code();
		}
		//拼接redis查询需要的key
		String mustKey = vo.getProvince_code() + "_" + vo.getArea_code() + "_" + vo.getTache_code() + "_" + vo.getOper_code();

		String queryKey8 = mustKey + "_*_*_*";
		String queryKey7 = mustKey + "_*_*" + "_" + prodCode;
		String queryKey6 = mustKey + "_*_" + acceptDepartNo + "_*";
		String queryKey5 = mustKey + "_" + acceptOperNo + "_*_*";
		String queryKey4 = mustKey + "_*_" + acceptDepartNo + "_" + prodCode;
		String queryKey3 = mustKey + "_" + acceptOperNo + "_*_" + prodCode;
		String queryKey2 = mustKey + "_" + acceptOperNo + "_" + acceptDepartNo + "_*";
		String queryKey1 = mustKey + "_" + acceptOperNo + "_" + acceptDepartNo + "_" + prodCode;
		String getValueKey = "";

		UocMessage queryRedisMsg = redisServiceServ.queryDataFromRedis("proc_task_assign_rule");
		if (!"0000".equals(queryRedisMsg.getRespCode())) {
			logger.info("=================查询redis错误================");
			return null;
		} else {
			Map<String, Object> redismap = queryRedisMsg.getArgs();
			RedisData redisDataResult = (RedisData) redismap.get("RedisDataResult");
			Map<String, Object> dataMap = redisDataResult.getMap();
			if (dataMap.containsKey(queryKey1)) {
				getValueKey = queryKey1;
			} else if (dataMap.containsKey(queryKey2)) {
				getValueKey = queryKey2;
			} else if (dataMap.containsKey(queryKey3)) {
				getValueKey = queryKey3;
			} else if (dataMap.containsKey(queryKey4)) {
				getValueKey = queryKey4;
			} else if (dataMap.containsKey(queryKey5)) {
				getValueKey = queryKey5;
			}else if (dataMap.containsKey(queryKey6)) {
				getValueKey = queryKey6;
			}else if (dataMap.containsKey(queryKey7)) {
				getValueKey = queryKey7;
			}else if (dataMap.containsKey(queryKey8)) {
				getValueKey = queryKey8;
			}else {
				getValueKey = "";
			}

			if ("".equals(getValueKey)) {
				logger.info("=================查询redis（proc_task_assign_rule表），不存在对应的数据================");
				return null;
			} else {
				logger.info("=================查询redis（proc_task_assign_rule表），存在对应的数据================");
				ProcTaskAssignRule procTaskAssignRuleData = (ProcTaskAssignRule) dataMap.get(getValueKey);
				if (procTaskAssignRuleData != null) {
					ProcTaskAssignRuleVo procTaskAssignRuleVoData = new ProcTaskAssignRuleVo();
					BeanUtils.copyProperties(procTaskAssignRuleData, procTaskAssignRuleVoData);
					return procTaskAssignRuleVoData;
				} else {
					return null;
				}
			}
		}

	}

	@Override
	public boolean create(ProcTaskRuleAssignVo vo) throws Exception {
		ProcTaskAssignRulePo po=new ProcTaskAssignRulePo();
		BeanUtils.copyProperties(vo, po);
		boolean res=procTaskAssignRuleServ.create(po);
		return res;
	}

	@Override
	public boolean delete(ProcTaskRuleAssignVo vo) throws Exception {
		ProcTaskAssignRulePo po=new ProcTaskAssignRulePo();
		BeanUtils.copyProperties(vo, po);
		boolean res=procTaskAssignRuleServ.delete(po);
		return res;
	}

	@Override
	public boolean update(ProcTaskRuleAssignVo vo) throws Exception {
		ProcTaskAssignRulePo po=new ProcTaskAssignRulePo();
		BeanUtils.copyProperties(vo, po);
		boolean res=procTaskAssignRuleServ.update(po);
		return res;
	}

	@Override
	public List<ProcTaskRuleAssignVo> queryProcTaskAssignRuleByPage(
			ProcTaskRuleAssignVo vo, int pageNo, int pageSize) throws Exception {
		ProcTaskAssignRulePo po = new ProcTaskAssignRulePo();
		BeanUtils.copyProperties(vo, po);
		List<ProcTaskAssignRulePo> listPo = procTaskAssignRuleServ.queryProcTaskAssignRuleByPage(po, pageNo, pageSize);
		List<ProcTaskRuleAssignVo> listVo = new ArrayList<ProcTaskRuleAssignVo>();
		if(listPo != null && listPo.size()>0){
			for(ProcTaskAssignRulePo rulePo : listPo){
				ProcTaskRuleAssignVo ruleVo = new ProcTaskRuleAssignVo();
				BeanUtils.copyProperties(rulePo, ruleVo);
				listVo.add(ruleVo);
			}
			return listVo;
		}
		return null;
	}

	@Override
	public ProcTaskRuleAssignVo queryProcTaskAssignRuleById(
			ProcTaskRuleAssignVo vo) throws Exception {
		ProcTaskAssignRulePo po = new ProcTaskAssignRulePo();
		BeanUtils.copyProperties(vo, po);
		ProcTaskAssignRulePo rulePo=procTaskAssignRuleServ.queryProcTaskAssignRuleById(po);
		ProcTaskRuleAssignVo ruleVo=new ProcTaskRuleAssignVo();
		if (rulePo != null) {
			BeanUtils.copyProperties(rulePo, ruleVo);
		} else {
			return null;
		}
		return ruleVo;
	}

	@Override
	public int queryProcTaskAssignRuleCount(ProcTaskRuleAssignVo vo)
			throws Exception {
		ProcTaskAssignRulePo po = new ProcTaskAssignRulePo();
		BeanUtils.copyProperties(vo, po);
		int res=procTaskAssignRuleServ.queryProcTaskAssignRuleCount(po);
		return res;
	}

	@Override
	public ProcTaskRuleAssignVo queryProcTaskAssignRuleByVo(ProcTaskRuleAssignVo vo) throws Exception {
		ProcTaskAssignRulePo po = new ProcTaskAssignRulePo();
		BeanUtils.copyProperties(vo, po);
		ProcTaskAssignRulePo rulePo = procTaskAssignRuleServ.queryByProcTaskAssignRulePo(po);
		ProcTaskRuleAssignVo ruleVo = new ProcTaskRuleAssignVo();
		if(rulePo!=null){
			BeanUtils.copyProperties(rulePo, ruleVo);
		} else {
			return null;
		}
		return ruleVo;
	}

}
