package com.tydic.unicom.apc.business.common.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.apc.business.common.interfaces.GetOptionalRegionServDu;
import com.tydic.unicom.apc.business.common.vo.InfoRegionVo;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

import net.sf.json.JSONObject;

/**
 * 触点中心服务
 * APC0013-获取可选地区<br>
 * @author ZhangCheng
 * @date 2017-04-14
 */
public class GetOptionalRegionServDuImpl implements GetOptionalRegionServDu{

	private static Logger logger = LoggerFactory.getLogger(GetOptionalDeveServDuImpl.class);

	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;

	
	@Override
	public UocMessage GetOptionalRegionInfo(InfoRegionVo infoRegionVo) throws Exception {
		UocMessage resultUocMessage = new UocMessage();
		
		if(logger.isInfoEnabled()){
			logger.info("[APC0013]==========>请求参数：jsession_id：{},region_id：{},"
				+ "region_name：{},parent_region_id,region_level:{}",infoRegionVo.getJsession_id(),infoRegionVo.getRegion_id(),
				infoRegionVo.getRegion_name(),infoRegionVo.getParent_region_id(),infoRegionVo.getRegion_level());
		}
		
		// 封装请求参数
		JSONObject requestJson = new JSONObject();
		
		Map<String,String> requestMap = new HashMap<String,String>();
		requestMap.put("jsession_id", infoRegionVo.getJsession_id());
		requestMap.put("region_id", infoRegionVo.getRegion_id());
		requestMap.put("region_name", infoRegionVo.getRegion_name());
		requestMap.put("parent_region_id", infoRegionVo.getParent_region_id());
		requestMap.put("region_level", infoRegionVo.getRegion_level());
		
		requestJson.put("param", requestMap);
		requestJson.put("SERVICE_NAME", "queryRegionInfo");
		
		try{
			// 通过能力平台服务调UAC认证中心服务-UAC0009-获取可选地区服务
			resultUocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(requestJson.toString(), "", "UAC");
		} catch(Exception e){
			
			if(logger.isWarnEnabled()){
				logger.warn("[APC0013]==========>通过能力平台调认证中心获取可选地区服务异常：{}",e);				
			}
			
			resultUocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			resultUocMessage.setContent("通过能力平台调认证中心获取可选地区服务异常");
		}
		
		if(logger.isInfoEnabled()){
			logger.info("[APC0013]==========>响应参数：返回响应消息：{}",resultUocMessage.getContent());			
		}
		
		// 返回结果
		return resultUocMessage;
	}
}
