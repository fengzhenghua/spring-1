package com.tydic.unicom.uoc.rest;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.common.interfaces.CheckInfoServDu;
import com.tydic.unicom.uoc.business.common.vo.InfoIDCardVo;
import com.tydic.unicom.uoc.business.common.vo.LivingCheckVo;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.CHECK_INFO_REST)
public class CheckInfoRest {

	Logger logger = Logger.getLogger(CheckInfoRest.class);
	
	@Autowired
	private CheckInfoServDu checkInfoServDu;
	
	/**
	 * ⦁ 国政通校验	UOC0049
	 */
	@RequestMapping(value = UrlsMappings.GZT_VALID , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage gztValid(InfoIDCardVo vo,HttpSession session) {
		logger.info("rest-----------gztValid");
		String path =session.getServletContext().getRealPath("/");
		path = path.replaceAll("\\\\", "/");
		logger.info(path);
		UocMessage message =new UocMessage();
		try {
			message = checkInfoServDu.createGztValid(vo, path);
		} 
		catch (UocException e){
			e.printStackTrace();
			return e.getUocMessage();
		}
		catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("国政通校验异常");
			return message;
		}
		return message;
	}
	
	/**
	 * ⦁ 活体自动审核结果通知	UOC0051
	 */
	@RequestMapping(value = UrlsMappings.LIVING_CHECK_RESULT , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage livingChecKResult(LivingCheckVo vo) {
		logger.info("rest-----------livingChecKResult");
		UocMessage  message =new UocMessage();
		try {
			message = checkInfoServDu.createLivingChecKResult(vo);
		}
		catch (UocException e){
			e.printStackTrace();
			return e.getUocMessage();
		}
		catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("活体自动审核结果通知异常");
			return message;
		}
		return message;
	}
	
	
	/**
	 * ⦁ 获取证件信息
	 */
	@RequestMapping(value = UrlsMappings.GET_CERT_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getCertInfo(ParaVo vo) {
		logger.info("rest-----------getCertInfo");
		UocMessage  message =new UocMessage();
		try {
			message = checkInfoServDu.getCertInfo(vo);
		}
		catch (UocException e){
			e.printStackTrace();
			return e.getUocMessage();
		}
		catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("获取证件信息异常");
			return message;
		}
		return message;
	}
	/**
	 * 获取工号信息UOC0056
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.GET_OPER_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOperInfo(String jsession_id) {
		logger.info("rest-----------getOperInfo");
		UocMessage  message =new UocMessage();
		try {
			message =checkInfoServDu.getOperInfo(jsession_id);
		} 
		catch (UocException e){
			e.printStackTrace();
			return e.getUocMessage();
		}
		catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("获取工号信息异常");
			return message;
		}
		return message;
	}
	
	/**
	 * 106	活体认证请求UOC0077
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.CTREATE_NEW_LIVING_CHECK , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage createNewLivingChecK(String jsession_id,
			String serv_order_no,String json_info,String flow_flag,String threshold_low,String threshold_high) {
		logger.info("rest-----------createNewLivingChecK");
		UocMessage  message =new UocMessage();
		try {
			message =checkInfoServDu.createNewLivingChecK (jsession_id, serv_order_no, json_info, flow_flag, threshold_low, threshold_high);
		} 
		catch (UocException e){
			e.printStackTrace();
			return e.getUocMessage();
		}
		catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("活体认证请求异常");
			return message;
		}
		return message;
	}
}
