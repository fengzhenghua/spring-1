package com.tydic.unicom.uac.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uac.business.common.interfaces.GetOptionalOperServDu;
import com.tydic.unicom.uac.business.common.interfaces.InfoBaseOperServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 获取信息REST<br>
 * UAC0005-获取登录信息-REST<br>
 * UAC0003-获取可选工号-REST<br>
 * @author ZhangCheng
 * @version V1.0
 * @date 2017-03-02
 */
@RestController
@RequestMapping(value = ControllerMappings.BASE_OPER_REST)
public class BaseOperRest {
	
	private static Logger LOGGER = Logger.getLogger(OperRest.class);
	
	@Autowired
	private InfoBaseOperServDu infoBaseOperServDu;
	
	@Autowired
	private GetOptionalOperServDu getOptionalOperServDu;
	
	/**
	 * UAC0005-获取登录信息-REST
	 * @param jsessionId 工号或验证字符串
	 * @return 对应的操作员信息
	 */
	@RequestMapping(value =UrlsMappings.GET_BASE_OPER_INFO_BY_JSESSION_ID ,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage queryOperInfoByJsessionId(String jsessionId){
		UocMessage resultUocMessage = new UocMessage();
		
		LOGGER.info("UAC0005-获取登录信息-REST-jsessionId："+jsessionId);
		
		// 参数校验
		if(StringUtils.isEmpty(jsessionId)){
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("工号或验证字符串不能为空");
			return resultUocMessage;
		}
		// 调接口
		try{
			resultUocMessage = infoBaseOperServDu.getOperInfoByJsessionId(jsessionId);
			return resultUocMessage;
		}catch(Exception e){
			LOGGER.info("UAC0005-获取登录信息-REST-异常");
			e.printStackTrace();
			resultUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			resultUocMessage.setContent("获取工号信息异常");
			return resultUocMessage;
		}
	}
	
	/**
	 * UAC0003-获取可选工号-REST
	 * @param jsessionId 工号或验证字符串
	 * @param operNo 工号
	 * @param operName 名称
	 * @return 可选工号
	 */
	@RequestMapping(value =UrlsMappings.GET_OPER_INFO_BY_DEPART_NO ,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage getOperInfoByDepartNo(String jsessionId,String operNo,String operName){
		UocMessage resultUocMessage = new UocMessage();
		
		LOGGER.info("UAC0003-获取可选工号-REST-jsessionId:"+jsessionId+",operNo:"+operNo+",operName:"+operName);
		
		// 参数校验
		if(StringUtils.isEmpty(jsessionId)){
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("工号或验证字符串不能为空");
			return resultUocMessage;
		}
		// 调接口
		try{
			resultUocMessage = getOptionalOperServDu.getOptionalOperInfoString(jsessionId, operNo, operName);
			return resultUocMessage;
		}catch(Exception e){
			LOGGER.info("UAC0003-获取可选工号-REST-异常");
			e.printStackTrace();
			resultUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			resultUocMessage.setContent("REST->业务接口-获取可选工号异常");
			return resultUocMessage;
		}
		
	}
	
	

}
