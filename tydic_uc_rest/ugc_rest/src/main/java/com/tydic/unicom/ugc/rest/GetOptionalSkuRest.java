package com.tydic.unicom.ugc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.ugc.business.common.interfaces.GetOptionalSkuDefineServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * <P>获取可选sku</P>
 * @author ZhangCheng
 * @date 2017-03-31
 * @version V1.0
 */
@RestController
@RequestMapping(value=ControllerMappings.GET_OPTIONAL_SKUR_EST)
public class GetOptionalSkuRest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GetOptionalSkuRest.class);
	
	@Autowired
	private GetOptionalSkuDefineServDu getOptionalSkuDefineServDu;
	
	/**
	 * UGC0010-REST-获取可选sku
	 * @param jsession_id
	 * @param sku_id
	 * @param sku_name
	 * @return
	 */
	@RequestMapping(value=UrlsMappings.GET_OPTIONAL_SKU_REST_METHOD,method=RequestMethod.POST)
	public UocMessage getOptionalSkuRestMethod(String jsession_id,String sku_id,String sku_name){
		
		LOGGER.debug("[UGC0010REST]==========>begin");
		LOGGER.info("[UGC0010REST]==========>请求参数：jsession_id:{},sku_id:{},sku_name:{}",jsession_id,sku_id,sku_name);
		
		UocMessage result = new UocMessage();
		
		try{
			
			result= getOptionalSkuDefineServDu.getSkuDefineBySkuIdOrSkuName(jsession_id, sku_id, sku_name);
			
		}catch(Exception ex){
			result.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			result.setContent("获取可选sku失败，调服务异常。");
			return result;
		}
		
		LOGGER.info("[UGC0010]==========>响应参数：respcode:{},content:{},arg:{}",
				result.getRespCode(),result.getContent(),result.getArgs());
		LOGGER.debug("[UGC0010REST]==========> end ");
		
		return result;
	}

}
