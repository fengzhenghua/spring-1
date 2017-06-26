package com.tydic.unicom.ugc.service.common.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.ugc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.ugc.service.common.interfaces.OperServDu;
import com.tydic.unicom.ugc.service.common.interfaces.UgcAbilityPlatformServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("OperServDu")
public class OperServDuImpl implements OperServDu {

	Logger logger = Logger.getLogger(OperServDuImpl.class);
	@Autowired
	private UgcAbilityPlatformServDu abilityPlatformServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	@Override
	public UocMessage isLogin(String jsession_id) {
		/*
		 * 调接口获得 工号信息oper_info
		 */
		// Map<String, Object> oper_info = new HashMap<String, Object>();
		// oper_info.put("province_code", "83");
		// oper_info.put("area_code", "831");
		// oper_info.put("depart_no", "83a0964");
		// oper_info.put("depart_name", "1020300");
		// oper_info.put("oper_no", "CF0540");
		// oper_info.put("role_id", "A002");
		// oper_info.put("channelType", "1010300");
		// oper_info.put("district", "832005");
		// oper_info.put("channel_id", "83a0964");

		logger.info("INFO [校验是否登录]==========>请求参数：" + jsession_id);

		UocMessage respUocMessage = new UocMessage();

		// 参数校验
		if (StringUtils.isEmpty(jsession_id)) {
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("请求参数中jsession_id不能为空");
			return respUocMessage;
		}

		// 封装请求参数
		JSONObject requestJson = new JSONObject();
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("jsession_id", jsession_id);
		requestJson.put("param", requestMap);
		requestJson.put("SERVICE_NAME", "getBaseOperInfo");

		try {
			// 通过能力平台服务调UAC认证中心服务-UAC0005-获取登陆信息
			respUocMessage = abilityPlatformServDu.CallUgcAbilityPlatform(requestJson.toString(), "", "UAC");
			String code = (String) respUocMessage.getArgs().get("code");
			if (code != null && "200".equals(code)) {
				String json_info_out = (String) respUocMessage.getArgs().get("json_info");
				Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info_out);
				@SuppressWarnings("unchecked")
				Map<String, Object> oper_info = (Map<String, Object>) map.get("oper_info");

				if (oper_info == null) {
					logger.info("----------无对应工号信息----------");
					respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
					respUocMessage.setContent("无对应工号信息");
					return respUocMessage;
				} else {
					respUocMessage.addArg("oper_info", oper_info);
				}
				logger.info("----------oper_info----------" + oper_info.toString());
			} else {
				logger.info("----------能力平台调用失败----------");
				respUocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
				return respUocMessage;
			}
		} catch (Exception e) {
			logger.warn("WARN [UGC]==========>通过能力平台调认证中心校验登录接口异常：");
			e.printStackTrace();
			respUocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			respUocMessage.setContent("通过能力平台调认证中心校验登录接口异常");
		}

		logger.info("INFO [校验是否登录]==========>响应参数：" + respUocMessage.toString());

		return respUocMessage;
	}


}
