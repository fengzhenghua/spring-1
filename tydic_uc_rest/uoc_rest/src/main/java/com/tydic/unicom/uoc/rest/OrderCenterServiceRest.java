package com.tydic.unicom.uoc.rest;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.common.interfaces.AbilityPlatformForYunWoServDu;
import com.tydic.unicom.uoc.business.common.interfaces.PushMsgToWebAppBusServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;


@Controller
@RequestMapping(value = ControllerMappings.ORDER_CENTER_REST)
public class OrderCenterServiceRest {

	Logger logger = Logger.getLogger(OrderCenterServiceRest.class);

	@Autowired
	private AbilityPlatformForYunWoServDu abilityPlatformForYunWoServDu;
	@Autowired
	private PushMsgToWebAppBusServDu pushMsgToWebAppBusServDu;

	@RequestMapping(value = UrlsMappings.ABILITY_PLATFORM_SERVICE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage AbilityPlatformForYunWo(String json_info){

		UocMessage message = new UocMessage();

		logger.info("===json_info===="+json_info);
		try {
			message = abilityPlatformForYunWoServDu.createPlatformForYunWo(json_info);
			if(RespCodeContents.SUCCESS.equals(message.getRespCode())){
				Map<String,Object> resMap=message.getArgs();

				//消息推送
				if (resMap != null && resMap.get("pushMsgFlag") != null) {
					String pushMsgFlag = resMap.get("pushMsgFlag").toString();
					if ("1".equals(pushMsgFlag)) {
						String operNo = resMap.get("operNo").toString();
						String msgTpye = resMap.get("msgTpye").toString();
						UocMessage pushMsgResult = pushMsgToWebAppBusServDu.pushMsgToWebAppByRedis(operNo, msgTpye);
						logger.debug("======================推送系统信息结果：" + pushMsgResult.getRespCode());
					}
				}
			}
			logger.info("======================success============================");
			return message;
		} catch (Throwable e) {
			e.printStackTrace();
			logger.info("======================exception============================");
			message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			message.setContent("通过能力平台调用订单中心接口异常");
			return message;
		}
	}

}
