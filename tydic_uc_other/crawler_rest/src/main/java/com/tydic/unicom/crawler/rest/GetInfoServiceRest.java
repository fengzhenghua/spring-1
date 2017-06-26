package com.tydic.unicom.crawler.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crawler.business.interfaces.GetInfoServiceBus;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = ControllerMappings.GET_INFO_SERVICE_REST)
public class GetInfoServiceRest {

	private static Logger logger = Logger.getLogger(GetInfoServiceRest.class);
	
	@Autowired
	private GetInfoServiceBus getInfoServiceBus;
	
	@RequestMapping(value = UrlsMappings.GET_SAFE_CODE , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage getSafeCode(String json_info){
		BusRespMessage message = new BusRespMessage();
		try {
			logger.info("[BEG]得到安全码参数："+json_info);
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setUser(json.getString("user"));
			brpv.setType(json.getString("type"));
			message = getInfoServiceBus.getSafeCode(brpv);
			logger.info("[END]获取验证码#OK#:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("获取验证码异常");
			logger.info("[ERR]获取验证码异常:"+e.getMessage());
			return message;
		}
	}
	
	@RequestMapping(value = UrlsMappings.CRAWLER_INIT_METHOD , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage crawlerinitmethod(String json_info){
		BusRespMessage message = new BusRespMessage();
		try {
			logger.info("[BEG]初始化爬虫参数："+json_info);
			message = getInfoServiceBus.initSysParamsMethod(null);
			logger.info("[END]初始化爬虫参数#OK#:"+message.toString());
			return message;
		} catch (Exception e) {
			logger.info("[================================ERR]初始化参数异常",e);
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("初始化参数失败！");
			logger.info("[ERR]初始化参数异常",e);
			return message;
		}
	}
	
	@RequestMapping(value = UrlsMappings.CRAWLER_GETUSERNAME_METHOD , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage crawlergetusernamemethod(String json_info){
		BusRespMessage message = new BusRespMessage();
		try {
			logger.info("[BEG]通过SESSIOID得到用户名："+json_info);
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setJsession_id(json.getString("jsession_id"));
			message = getInfoServiceBus.getUserNameMethod(brpv);
			logger.info("[END]通过SESSIOID得到用户名#OK#:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("获取用户名异常");
			logger.info("[ERR]通过SESSIOID得到用户名异常:"+e.getMessage());
			return message;
		}
	}
	
	
	@RequestMapping(value = UrlsMappings.GETORDERINFOLIST , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage getOrderInfoListmethod(String json_info){
		BusRespMessage message = new BusRespMessage();
		try {
			logger.info("[BEG]查询爬取列表："+json_info);
			if(json_info == null){
				throw new Exception("参数不能为空");
			}
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setUser(json.getString("user"));
			brpv.setOther(json_info);
			message = getInfoServiceBus.getOrderInfoList(brpv);
			logger.info("[END]查询爬取列表#OK#:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("查询爬取列表");
			logger.info("[ERR]查询爬取列表异常:"+e.getMessage());
			return message;
		}
	}
	
	
	@RequestMapping(value = UrlsMappings.GETCOUNTSTATUS , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage getCountStatus(String json_info){
		BusRespMessage message = new BusRespMessage();
		try {
			//	select order_status,count(0) from crawler_info where order_status in (0,43,53) group by order_status
			logger.info("[BEG]得到状态数据："+json_info);
			if(json_info == null){
				throw new Exception("参数不能为空");
			}
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setUser(json.getString("user"));
			brpv.setOther(json_info);
			message = getInfoServiceBus.getCountStatus(brpv);
			logger.info("[END]得到状态数据#OK#:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("得到状态数据异常");
			logger.info("[ERR]得到状态数据异常:"+e.getMessage());
			return message;
		}
	}

	@RequestMapping(value = UrlsMappings.GETORDERINFOITEM , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage getOrderInfoItem(String json_info){
		//http://localhost:8080/crawler_rest/toDetail.jsp?ciuuid=50408AF8D8029934E050937B5DDF6153&username=CF0540
		BusRespMessage message = new BusRespMessage();
		try {
			//	select order_status,count(0) from crawler_info where order_status in (0,43,53) group by order_status
			logger.info("[BEG]得到数据："+json_info);
			if(json_info == null){
				throw new Exception("参数不能为空");
			}
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setUser(json.getString("user"));
			brpv.setOther(json.optString("ciuuid",""));
			brpv.setOrder_id(json.optString("orderid",""));
			message = getInfoServiceBus.getOrderInfoItem(brpv);
			logger.info("[END]得到数据#OK#:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("得到数据异常");
			logger.info("[ERR]得到数据异常:"+e.getMessage());
			return message;
		}
	}
	
	
}
