package com.tydic.unicom.uoc.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.business.common.vo.InfoMenuVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleAssignVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleDepartVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleSpecVo;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskAssignRuleServDu;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskRuleDepartServDu;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskRuleSpecServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;
@Service("OperServDu")
public class OperServDuImpl implements OperServDu {

	Logger logger = Logger.getLogger(OperServDuImpl.class);
	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	private RedisServiceServ redisServiceServ;
	@Autowired
	private ProcTaskRuleSpecServDu procTaskRuleSpecServDu;
	@Autowired
	private ProcTaskRuleDepartServDu procTaskRuleDepartServDu;
	@Autowired
	private ProcTaskAssignRuleServDu procTaskAssignRuleServDu;

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage isLogin(String jsession_id) {
		UocMessage message =new UocMessage();
		/*
		 * 调接口获得 工号信息oper_info
		 */
		Map<String,Object> oper_info =new HashMap<String,Object>();
		List<InfoMenuVo> infoMenus =new ArrayList<InfoMenuVo>();
//		oper_info.put("province_code", "83");
//		oper_info.put("area_code", "831");
//		oper_info.put("depart_no", "83a0964");
//		oper_info.put("depart_name", "1020300");
//		oper_info.put("oper_no", "CF0540");
//		oper_info.put("role_id", "A002");
//		oper_info.put("channelType", "1010300");
//		oper_info.put("district", "832005");
//		oper_info.put("channel_id", "83a0964");

		/*
		 * 先从缓存查询是否有数据
		 */
		UocMessage redisMessage =new UocMessage();
		String operJoinState = "";
		try {
			redisMessage =redisServiceServ.queryDataFromRedis("oper_info");
			if("0000".equals(redisMessage.getRespCode())){
				RedisData redisData = (RedisData) redisMessage.getArgs().get("RedisDataResult");
				Map<String,Object> dataMap = redisData.getMap();
				if(dataMap.containsKey(jsession_id)){
					oper_info =(Map<String,Object>)dataMap.get(jsession_id);
					infoMenus =(List<InfoMenuVo>)dataMap.get(jsession_id+"infoMenuList");
					operJoinState = getOperJoinState(oper_info);

					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("校验成功");
					message.addArg("oper_info", oper_info);
					message.addArg("infoMenus", infoMenus);
					message.addArg("operJoinState", operJoinState);
					return message;
				}
			}

		} catch (Exception e1) {
			logger.info("---jsession_id--读取缓存异常-----");
			e1.printStackTrace();
		}


		String json_info ="{\"SERVICE_NAME\":\"queryOperInfo\",\"param\":{\"jsession_id\":\""+jsession_id+"\"}}";

		try {
			UocMessage res =abilityPlatformServDu.CallAbilityPlatform(json_info, "300", "","");
			if(res != null){
				//9、如果有调用能力平台接口，则返回的信息挂在args节点下返回
				String code = (String) res.getArgs().get("code");
				logger.info("----------code----------"+code);
				if(code != null && "200".equals(code)){
					String json_info_out = (String) res.getArgs().get("json_info");
					Map<String,Object> map =jsonToBeanServDu.jsonToMap(json_info_out);
					oper_info =(Map<String,Object>)map.get("oper_info");
					if(map.get("infoMenuList")!=null && !"".equals(map.get("infoMenuList"))){
					   infoMenus =(List<InfoMenuVo>)map.get("infoMenuList");
					}
					if(oper_info ==null){
						logger.info("----------无对应工号信息----------");
						message.setRespCode(RespCodeContents.PARAM_ERROR);
						message.setContent("无对应工号信息");
						return message;
					}
					logger.info("----------oper_info----------"+oper_info.toString());
				}else{
					logger.info("----------能力平台调用失败----------");
					message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
					return res;
				}

			}else{
				logger.info("----------能力平台调用失败----------");
				message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
				message.setContent("能力平台调用失败");
				return message;
			}

		} catch (Exception e) {
			logger.info("----------能力平台调用异常----------");
			e.printStackTrace();
			message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			message.setContent("能力平台调用异常");
			return message;
		}

		/*
		 * 将数据写入缓存，下次校验先从缓存取出
		 */
		RedisData redisPo = new RedisData();
		redisPo.setId("oper_info");
		Map<String,Object> redisCreateMap = new HashMap<String,Object>();

		redisCreateMap.put(jsession_id, oper_info);
		redisCreateMap.put(jsession_id+"infoMenuList", infoMenus);
		redisPo.setMap(redisCreateMap);
		try{
			if("0000".equals(redisMessage.getRespCode())){
				redisServiceServ.updateDataToRedis(redisPo);
			}else{
				redisServiceServ.createDataToRedis(redisPo);
			}
		} catch (Exception e) {
			logger.info("缓存异常");
			e.printStackTrace();
		}

		operJoinState = getOperJoinState(oper_info);

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("校验成功");
		message.addArg("oper_info", oper_info);
		message.addArg("infoMenus", infoMenus);
		message.addArg("operJoinState", operJoinState);
		return message;
	}

	@Override
	public String loginShareParam(Map<String, Object> oper_info,
			String jsession_id) {
		oper_info.put("jsession_id", jsession_id);
		String json_out =jsonToBeanServDu.mapToJson(oper_info).toString();
		return json_out;
	}

	// 签入签出状态
	private String getOperJoinState(Map<String, Object> oper_info) {
		String operJoinState = "";
		String depart_no = oper_info.get("depart_no").toString();
		String province_code = oper_info.get("province_code").toString();
		String area_code = oper_info.get("area_code").toString();
		String oper_no = oper_info.get("oper_no").toString();

		try {
			ProcTaskRuleDepartVo departVo = new ProcTaskRuleDepartVo();
			departVo.setDepart_no(depart_no);
			List<ProcTaskRuleDepartVo> departList = procTaskRuleDepartServDu.queryProcTaskRuleDepartByVo(departVo);
			if (departList == null) {
				operJoinState = "2";
				return operJoinState;
			}

			ProcTaskRuleAssignVo ruleVo = new ProcTaskRuleAssignVo();
			ruleVo.setProvince_code(province_code);
			ruleVo.setArea_code(area_code);
			ruleVo.setTarget_oper_no(departList.get(0).getRule_id());
			ProcTaskRuleAssignVo procTaskAssignRuleVo = procTaskAssignRuleServDu.queryProcTaskAssignRuleByVo(ruleVo);
			if (procTaskAssignRuleVo == null) {
				operJoinState = "2";
				return operJoinState;
			}

			ProcTaskRuleSpecVo specVo = new ProcTaskRuleSpecVo();
			specVo.setRule_id(departList.get(0).getRule_id());
			specVo.setTarget_oper_no("'" + oper_no + "'");
			specVo.setFlag("0");
			List<ProcTaskRuleSpecVo> procTaskRuleSpecList = procTaskRuleSpecServDu.queryProcTaskRuleSpecByWeb(specVo);
			// proc_task_rule_spec表中存在且有效 0-签入中 ，1-存在，2-不存在
			if (procTaskRuleSpecList != null && procTaskRuleSpecList.size() > 0) {
				operJoinState = "0";
			} else {
				specVo.setFlag("1");
				procTaskRuleSpecList = procTaskRuleSpecServDu.queryProcTaskRuleSpecByWeb(specVo);
				if (procTaskRuleSpecList != null && procTaskRuleSpecList.size() > 0) {
					operJoinState = "1";
				} else {
					operJoinState = "2";
				}
			}
		} catch (Exception e) {
			logger.info("查询签入签出状态异常");
			e.printStackTrace();
		}

		return operJoinState;
	}

}
