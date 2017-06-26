package com.tydic.unicom.ugc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.ugc.business.common.interfaces.ApGoodsServDu;
import com.tydic.unicom.ugc.business.common.interfaces.GetContactSkuMessageServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.APGOODS_REST)
public class ApGoodsServiceRest {

	Logger logger = Logger.getLogger(ApGoodsServiceRest.class);

	@Autowired
	private ApGoodsServDu apGoodsServDu;

	@Autowired
	private GetContactSkuMessageServDu getContactSkuMessageServDu;

	@RequestMapping(value = UrlsMappings.APGOODS_QUERY, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage queryApGoodsList(String jsession_id, String json_info) {
		UocMessage message = new UocMessage();

		logger.info("===json_info====" + json_info);
		try {
			message = apGoodsServDu.queryApGoodsList(jsession_id, json_info);
			logger.info("======================success============================");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("======================exception============================");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("触点商品查询接口异常");
			return message;
		}
	}

	@RequestMapping(value = UrlsMappings.GET_CONTACT_SKU, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getContactSku(String ap_id) {
		UocMessage message = new UocMessage();

		logger.info("===ap_id====" + ap_id);
		try {
			message = getContactSkuMessageServDu.queryContactSku(ap_id);
			logger.info("======================success============================");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("======================exception============================");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取触点sku信息异常");
			return message;
		}
	}

	/**
	 * 4	触点商品维护  UGC0003
	 * @param jsession_id
	 * @param json_info
	 * @param oper_type
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.OPERATE_AP_GOODS , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage operateApGoodsUGC(String jsession_id, String json_info,String oper_type){
		UocMessage message =new UocMessage();

		try {
			message = apGoodsServDu.commitOperateApGoods(jsession_id, json_info, oper_type);
		} catch (Exception e) {

			e.printStackTrace();
			  message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			  message.setContent("触点商品维护异常 ");
			  return message;
		}

		return message;
	}
	@RequestMapping(value = UrlsMappings.GET_OPTIONAL_GOODS , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOptionalGoods(String jsession_id){

		UocMessage message = new UocMessage();

		try {
			message =getContactSkuMessageServDu.getOptionalGoods(jsession_id);
			logger.info("======================success============================");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("======================exception============================");
			message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			message.setContent("通过能力平台调用商品中心接口异常");
			return message;
		}
	}
}
