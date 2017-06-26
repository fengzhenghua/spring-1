package com.tydic.unicom.uoc.business.order.pay.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.uoc.business.order.pay.interfaces.GrantFeePayServDu;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class GrantFeePayServDuImpl implements GrantFeePayServDu {
	Logger logger = Logger.getLogger(GrantFeePayServDuImpl.class);

	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;
	@Autowired
	private OperServDu operServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	/*
	 * AOP赠款 UOC0080
	 */
	@Override
	public UocMessage promFeeForAop(String jsession_id, String serv_order_no, String prom_fee, String acc_nbr) throws Exception {
		UocMessage uocMessage = new UocMessage();

		if (StringUtils.isEmpty(jsession_id) || StringUtils.isEmpty(serv_order_no) || StringUtils.isEmpty(prom_fee)
				|| StringUtils.isEmpty(acc_nbr)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("必传参数为空");
			return uocMessage;
		}

		// 1、调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
		UocMessage res = operServDu.isLogin(jsession_id);
		if (!"0000".equals(res.getRespCode())) {
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent(res.getContent());
			return uocMessage;
		}
		@SuppressWarnings("unchecked")
		Map<String, String> oper_info = (Map<String, String>) res.getArgs().get("oper_info");
		String channel_id = oper_info.get("channel_id");
		String channel_type = oper_info.get("channel_type");
		String district = oper_info.get("district");
		String province_code = oper_info.get("province_code");
		String area_code = oper_info.get("area_code");
		String oper_no = oper_info.get("oper_no");
		String sysDate = DateUtil.getSysDateString("yyyyMMddHHmmss");

		Map<String, Object> resJson = new HashMap<String, Object>();
		resJson.put("channelId", channel_id);
		resJson.put("channelType", channel_type);
		resJson.put("city", area_code);
		resJson.put("district", district);
		resJson.put("operatorId", oper_no);
		resJson.put("province", province_code);
		resJson.put("traId", serv_order_no);
		resJson.put("payMentId", "100302");
		resJson.put("serviceClassCode", "0050");
		resJson.put("serialNumber", acc_nbr);
		resJson.put("fee", prom_fee);
		resJson.put("feeTime", sysDate);
		resJson.put("payType", "1");

		String json_info = jsonToBeanServDu.mapToJson(resJson);
		String interface_type = "200";
		String interface_param_json = "{\"bizkey\":\"PC-004\",\"method\": \"ecaop.trades.serv.grant.fee.sub\"}";
		// BASE0018 调用能力平台服务
		UocMessage uocMessageAbilityPlat = abilityPlatformServDu.CallAbilityPlatform(json_info, interface_type, interface_param_json, "");
		if (!"0000".equals(uocMessageAbilityPlat.getRespCode())) {
			return uocMessageAbilityPlat;
		} else {
			String code = uocMessageAbilityPlat.getArgs().get("code").toString();
			String json_info_in = (String) uocMessageAbilityPlat.getArgs().get("json_info");
			if (!"200".equals(code)) {
				JSONObject json = JSONObject.fromObject(json_info_in);
				String resultStr = json.getString("detail");
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(resultStr);
				return uocMessage;
			}
			uocMessage.addArg("abilityPlatJson", json_info_in);
		}

		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("调用AOP赠款接口成功");
		return uocMessage;
	}

}
