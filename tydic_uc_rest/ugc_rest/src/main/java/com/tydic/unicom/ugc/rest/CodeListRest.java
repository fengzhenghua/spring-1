package com.tydic.unicom.ugc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.service.cache.service.redis.interfaces.UgcRedisInitServ;
import com.tydic.unicom.ugc.business.common.interfaces.GetCodeListServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.CODE_LIST)
public class CodeListRest {
	
	Logger logger = Logger.getLogger(CodeListRest.class);
	@Autowired 
	private GetCodeListServDu getCodeListServDu;
	@Autowired
	private UgcRedisInitServ ugcRedisInitServ;
	/**
	 * UGC0011
	 * @param jession_id
	 * @param type_code
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.GET_CODE_LIST_BY_TYPE, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getCodeListByType(String jsession_id,String type_code){
		UocMessage message = new UocMessage();
		try{
			message = getCodeListServDu.getCodeListData(jsession_id, type_code);
			
		}catch(Exception e){
			e.printStackTrace();
			message.setContent(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取codeList数据失败");
			return message;
		}
		return message;
	}
	
	/**
	 * 刷ugc缓存
	 */
	@RequestMapping(value = UrlsMappings.REFRESH_UGC_REDIS_ALL , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage refreshUgcRedisAll(){
		boolean result = false;
		UocMessage uocMessage = new UocMessage();
		try{
			logger.info("=============刷新UgcRedis==============");
			logger.info("==============删除UgcRedis数据================");
			result = ugcRedisInitServ.ugcInitDeleteDataFromRedis();
			logger.info("==============写入UgcRedis数据================");
			result = ugcRedisInitServ.ugcInitCreateDataToRedis();
			if(!result){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("刷新缓存失败");
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.setContent("刷新缓存成功");
			}
		}catch(Exception e){
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("刷新缓存异常");
			return uocMessage;
		}
		return uocMessage;		
	}
}
