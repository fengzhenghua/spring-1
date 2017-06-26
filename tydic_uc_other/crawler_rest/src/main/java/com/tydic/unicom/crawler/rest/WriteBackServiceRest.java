package com.tydic.unicom.crawler.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crawler.business.interfaces.WriteBackServiceBus;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = ControllerMappings.WRITE_BACK_SERVICE_REST)
public class WriteBackServiceRest {

	private static Logger logger = Logger.getLogger(WriteBackServiceRest.class);
	
	@Autowired
	private WriteBackServiceBus writeBackServiceBus;
	
	@RequestMapping(value = UrlsMappings.WRITE_BACK_MANUAL_ACCOUNT , method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 回写总部手工开户接口
	 * @param json_info
	 * @return
	 */
	public BusRespMessage writeBackManualAccount(String json_info){
		BusRespMessage message = new BusRespMessage();
		logger.info("[BEG]回写总部接口："+json_info);
		try {
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setUser(json.getString("user"));
			brpv.setPassword(json.getString("pwd"));
			message = writeBackServiceBus.writeBackManualAccountMethod(brpv);
			logger.info("[END]回写总部手工开户接口:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("回写手工开户异常");
			logger.info("[ERR]回写总部手工开户接口:"+e.getMessage());
			return message;
		}
	}
	
	@RequestMapping(value = UrlsMappings.WRITE_BACK_EXPRESS , method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 回写总部物流接口
	 * @param json_info
	 * @return
	 */
	public BusRespMessage writeBackExpress(String json_info){
		BusRespMessage message = new BusRespMessage();
		logger.info("[BEG]回写总部物流接口："+json_info);
		try {
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setUser(json.getString("user"));
			brpv.setPassword(json.getString("pwd"));
			message = writeBackServiceBus.writeBackExpressMethod(brpv);
			logger.info("[END]回写总部物流接口:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("回写总部物流接口异常");
			logger.info("[ERR]回写总部物流接口:"+e.getMessage());
			return message;
		}
	}
	
	/**
	 * 订单回调接口
	 * @param json_info
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.CRAWLER_RECALL_METHOD , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage crawlerRecallMethod(String params){
		
		BusRespMessage message = new BusRespMessage();
		logger.info("[BEG]订单中心回调爬虫 params："+params);
		
		try {
			JSONObject json = JSONObject.fromObject(params);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setJsonInfo(params);
			message = writeBackServiceBus.crawlerRecallMethod(brpv);
			logger.info("[END]订单中心回调爬虫:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("订单中心回调爬虫异常");
			logger.info("[ERR]订单中心回调爬虫:"+e.getMessage());
			return message;
		}
	}
	
	/**
	 * 订单回调接口
	 * @param json_info
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.UPDATEORDERSTATUS , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage updateOrderStatus(String json_info){
		
		BusRespMessage message = new BusRespMessage();
		logger.info("[BEG]订单中心回调爬虫 params："+json_info);
		
		try {
			logger.info("[BEG]得到数据："+json_info);
			if(json_info == null){
				throw new Exception("参数不能为空");
			}
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setUser(json.optString("user",""));
			brpv.setPassword(json.optString("pwd",""));
			brpv.setOrder_id(json.optString("orderid",""));
			//状态
			brpv.setOther(json.optString("orderstatus",""));
			message = writeBackServiceBus.updateOrderStatusMethod(brpv);
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
