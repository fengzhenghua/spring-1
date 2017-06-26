package com.tydic.unicom.apc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.apc.business.common.interfaces.GetOptionalContactServDu;
import com.tydic.unicom.apc.business.pub.interfaces.ApServDu;
import com.tydic.unicom.apc.business.pub.interfaces.GetApMsgServDu;
import com.tydic.unicom.apc.business.pub.vo.ApParaVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.AP)
public class OperateApRest {

	Logger logger = LoggerFactory.getLogger(OperateApRest.class);

	@Autowired
	private ApServDu apServDu;

	@Autowired
	private GetApMsgServDu getApMsgServDu;

	@Autowired
	private GetOptionalContactServDu getOptionalContactServDu;
	/**
	 * 6	触点维护APC0005
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = UrlsMappings.OPERATE_AP)
	@ResponseBody
	public UocMessage operateAp(ApParaVo vo) {
		UocMessage message = new UocMessage();

		try {
			message =apServDu.createOperateAp(vo);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("触点维护异常");
			return message;
		}

		return message;
	}
	/**
	 * 7	触点商品维护 APC0006
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = UrlsMappings.OPERATE_AP_GOODS)
	@ResponseBody
	public UocMessage operateApGoods(ApParaVo vo) {
		UocMessage message = new UocMessage();

		try {
			message =apServDu.createOperateApGoods(vo);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("触点商品维护异常");
			return message;
		}

		return message;
	}

	/**
	 * 触点查询 APC0008
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = UrlsMappings.QUERY_AP_LIST)
	@ResponseBody
	public UocMessage queryApList(ApParaVo vo) {

		if (logger.isInfoEnabled()) {
			logger.info("INFO [APC0008]==========>请求参数：{}",vo.toString());
		}

		UocMessage message = new UocMessage();

		try {
			message = apServDu.queryApDefineInfo(vo);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("触点查询异常");
			return message;
		}

		return message;
	}

	/**
	 * 获取触点信息APC0001
	 * @param ap_id
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.QUERY_AP_MSG)
	@ResponseBody
	public UocMessage queryApMsg(String ap_id) {
		UocMessage message = new UocMessage();

		try {
			message = getApMsgServDu.queryApMsg(ap_id);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("获取触点信息异常");
			return message;
		}

		return message;
	}
	/**
	 * 获取可选触点 APC0011
	 */
	@RequestMapping(value = UrlsMappings.GET_OPTIONAL_CONTACT)
	@ResponseBody
	public UocMessage getOptionalContact(String jsession_id,String ap_id,String ap_name){
		UocMessage message = new UocMessage();

		try{
			message=getOptionalContactServDu.getOptionalContact(jsession_id, ap_id, ap_name);
		}catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("获取可选触点异常");
			return message;
		}
		return message;
	}

	@RequestMapping(value = UrlsMappings.QUERY_AP_DEVELOPERS)
	@ResponseBody
	public UocMessage getOptionalContact(ApParaVo vo) {
		UocMessage message = new UocMessage();

		try {
			message = apServDu.queryApBulidQrcodeDevelopers(vo);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("获取触点发展人异常");
			return message;
		}
		return message;
	}
}
