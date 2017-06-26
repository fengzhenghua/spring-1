package com.tydic.unicom.uoc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.common.interfaces.CqSFServiceServDu;
import com.tydic.unicom.uoc.business.common.interfaces.SFServiceServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.LOGISTICS_SERVICE)
public class LogisticsServiceRest {

	private static Logger logger = Logger.getLogger(LogisticsServiceRest.class);

	@Autowired
	private SFServiceServDu sFServiceServDu;
	
	@Autowired
	private CqSFServiceServDu cqSFServiceServDu;

	@RequestMapping(value = UrlsMappings.GET_SF_LOGISTICS_ROUTER_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getSFLogisticsRouterInfo(String jsession_id,String order_no,String order_no_type,String query_type){
		logger.info("=============获取顺丰物流信息（rest）============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = sFServiceServDu.getLogisticsRouterInfo(jsession_id, order_no, order_no_type, query_type);
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取顺丰物流信息接口异常");
			return uocMessage;
		}
		return uocMessage;
	}

	@RequestMapping(value = UrlsMappings.SEND_SF_LOGISTICS_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage sendSFLogisticsInfo(String jsession_id, String serv_order_no, String d_contact, String d_tel, String d_address,
			String j_tel, String j_contact, String j_address, String name, String remark, String flow_type, String deal_code,
			String deal_desc, String deal_system_no, String cod_account, String cod_charge, String insure_charge,
			String need_return_tracking_no) {
		logger.info("==============顺丰发货（rest）=============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = sFServiceServDu.sendlogisticsInfo(jsession_id, serv_order_no, d_contact, d_tel, d_address, j_tel, j_contact,
					j_address, name, remark, flow_type, deal_code, deal_desc, deal_system_no, cod_account, cod_charge, insure_charge,
					need_return_tracking_no);
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("顺丰发货接口异常");
			return uocMessage;
		}
		return uocMessage;
	}

	@RequestMapping(value = UrlsMappings.SEND_SF_LOGISTICS_INFO_NO_PROCESS , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage sendSFLogisticsInfoNoProcess(String jsession_id, String serv_order_no, String d_contact, String d_tel,
			String d_address, String j_tel, String j_contact, String j_address, String name, String remark, String cod_account,
			String cod_charge, String insure_charge, String need_return_tracking_no) {
		logger.info("==============顺丰发货无流程（rest）=============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = sFServiceServDu.sendLogisticsInfoNoProcess(jsession_id, serv_order_no, d_contact, d_tel, d_address, j_tel,
					j_contact, j_address, name, remark, cod_account, cod_charge, insure_charge, need_return_tracking_no);
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		}catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("顺丰发货接口异常");
			return uocMessage;
		}
		return uocMessage;
	}
	
	//重庆本地调用东方国信顺丰接口
	@RequestMapping(value = UrlsMappings.GET_SF_LOGISTICS_ROUTER_INFO_CQ , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getSFLogisticsRouterInfoCq(String jsession_id,String order_no,String order_no_type,String query_type){
		logger.info("=============获取顺丰物流信息（rest）============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = cqSFServiceServDu.getLogisticsRouterInfoCq(jsession_id, order_no, order_no_type, query_type);
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取顺丰物流信息接口异常");
			return uocMessage;
		}
		return uocMessage;
	}

	//重庆本地调用东方国信顺丰接口
	@RequestMapping(value = UrlsMappings.SEND_SF_LOGISTICS_INFO_CQ , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage sendSFLogisticsInfoCq(String jsession_id, String serv_order_no, String d_contact, String d_tel, String d_address,
			String j_tel, String j_contact, String j_address, String name, String remark, String flow_type, String deal_code,
			String deal_desc, String deal_system_no, String cod_account, String cod_charge, String insure_charge,
			String need_return_tracking_no) {
		logger.info("==============顺丰发货（rest）=============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = cqSFServiceServDu.sendlogisticsInfoCq(jsession_id, serv_order_no, d_contact, d_tel, d_address, j_tel, j_contact,
					j_address, name, remark, flow_type, deal_code, deal_desc, deal_system_no, cod_account, cod_charge, insure_charge,
					need_return_tracking_no);
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("顺丰发货接口异常");
			return uocMessage;
		}
		return uocMessage;
	}

	//重庆本地调用东方国信顺丰接口
	@RequestMapping(value = UrlsMappings.SEND_SF_LOGISTICS_INFO_NO_PROCESS_CQ , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage sendSFLogisticsInfoNoProcessCq(String jsession_id, String serv_order_no, String d_contact, String d_tel,
			String d_address, String j_tel, String j_contact, String j_address, String name, String remark, String cod_account,
			String cod_charge, String insure_charge, String need_return_tracking_no) {
		logger.info("==============顺丰发货无流程（rest）=============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = cqSFServiceServDu.sendLogisticsInfoNoProcessCq(jsession_id, serv_order_no, d_contact, d_tel, d_address, j_tel,
					j_contact, j_address, name, remark, cod_account, cod_charge, insure_charge, need_return_tracking_no);
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		}catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("顺丰发货接口异常");
			return uocMessage;
		}
		return uocMessage;
	}
}
