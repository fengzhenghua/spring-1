package com.tydic.unicom.apc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tydic.unicom.apc.business.ofr.interfaces.OppoCommonServiceServDu;
import com.tydic.unicom.apc.business.ofr.vo.CodeAreaVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.OPPO_COMMON_SERVICE_REST)
public class OppoCommonServiceRest {

	private static Logger logger = Logger.getLogger(OppoCommonServiceRest.class);
	
	@Autowired
	private OppoCommonServiceServDu oppoCommonServiceServDu;
	
	@RequestMapping(value = UrlsMappings.GET_ALL_PROVINCE_INFO,method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getAllProvinceInfo(){
		logger.info("============获取所有省份信息===========");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoCommonServiceServDu.getAllProvinceInfo();
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取所有省份信息异常");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.GET_PROVINCE_CODE,method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getProvinceCode(){
		logger.info("============获取省份编码===========");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoCommonServiceServDu.getProvinceCode();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取省份编码异常");
			return uocMessage;
		}
		return uocMessage;
	}
	
	@RequestMapping(value = UrlsMappings.GET_AREA_INFO,method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getAreaInfo(CodeAreaVo codeAreaVo){
		logger.info("=============获取地区信息===========");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoCommonServiceServDu.getAreaInfo(codeAreaVo);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取区域信息异常");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.GET_APC_ORDER_ID,method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getApcOrderId(String serv_order_no){
		logger.info("==============根据订单中心服务订单号查询触点订单号==============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoCommonServiceServDu.getApcOrderId(serv_order_no);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取触点订单号异常");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.GET_APC_INFO_BY_SIM_AND_DEVICE_NUMBER,method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getApcInfoBySimAndDeviceNumber(String sim_id,String acc_nbr,String contact_phone){
		logger.info("==============激活流程通过卡号和号码获取触点信息==============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoCommonServiceServDu.getApcInfoBySimAndDeviceNumber(sim_id, acc_nbr, contact_phone);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取触点信息异常");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.CALL_BACK_UPDATE_APC_ORDER,method = RequestMethod.POST)
	@ResponseBody
	public UocMessage callBackUpdateApcOrder(String json_info){
		logger.info("==============中台回调更新触点中心订单信息==============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoCommonServiceServDu.updateCallBackApcOrder(json_info);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("更新触点订单异常");
			return uocMessage;
		}
	}
}
