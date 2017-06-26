package com.tydic.unicom.uoc.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.tydic.unicom.sms.ShortMessage;
import com.tydic.unicom.sms.TextShortMessage;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.uoc.service.sms.interfaces.IMessageSendService;
import com.tydic.unicom.uoc.service.sms.vo.SmsAbilitySendVo;
import com.tydic.unicom.webUtil.UocMessage;

/**
 *
 * <p>
 * </p>
 * @author heguoyong 2017年5月31日 上午11:47:25
 * @ClassName MessageSendServiceImpl
 * @Description 短信发送服务
 * @version V1.0
 */
@Component
public class MessageSendServiceImpl implements IMessageSendService {
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;

	@Override
	public boolean sendShortMessage(ShortMessage<?> msg) {
		// 发送成功
		boolean sentSuc = false;
		if (msg != null && !StringUtils.isEmpty(msg.getDesMobile())) {
			if(msg instanceof TextShortMessage){
				sentSuc = sendTextMessage((TextShortMessage)msg);
			}
		}
		return sentSuc;
	}

	/**
	 * 发送文本短信
	 *
	 * @param textMsg
	 * @return
	 */
	public boolean sendTextMessage(TextShortMessage textMsg) {
		// 发送成功
		boolean sentSuc = false;
		if (textMsg != null) {
			String content = textMsg.getMsgContent();
			String phone = textMsg.getDesMobile();
			if (!StringUtils.isEmpty(content) && !StringUtils.isEmpty(phone)) {
				SmsAbilitySendVo smsSendVo = new SmsAbilitySendVo();
				smsSendVo.setAppKey(propertiesParamVo.getSmsAppKey());
				smsSendVo.setMobile(phone);
				smsSendVo.setContent(content);
				smsSendVo.setSendTime("");
				smsSendVo.setSubPort("");

				String json_info_out = JSON.toJSONString(smsSendVo);
				try {
					// BASE0018 调用能力平台服务
					UocMessage uocMessageAbilityPlat = abilityPlatformServDu.CallAbilityPlatform(json_info_out, "1500", "", "");
					if (uocMessageAbilityPlat != null) {
						if ("0000".equals(uocMessageAbilityPlat.getRespCode())) {
							String code = (String) uocMessageAbilityPlat.getArgs().get("code");
							if (code != null && code.equals("200")) {
								sentSuc = true;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return sentSuc;
	}
}
