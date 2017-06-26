package com.tydic.unicom.uoc.business.common.impl;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.uoc.business.common.interfaces.GetDepartAndOperInfoServDu;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class GetDepartAndOperInfoServDuImpl implements GetDepartAndOperInfoServDu{

	private static Logger logger = Logger.getLogger(GetDepartAndOperInfoServDuImpl.class);

	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;

	@Autowired
	private OperServDu operServDu;

	@Override
	public UocMessage getDepartInfo(String jsessionId, String departNo,String departName) throws Exception {
		logger.info("=================获取可选部门（从云销售获取）======================");
		UocMessage uocMessage = new UocMessage();
		if(StringUtils.isEmpty(jsessionId)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		//获取登陆信息服务，校验是否登陆
		UocMessage uocMessageLogin = operServDu.isLogin(jsessionId);
		if(!"0000".equals(uocMessageLogin.getRespCode())){
			return uocMessageLogin;
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> oper_info =(Map<String, Object>) uocMessageLogin.getArgs().get("oper_info");
		if(StringUtils.isEmpty(oper_info.get("oper_no").toString())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取当前部门号失败");
			return uocMessage;
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "getDepartInfo");
		Map<String,String> map = new HashMap<String,String>();
		map.put("currDepartNo", oper_info.get("depart_no").toString());

		if(!departNo.isEmpty()){
			map.put("departNo", departNo);
		} else {
			map.put("departNo", "");
		}
		if(!departName.isEmpty()){
			map.put("departName", departName);
		} else {
			map.put("departName", "");
		}
		jsonObj.put("param", map);
		//调用能力平台接口
		uocMessage = abilityPlatformServDu.CallAbilityPlatform(jsonObj.toString(), "300", "", "");
		return uocMessage;
	}

	@Override
	public UocMessage getOperInfo(String jsessionId, String operNo,String operName) throws Exception {
		logger.info("=================获取可选工号（从云销售获取）====================");
		UocMessage uocMessage = new UocMessage();
		if(StringUtils.isEmpty(jsessionId)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		//获取登陆信息服务，校验是否登陆
		UocMessage uocMessageLogin = operServDu.isLogin(jsessionId);
		if(!"0000".equals(uocMessageLogin.getRespCode())){
			return uocMessageLogin;
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> oper_info =(Map<String, Object>) uocMessageLogin.getArgs().get("oper_info");
		if(StringUtils.isEmpty(oper_info.get("oper_no").toString())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取当前部门号失败");
			return uocMessage;
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "getOperInfo");
		Map<String,String> map = new HashMap<String,String>();
		map.put("currDepartNo", oper_info.get("depart_no").toString());

		if(!operNo.isEmpty()){
			map.put("operNo", operNo);
		} else {
			map.put("operNo", "");
		}

		if(!operName.isEmpty()){
			map.put("operName", operName);
		} else {
			map.put("operName", "");
		}
		jsonObj.put("param", map);
		//调用能力平台接口
		uocMessage = abilityPlatformServDu.CallAbilityPlatform(jsonObj.toString(), "300", "", "");
		return uocMessage;
	}

	@Override
	public UocMessage getDepartInfoByOperNo(String operNo) throws Exception {
		logger.info("=================获取工号对应部门信息（从云销售获取）====================");
		UocMessage uocMessage = new UocMessage();
		if(StringUtils.isEmpty(operNo)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("工号不能为空");
			return uocMessage;
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "getDepartInfoByOperNo");
		Map<String,String> map = new HashMap<String,String>();
		map.put("operNo", operNo);
		jsonObj.put("param", map);
		//调用能力平台接口
		uocMessage = abilityPlatformServDu.CallAbilityPlatform(jsonObj.toString(), "300", "", "");
		return uocMessage;
	}
	/**
	 * UOC0078 	获取可选营业厅
	 * @param jsession_id
	 * @param region_id
	 * @param dept_no
	 * @param dept_name
	 * @return
	 * @throws Exception
	 */
	@Override
	public UocMessage getShellingInfo(String jsession_id, String region_id,
			String dept_no, String dept_name) throws Exception {
		UocMessage uocMessage = new UocMessage();
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "getBusinessHallInfo");
		Map<String,String> map = new HashMap<String,String>();
		map.put("jsessionId", jsession_id);
		map.put("areaId", region_id);
		map.put("departNo", dept_no);
		map.put("departName", dept_name);
		jsonObj.put("param", map);
		uocMessage =abilityPlatformServDu.CallAbilityPlatform(jsonObj.toString(), "300", "", "");
		
		
		return uocMessage;
	}

	@Override
	public UocMessage getDepartInfoNew(String jsession_id, String region_id,
			String depart_no, String depart_name, String query_type)
			throws Exception {
		logger.info("=================获取可选部门（从云销售获取）======================");
		UocMessage uocMessage = new UocMessage();
		if(StringUtils.isEmpty(jsession_id)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		//获取登陆信息服务，校验是否登陆
		UocMessage uocMessageLogin = operServDu.isLogin(jsession_id);
		if(!"0000".equals(uocMessageLogin.getRespCode())){
			return uocMessageLogin;
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> oper_info =(Map<String, Object>) uocMessageLogin.getArgs().get("oper_info");
		if(StringUtils.isEmpty(oper_info.get("oper_no").toString())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取当前部门号失败");
			return uocMessage;
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "queryDepartInfo");
		Map<String,String> map = new HashMap<String,String>();
		map.put("jsession_id", jsession_id);

		if(!region_id.isEmpty()){
			map.put("region_id", region_id);
		} else {
			map.put("region_id", "");
		}
		if(!depart_no.isEmpty()){
			map.put("depart_no", depart_no);
		} else {
			map.put("depart_no", "");
		}
		if(!depart_name.isEmpty()){
			map.put("depart_name", depart_name);
		} else {
			map.put("depart_name", "");
		}
		if(!query_type.isEmpty()){
			map.put("query_type", query_type);
		} else {
			map.put("query_type", "");
		}
		jsonObj.put("param", map);
		//调用能力平台接口
		uocMessage = abilityPlatformServDu.CallAbilityPlatform(jsonObj.toString(), "300", "", "");
		return uocMessage;
	}

}
