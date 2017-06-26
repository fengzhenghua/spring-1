package com.tydic.unicom.apc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.apc.business.order.interfaces.OppoOrderServiceServDu;
import com.tydic.unicom.apc.business.order.vo.OppoOrderUpdateResVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.OPPO_ORDER_SERVICE_REST)
public class OppoOrderServiceRest {

	private static Logger logger = Logger.getLogger(OppoOrderServiceRest.class);
	
	@Autowired
	private OppoOrderServiceServDu oppoOrderServiceServDu;
	
	
	@RequestMapping(value = UrlsMappings.GET_AP_ORDER_ATTR_INFO,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage getApOrderAttrInfo(String ap_order_id){
		logger.info("==============获取触点订单属性值===============");
		UocMessage ucoMessage = new UocMessage();
		try {
			ucoMessage = oppoOrderServiceServDu.getApOrderAttrInfo(ap_order_id);
			return ucoMessage;
		} catch (Exception e) {
			e.printStackTrace();
			ucoMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			ucoMessage.setContent("获取触点订单属性值异常");
			return ucoMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.CREATE_OPPO_ORDER_INFO,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage createOppoOrderInfo(String oper_no,String tele_type,String order_source){
		logger.info("==============创建订单信息===============");
		UocMessage ucoMessage = new UocMessage();
		try {
			ucoMessage = oppoOrderServiceServDu.createOppoOrderInfo(oper_no, tele_type, order_source);
			return ucoMessage;
		} catch (Exception e) {
			e.printStackTrace();
			ucoMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			ucoMessage.setContent("创建订单异常");
			return ucoMessage;
		}		
	}
	
	@RequestMapping(value = UrlsMappings.CREATE_AP_ORDER,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage createApOrder(OppoOrderUpdateResVo oppoOrderUpdateResVo){
		logger.info("=================创建触点订单===========");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoOrderServiceServDu.createApOrder(oppoOrderUpdateResVo);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("创建触点订单");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.UPDATE_OPPO_ORDER_INFO,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage updateOppoOrderInfo(OppoOrderUpdateResVo oppoOrderUpdateResVo,String order_id){
		logger.info("=================更新订单相关信息表===========");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoOrderServiceServDu.updateOppoOrderInfo(oppoOrderUpdateResVo, order_id);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("更新订单异常");
			return uocMessage;
		}
		return uocMessage;
	}
	
	@RequestMapping(value = UrlsMappings.QUERY_ORDER_INFO_FROM_UOC,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage queryOrderInfoFromUoc(String sim_id, String acc_nbr,String contact_tel,String oper_no){
		logger.info("=================查询中台订单信息===========");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoOrderServiceServDu.queryOrderInfoFromUoc(sim_id, acc_nbr, contact_tel, oper_no);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("查询中台订单状态异常");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.UPDATE_ORDER_INFO_FROM_UOC,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage updateOrderInfoFromUoc(String order_id){
		logger.info("==============更新中台订单信息=============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoOrderServiceServDu.updateOrderInfoFromUoc(order_id);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("更新中台订单信息异常");
			return uocMessage;
		}
	}
	
}
