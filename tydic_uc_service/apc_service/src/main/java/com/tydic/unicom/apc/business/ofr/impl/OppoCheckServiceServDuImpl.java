package com.tydic.unicom.apc.business.ofr.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.tydic.unicom.apc.business.common.interfaces.GetInfoServDu;
import com.tydic.unicom.apc.business.ofr.interfaces.OppoCheckServiceServDu;
import com.tydic.unicom.apc.business.pub.vo.CheckUserInfoVo;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.common.interfaces.ApcRedisServiceServDu;
import com.tydic.unicom.apc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class OppoCheckServiceServDuImpl implements OppoCheckServiceServDu{

	private static Logger logger = Logger.getLogger(OppoCheckServiceServDuImpl.class);

	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;

	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;

	@Autowired
	private GetInfoServDu getInfoServDu;

	@Autowired
	private ApcRedisServiceServDu apcRedisServiceServDu;

	@Override
	public UocMessage checkGZT(String certName, String certId,String flag) throws Exception {
		UocMessage uocMessage = new UocMessage();

		if(StringUtils.isEmpty(flag)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("校验标志不能为空");
			return uocMessage;
		}

		if("0".equals(flag)){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("不进行国政通校验");
			return uocMessage;
		}
		else{
			if(StringUtils.isEmpty(certName)){
				uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
				uocMessage.setContent("姓名不能为空");
				return uocMessage;
			}
			if(StringUtils.isEmpty(certId)){
				uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
				uocMessage.setContent("证件号不能为空");
				return uocMessage;
			}
		}

		String json_info = "{\"certName\":\"" + certName + "\",\"certId\":\"" + certId + "\"}";
		// 通过APCBS0001能力平台服务直接调用国政通校验
		JSONObject jsonObj = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		map.put("json_info", json_info);
		map.put("interface_type", "900");
		map.put("interface_param_json", "");
		map.put("interface_url", "");
		jsonObj.put("center_code", "APC");
		jsonObj.put("params", map);

		uocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(jsonObj.toString(), "900", "UIF");

		if (!"0000".equals(uocMessage.getRespCode())) {
			uocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			uocMessage.setContent("调用能力平台失败");
			return uocMessage;
		}

		if ("0000".equals(uocMessage.getRespCode()) && !"200".equals(uocMessage.getArgs().get("code"))) {
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("国政通校验失败");
			return uocMessage;
		}

		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("国政通校验通过");
		return uocMessage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage checkCustInfo(CheckUserInfoVo vo) throws Exception {
		UocMessage message=new UocMessage();
		//校验入参
		if(StringUtils.isEmpty(vo.getFlag())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("校验标志不能为空");
			return message;
		}

		if("0".equals(vo.getFlag())){
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("不进行客户资料校验");
			return message;
		}

		if(StringUtils.isEmpty(vo.getOper_no())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("oper_no入参不能为空！！！");
			return message;
		}
		if(StringUtils.isEmpty(vo.getTele_type())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("tele_type入参不能为空！！！");
			return message;
		}
		if(StringUtils.isEmpty(vo.getId_number())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("id_number入参不能为空！！！");
			return message;
		}
		if(StringUtils.isEmpty(vo.getId_type())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("id_type入参不能为空！！！");
			return message;
		}
		if(StringUtils.isEmpty(vo.getCustomer_name())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("customer_name入参不能为空！！！");
			return message;
		}
		if(StringUtils.isEmpty(vo.getCheckType())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("checkType入参不能为空！！！");
			return message;
		}
		if(StringUtils.isEmpty(vo.getFlag())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("flag入参不能为空！！！");
			return message;
		}

		//获取工号信息
		String province_code = "";
		String area_code = "";
		String district = "";
		String channel_id = "";
		String channel_type = "";

		UocMessage operInfoResult = getInfoServDu.getOperInfo(vo.getOper_no());
		if(!RespCodeContents.SUCCESS.equals(operInfoResult.getRespCode())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取工号信息失败："+operInfoResult.getContent());
			return message;
		}
		else{
			String strData = (String) operInfoResult.getArgs().get("json_info");
			JSONObject jsonObj = JSONObject.fromObject(strData);
			Map<String, Object> jsonInfomap = jsonObj;
			Map<String, Object> dataMap = (Map<String, Object>) jsonInfomap.get("oper_info");
			province_code = dataMap.get("province_code").toString();
			area_code = dataMap.get("area_code").toString();
			district = dataMap.get("district").toString();
			channel_id = dataMap.get("channel_id").toString();
			channel_type = dataMap.get("channel_type").toString();
		}


		//拼装请求入参
		JSONObject jsonObj = new JSONObject();
		JSONObject json_info = new JSONObject();
		JSONObject interface_param_json = new JSONObject();
		JSONObject params = new JSONObject();

		if ("4G".equals(vo.getTele_type())) {
			json_info.put("opeSystype", "2");
		} else {
			json_info.put("opeSystype", "1");
		}

		json_info.put("province", province_code);
		json_info.put("city", area_code);
		json_info.put("district", district);
		json_info.put("channelId", channel_id);
		json_info.put("channelType", channel_type);

		json_info.put("operatorId", vo.getOper_no());
		json_info.put("checkType", vo.getCheckType());
		json_info.put("serType", "2");
		json_info.put("serialNumber", vo.getDevice_number());
		json_info.put("certNum", vo.getId_number());
		json_info.put("certType", vo.getId_type());
		json_info.put("certName", vo.getCustomer_name());

		interface_param_json.put("bizkey", "TS-3G-012");
		interface_param_json.put("method", "ecaop.trades.query.comm.cust.check");

		params.put("json_info", json_info.toString());
		params.put("interface_param_json", interface_param_json.toString());
		params.put("interface_type", "200");
		params.put("interface_url", "");

		jsonObj.put("center_code", "APC");
		jsonObj.put("params", params.toString());



		logger.debug("=================通过能力平台去uif获取可选营业厅信息(报文):"+jsonObj.toString());
		message = apcAbilityPlatformServDu.CallApcAbilityPlatform(jsonObj.toString(), "200", "UIF");

		return message;
	}

	@Override
	public UocMessage queryUserNumber(String oper_no, String certId,String certName) throws Exception {
		UocMessage message=new UocMessage();
		//获取工号信息
		String province_code = "";
		String area_code = "";
		String district = "";
		String channel_id = "";
		String channel_type = "";

		UocMessage operInfoResult = getInfoServDu.getOperInfo(oper_no);
		if(!RespCodeContents.SUCCESS.equals(operInfoResult.getRespCode())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取工号信息失败："+operInfoResult.getContent());
			return message;
		}else{
			String strData = (String) operInfoResult.getArgs().get("json_info");
			JSONObject jsonObj = JSONObject.fromObject(strData);
			Map<String, Object> jsonInfomap = jsonObj;
			Map<String, Object> dataMap = (Map<String, Object>) jsonInfomap.get("oper_info");
			province_code = dataMap.get("province_code").toString();
			area_code = dataMap.get("area_code").toString();
			district = dataMap.get("district").toString();
			channel_id = dataMap.get("channel_id").toString();
			channel_type = dataMap.get("channel_type").toString();
		}


		//拼装请求入参COM_BUS_INFO
		JSONObject jsonObj = new JSONObject();
		JSONObject params = new JSONObject();
		JSONObject json_info = new JSONObject();
		JSONObject uni_bss_head = new JSONObject();
		JSONObject uni_bss_body = new JSONObject();
		JSONObject qry_user_number_req = new JSONObject();
		JSONObject uni_bss_attachen = new JSONObject();
		JSONObject sp_reserve = new JSONObject();
		JSONObject routing = new JSONObject();
		JSONObject com_bus_info = new JSONObject();
		String trans_id ="APC"+System.currentTimeMillis();

		qry_user_number_req.put("CHECK_TYPE", "1");
		if(certId.length()==18){
			qry_user_number_req.put("CERT_TYPE", "01");
		}else if (certId.length()==15) {
			qry_user_number_req.put("CERT_TYPE", "12");
		}
		qry_user_number_req.put("CERT_NAME", certName);
		qry_user_number_req.put("CERT_NUM", certId);
		uni_bss_body.put("QRY_USER_NUMBER_REQ", qry_user_number_req);

		uni_bss_attachen.put("MEDIA_INFO", "");

		sp_reserve.put("CONV_ID", "");
		sp_reserve.put("CUTOFFDAY", "");
		sp_reserve.put("HSNDUNS", "1100");
		sp_reserve.put("OSNDUNS", 5000);
		sp_reserve.put("TRANS_IDC", "");

		routing.put("ROUTE_TYPE", "00");
		routing.put("ROUTE_VALUE", "28");

		com_bus_info.put("OPER_ID", oper_no);
		com_bus_info.put("PROVINCE_CODE", province_code);
		com_bus_info.put("CHANNEL_ID", channel_id);
		com_bus_info.put("CHANNEL_TYPE", channel_type);
		com_bus_info.put("EPARCHY_CODE", "110");
		com_bus_info.put("ACCESS_TYPE", "06");
		com_bus_info.put("ORDER_TYPE", "00");

		uni_bss_head.put("ACTION_CODE", "0");
		uni_bss_head.put("ACTION_RELATION", "0");
		uni_bss_head.put("COM_BUS_INFO", com_bus_info);
		uni_bss_head.put("MSG_RECEIVER", "0001");
		uni_bss_head.put("MSG_SENDER", "0002");
		uni_bss_head.put("OPERATE_NAME", "QryUserNumber");
		uni_bss_head.put("ORIG_DOMAIN", "UCRM");
		uni_bss_head.put("PROCESS_TIME", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		uni_bss_head.put("PROC_ID", trans_id);
		uni_bss_head.put("ROUTING", routing);
		uni_bss_head.put("SERVICE_NAME", "OneCardFiveUserSer");
		uni_bss_head.put("SP_RESERVE", sp_reserve);
		uni_bss_head.put("TEST_FLAG", "0");
		uni_bss_head.put("TRANS_IDO", trans_id);

		json_info.put("UNI_BSS_HEAD", uni_bss_head);
		json_info.put("UNI_BSS_BODY", uni_bss_body);
		json_info.put("UNI_BSS_ATTACHED", uni_bss_attachen);

		params.put("json_info", json_info);
		params.put("interface_param_json", "");
		params.put("interface_type", "1300");
		params.put("interface_url", "");

		jsonObj.put("center_code", "APC");
		jsonObj.put("params", params);

		logger.debug("=================通过能力平台去uif进行一证五户校验报文:"+jsonObj.toString());
		message = apcAbilityPlatformServDu.CallApcAbilityPlatform(jsonObj.toString(), "", "UIF");

		if(RespCodeContents.SUCCESS.equals(message.getRespCode())){
			String code=(String) message.getArgs().get("code");
			if("200".equals(code)){
				JSONObject rspInfo= JSONObject.fromObject((message.getArgs().get("json_info")));
				String rspCode=(String) rspInfo.get("code");
				if("200".equals(rspCode)){
					JSONObject jsonObject=rspInfo.getJSONObject("json_info");
					JSONObject body=jsonObject.getJSONObject("UNI_BSS_BODY");
					JSONObject nuberRsp=body.getJSONObject("QRY_USER_NUMBER_RSP");
					JSONObject countInfo=nuberRsp.getJSONObject("RESP_INFO");
					String USER_AMOUNT=(String) countInfo.get("USER_AMOUNT");
					int count=Integer.valueOf(USER_AMOUNT);
					message.setRespCode(RespCodeContents.SUCCESS);
					message.addArg("USER_AMOUNT", count);
				}else {
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("一证五户校验失败！");
				}
			}else {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("一证五户校验失败！");
			}
		}else {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent(message.getContent());
		}

		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage checkCertNumsForAp(String oper_no, String ap_id, String cert_no) throws Exception {
		UocMessage uocMessage = new UocMessage();
		Integer cert_limit_nums = 1;// 默认数量

		logger.info("=================从缓存中获取触点配置ap_id:" + ap_id);
		UocMessage redisInfo = apcRedisServiceServDu.queryForApAttr(ap_id);
		if (RespCodeContents.SUCCESS.equals(redisInfo.getRespCode())) {
			Map<String, Object> map = (Map<String, Object>) redisInfo.getArgs().get("apAttrInfo");
			if (map.get("cert_limit_nums") != null) {
				cert_limit_nums = Integer.parseInt(map.get("cert_limit_nums").toString());
				logger.info("=================ap_attr表中对应数量配置cert_limit_nums:" + cert_limit_nums);
			} else {
				logger.info("=================ap_attr表中未获取到配置cert_limit_nums");
			}
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		// 过去一年
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		Date lastYear = c.getTime();
		String accept_time_start = format.format(lastYear);
		String accept_time_end = format.format(new Date());

		// 调用ES查询服务UOC0074根据身份证查询一年内数据
		JSONObject json_param = new JSONObject();
		JSONObject params = new JSONObject();
		params.put("jsession_id", oper_no);
		params.put("page_no", "1");
		params.put("page_size", "5");
		params.put("cert_no", cert_no);
		params.put("accept_time_start", accept_time_start);
		params.put("accept_time_end", accept_time_end);

		json_param.put("params", params.toString());
		json_param.put("SERVICE_NAME", "queryServOrderListES");

		logger.info("=================通过能力平台调用UOC0074根据身份证查询一年内数据:" + json_param.toString());
		UocMessage respMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(json_param.toString(), "", "UOC");
		if (RespCodeContents.SUCCESS.equals(respMessage.getRespCode())) {
			String code = (String) respMessage.getArgs().get("code");
			if ("200".equals(code)) {
				String order_info = (String) respMessage.getArgs().get("json_info");
				JSONObject jsonObject = JSONObject.fromObject(order_info);
				Map<String, Object> info_serv_order_list = (Map<String, Object>) jsonObject.get("info_serv_order_list");
				Integer total_num = Integer.parseInt(info_serv_order_list.get("total_num").toString());

				logger.info("=================该身份证已开卡数量:" + total_num + "，可开卡数量:" + cert_limit_nums);
				if (total_num < cert_limit_nums) {
					uocMessage.addArg("checkFlag", "0");// 校验通过
				} else {
					uocMessage.addArg("checkFlag", "1");// 校验不通过
				}

			} else {
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("通过能力平台调用UOC0074查询失败");
				return uocMessage;
			}
		} else {
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent(respMessage.getContent());
			return uocMessage;
		}

		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("触点身份证开卡数量校验成功");
		return uocMessage;
	}

}
