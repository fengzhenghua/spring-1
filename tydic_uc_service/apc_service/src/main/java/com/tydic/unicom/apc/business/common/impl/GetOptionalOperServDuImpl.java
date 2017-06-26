package com.tydic.unicom.apc.business.common.impl;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.apc.business.common.interfaces.GetOptionalOperServDu;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.common.interfaces.GetIsLoginServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>
 * 触点中心服务
 * </p>
 * APC0002-获取可选工号<br>
 * 
 * @author ZhangCheng
 * @version V1.0
 * @date 2017-03-06
 */
public class GetOptionalOperServDuImpl implements GetOptionalOperServDu {

	private static Logger LOGGER = Logger.getLogger(GetOptionalOperServDuImpl.class);

	/**
	 * APCBS0001-能力平台服务
	 */
	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;
	
	@Autowired
	private GetIsLoginServDu getIsLoginServDu;

	@Override
	public UocMessage getOptionalOperInfoString(String jsession_id, String oper_no, String oper_name) throws Exception {
		
		LOGGER.debug("DEBUG[APC0002]==========>获取可选工号开始<==========");
		LOGGER.info("INFO [APC0002]==========>请求参数：jsession_id：" + jsession_id+",oper_no："+oper_no+",oper_name："+oper_name);
		
		UocMessage resultUocMessage = new UocMessage();
		
		// 参数校验
		if(StringUtils.isEmpty(jsession_id)){
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("请求参数中jsession_id不能为空");
			return resultUocMessage;
		}
		
		// 校验是否登录
		resultUocMessage = getIsLoginServDu.GetIsLoginByJsessionId(jsession_id);
		if( !(RespCodeContents.SUCCESS.equals(resultUocMessage.getRespCode())) ){
			return resultUocMessage;
		}
		
		// 封装请求参数
		JSONObject requestJson = new JSONObject();
		
		Map<String,String> requestMap = new HashMap<String,String>();
		requestMap.put("jsession_id", jsession_id);
		requestMap.put("oper_no", oper_no);
		requestMap.put("oper_name", oper_name);
		
		requestJson.put("param", requestMap);
		requestJson.put("SERVICE_NAME", "getOptionalOperInfo");
		
		try{
			// 通过能力平台服务调UAC认证中心服务-UAC0003-获取可选工号接口
			resultUocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(requestJson.toString(), "", "UAC");
		}catch(Exception e){
			LOGGER.warn("WARN [APC0002]==========>通过能力平台调认证中心获取可选工号接口异常：");
			e.printStackTrace();
			
			resultUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			resultUocMessage.setContent("通过能力平台调认证中心获取可选工号接口异常");
		}
		
		LOGGER.info( "INFO [APC0002]==========>响应参数："+resultUocMessage.toString());
		LOGGER.debug("DEBUG[APC0002]==========>获取可选工号结束<==========");
		
		// 返回结果
		return resultUocMessage;
	}

}
