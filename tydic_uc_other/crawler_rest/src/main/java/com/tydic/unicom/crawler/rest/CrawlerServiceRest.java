package com.tydic.unicom.crawler.rest;

import org.apache.log4j.Logger;
import org.jboss.netty.util.internal.SystemPropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crawler.business.interfaces.CrawlerServiceBus;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = ControllerMappings.CRAWLER_SERVICE_REST)
public class CrawlerServiceRest {

	private static Logger logger = Logger.getLogger(CrawlerServiceRest.class);
	
	@Autowired
	private CrawlerServiceBus crawlerServiceBus;
	
	@RequestMapping(value = UrlsMappings.CRAWLER_MANUAL_ACCOUNT , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage crawlerManualAccount(String json_info){
		BusRespMessage message = new BusRespMessage();
		try {
			logger.info("[BEG]得到爬取参数："+json_info);
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setUser(json.getString("user"));
			brpv.setPassword(json.getString("pwd"));
			message = crawlerServiceBus.crawlerManualAccountMethod(brpv);
			logger.info("[END]抓取手工开户#:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("抓取手工开户异常");
			logger.info("[ERR]抓取手工开户异常:"+e.getMessage());
			logger.debug("[ERR]抓取手工开户异常:",e);
			return message;
		}
	}
	
	@RequestMapping(value = UrlsMappings.CRAWLER_MANUAL_ACCOUNT_CREATEORDER , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage crawlerManualAccountToUocOrder(String json_info){
		BusRespMessage message = new BusRespMessage();
		try {
			logger.info("[BEG]得到爬取参数："+json_info);
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setUser(json.getString("user"));
			brpv.setPassword(json.getString("pwd"));
			message = crawlerServiceBus.crawlerManualAccountCreateOrder(brpv);
			logger.info("[END]创建订单#OK#:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("创建订单异常");
			logger.info("[ERR]创建订单异常:"+e.getMessage());
			logger.debug("[ERR]创建订单异常:",e);
			return message;
		}
	}
	
	
	@RequestMapping(value = UrlsMappings.GETSKULIST , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage getSKUList(String json_info){
		BusRespMessage message = new BusRespMessage();
		try {
			logger.info("[BEG]得到商品列表参数："+json_info);
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			if(!json.optString("insertskuname","").equals(""))
				brpv.addParmap("name", json.getString("insertskuname"));
			if(!json.optString("insertskuid","").equals(""))
				brpv.addParmap("id", json.getString("insertskuid"));
			if(!json.optString("insertskustats","").equals(""))
				brpv.addParmap("stats", json.getString("insertskustats"));
			if(!json.optString("insertopercode","").equals(""))
				brpv.addParmap("opercode", json.getString("insertopercode"));
			
			message = crawlerServiceBus.selectSKU(brpv);
			logger.info("[END]得到商品列表#OK#:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("得到商品列表异常");
			logger.info("[ERR]得到商品列表异常:"+e.getMessage());
			logger.debug("[ERR]得到商品列表异常:",e);
			return message;
		}
	}
	
	@RequestMapping(value = UrlsMappings.INSSKU , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage insSKU(String json_info){
		BusRespMessage message = new BusRespMessage();
		try {
			logger.info("[BEG]创建商品信息参数："+json_info);
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			
			
			if(json.optString("insertskuname","").equals("")
					||json.optString("insertskuid","").equals("")
					||json.optString("insertskustats","").equals("")
					){
				message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
				message.setContent("更新商品信息异常");
				return message;
			}
			brpv.addParmap("insertskuname", json.getString("insertskuname"));
			brpv.addParmap("insertskuid", json.getString("insertskuid"));
			brpv.addParmap("insertskustats", json.getString("insertskustats"));
			brpv.addParmap("insertopercode", json.optString("insertopercode",""));
			//默认是本地 4g开户
			if(json.optString("insertopercode","").equals("")){
				brpv.addParmap("insertopercode", "openLocal4G");
			}
			
			
			message = crawlerServiceBus.createSKU(brpv);
			logger.info("[END]创建商品信息#OK#:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("创建商品信息异常:"+e.getMessage());
			logger.info("[ERR]创建商品信息异常:"+e.getMessage());
			logger.debug("[ERR]创建商品信息异常:",e);
			return message;
		}
	}
	
	@RequestMapping(value = UrlsMappings.UPDATESKU , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage updateSKU(String json_info){
		BusRespMessage message = new BusRespMessage();
		try {
			logger.info("[BEG]更新商品信息："+json_info);
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			
			if(json.optString("updateskuuuid","").equals("") || json.optString("updateskuname","").equals("")
					||json.optString("updateskuid","").equals("")
					||json.optString("updateskustats","").equals("")
					||json.optString("updateopercode","").equals("")
					){
				message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
				message.setContent("更新商品信息异常,请确认参数传递正确！");
				return message;
			}
			brpv.addParmap("updateskuuuid", json.getString("updateskuuuid"));
			brpv.addParmap("updateskuname", json.getString("updateskuname"));
			brpv.addParmap("updateskuid", json.getString("updateskuid"));
			brpv.addParmap("updateskustats", json.getString("updateskustats"));
			brpv.addParmap("updateopercode", json.getString("updateopercode"));
			
			message = crawlerServiceBus.updateSKU(brpv);
			logger.info("[END]更新商品信息#OK#:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("更新商品信息异常");
			logger.info("[ERR]更新商品信息异常:"+e.getMessage());
			logger.debug("[ERR]更新商品信息异常:",e);
			return message;
		}
	}
	
}
