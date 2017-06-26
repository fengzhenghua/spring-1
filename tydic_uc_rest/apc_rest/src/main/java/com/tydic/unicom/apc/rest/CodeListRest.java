package com.tydic.unicom.apc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tydic.unicom.apc.business.common.interfaces.CodeListServDu;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * APC系统CodeList服务
 * @author ZhangCheng
 * @date 2017-04-19
 */
@RestController
@RequestMapping(value = ControllerMappings.CODE_LIST_REST)
public class CodeListRest {
	
	private static Logger logger = LoggerFactory.getLogger(OptionalOperRest.class);
	
	@Autowired
	private CodeListServDu codeListServDu;
	
	/** 
	 * 根据TypeCode获取CodeList
	 * */
	@RequestMapping(value=UrlsMappings.GET_CODE_LIST_BY_TYPE_CODE,method=RequestMethod.POST)
	public UocMessage getCodeListByTypeCode(String jsession_id,String type_code){
		
		if(logger.isInfoEnabled()){
			logger.info("==========>请求参数：{}",type_code);
		}
		
		UocMessage respUocMessage = new UocMessage();
		
		// 调接口
		try {
			respUocMessage= codeListServDu.queryCodeListByTypeCode(jsession_id,type_code);
		} catch (Exception e) {
			respUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			respUocMessage.setContent("调获取可选地区服务异常");
			return respUocMessage;
		}
		
		return respUocMessage;
	}
}
