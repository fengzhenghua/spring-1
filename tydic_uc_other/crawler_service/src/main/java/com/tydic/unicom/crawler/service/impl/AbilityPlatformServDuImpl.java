package com.tydic.unicom.crawler.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.open.rest.api.sdk.client.OrsException;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.common.ProParamVo;
@Service("AbilityPlatformServDu")
public class AbilityPlatformServDuImpl {// implements AbilityPlatformServDu{

	Logger logger = Logger.getLogger(AbilityPlatformServDuImpl.class);
	@Autowired
	ProParamVo ppv;
	
	public BusRespMessage CallAbilityPlatform(String json_info) throws Exception {
		logger.info("*******************能力平台调用***********************");
		logger.info("*******************能力平台调用入参***********************");
		logger.info("*******************能力平台调用入参json_info***********************" + json_info);
		logger.info("*******************能力平台调用入参***********************");
		logger.info("*******************能力平台调用***********************");

		BusRespMessage message = new BusRespMessage();
		String url = "";
		String token = "";
		// String appKey =
		// readPropertiesUtils.getPropertiesForUrl("new_appkey");
		String appKey = ppv.getNew_appkey();
		SimpleDateFormat sm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp1 = sm1.format(new Date());
		// String secret = readPropertiesUtils.getPropertiesForUrl("secret");
		String secret = ppv.getSecret();
		JSONObject data = new JSONObject();
		String serviceName = "";
		// 重庆本地接口能力平台调用
			// url = readPropertiesUtils.getPropertiesForUrl("local_url");
			url = ppv.getLocal_url();
			// token = readPropertiesUtils.getPropertiesForUrl("local_token");
			token = ppv.getLocal_token();
			data = JSONObject.fromObject(json_info);
			serviceName = data.get("SERVICE_NAME").toString();
			logger.info("----------serviceName---------" + serviceName);
		// 获取请求能力平台的配置数据
		String connectTimeout = "40000";
		String readTimeout = "40000";
		// 组装发送数据
		// 公共参数
		JSONObject tapService = new JSONObject();
		tapService.put("TOKEN", token);
		tapService.put("APP_KEY", appKey);
		tapService.put("COMMON_URL", url);
		tapService.put("SECRET", secret);
		if (connectTimeout != null && !connectTimeout.equals("")) {
			tapService.put("CONNECT_TIMEOUT", Integer.valueOf(connectTimeout)); // 毫秒
		} else {
			tapService.put("CONNECT_TIMEOUT", 10000); // 毫秒
		}
		if (readTimeout != null && !readTimeout.equals("")) {
			tapService.put("READ_TIMEOUT", Integer.valueOf(readTimeout)); // 毫秒
		} else {
			tapService.put("READ_TIMEOUT", 40000); // 毫秒
		}
		data.put("TapSerivce", tapService);
		TapServiceClient tapServiceClient = new TapServiceClient();
		JSONObject response = new JSONObject();
		String result = null;
		String code = null;
		try {
			logger.debug(data);
			response = tapServiceClient.performRequest(data);
			result = response.toString();
			logger.info("-----------能力平台接口调用返回报文-----------");
			logger.info(result);
			logger.info("-----------能力平台接口调用返回报文-----------");
			if("0000".equals(response.get("respCode").toString())){
				code="200";
			}else{
				code="9999";
			}
			message.setRespCode(BusRespMessage.SUCCESS);
			message.setContent("调用能力平台成功");
			message.addArg("json_info", response.get("args").toString());
			message.addArg("code", code);
		} catch (JSONException e) {
			code = "9999";
			response.put("code", "9999");
			response.put("detail", "响应报文异常，非json格式：" + e.getMessage());
			result = response.toString();
			logger.info(result);
			message.setRespCode(BusRespMessage.ABILITY_PLATFORM_FAIL);
			message.setContent("调用能力平台失败");
			message.addArg("json_info", result);
			message.addArg("code", code);
			return message;
		} catch (OrsException e) {
			code = "9999";
			response.put("code", "9999");
			response.put("detail", "响应报文异常：" + e.getMessage());
			result = response.toString();
			logger.info(result);
			message.setRespCode(BusRespMessage.ABILITY_PLATFORM_FAIL);
			message.setContent("调用能力平台失败");
			message.addArg("json_info", result);
			message.addArg("code", code);
			return message;
		} catch (Exception e) {
			code = "9999";
			response.put("code", "9999");
			response.put("detail", "异常信息：" + e.getMessage());
			result = response.toString();
			logger.info(result);
			message.setRespCode(BusRespMessage.ABILITY_PLATFORM_FAIL);
			message.setContent("调用能力平台失败");
			message.addArg("json_info", result);
			message.addArg("code", code);
			return message;
		}
		return message;
	}

	private Long apptx(String s) {
		Long sum = 0l;
		int tmp = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				tmp = c - '0';
			} else if (c >= 'A' && c <= 'F') {
				tmp = c - 'A' + 10;
			} else {
				break;
			}
			sum = sum * 16 + tmp;
		}
		return sum;
	}
}
