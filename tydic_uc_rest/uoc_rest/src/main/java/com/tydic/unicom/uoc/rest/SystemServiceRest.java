package com.tydic.unicom.uoc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisInitServ;
import com.tydic.unicom.uoc.business.common.interfaces.EsReportDataServDu;
import com.tydic.unicom.uoc.business.common.interfaces.SystemServiceServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;
import com.tydic.unicom.uoc.business.common.vo.LogisticsReportVo;
import com.tydic.unicom.uoc.business.common.vo.ServOrderListESInVo;

@Controller
@RequestMapping(value = ControllerMappings.SYSTEM_SERVICE_REST)
public class SystemServiceRest {

	private static Logger logger = Logger.getLogger(SystemServiceRest.class);
	
	@Autowired
	private RedisInitServ redisInitServ;
	@Autowired
	private EsReportDataServDu esReportDataServDu;
	
	@Autowired
	private SystemServiceServDu systemServiceServDu;
		
	@RequestMapping(value = UrlsMappings.LOGIN_OUT , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage loginOut(String jsession_id){
		logger.debug("==============退出（rest）============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = systemServiceServDu.loginOutMethod(jsession_id);
		}catch (UocException e){
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("退出登录异常");
			return uocMessage;
		}
		return uocMessage;		
	}
	
	@RequestMapping(value = UrlsMappings.REFRESH_REDIS_ALL , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage refreshRedisAll(){
		boolean result = false;
		UocMessage uocMessage = new UocMessage();
		try{
			logger.info("=============刷新redis==============");
			logger.info("==============删除redis数据================");
			result = redisInitServ.initDeleteDataFromRedis();
			result = redisInitServ.deleteStaticDataToRedis();
			logger.info("==============写入redis数据================");
			result = redisInitServ.initCreateDataToRedis();
			result = redisInitServ.createStaticDataToRedis();
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
	
	@RequestMapping(value = UrlsMappings.CREATE_LOGISTICS_REPORT_DATA , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage createLogisticsReportData(){
		UocMessage uocMessage = new UocMessage();
		try{
			uocMessage=esReportDataServDu.createLogisticsDetailData();
		}catch(Exception e){
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("物流明细报表中间数据生成异常");
			return uocMessage;
		}
		return uocMessage;
	}
	
	@RequestMapping(value = UrlsMappings.QUERY_LOGISTICS_REPORT_DATA, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage queryLogisticsReportData(LogisticsReportVo vo){
		UocMessage uocMessage = new UocMessage();
		try{
			uocMessage=esReportDataServDu.queryLogisticsDetailData(vo);
		}catch(Exception e){
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("物流明细报表查询异常");
			return uocMessage;
		}
		return uocMessage;
	}
	/**
	 *  服务订单列表查询 (ES版) UOC0074
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.GET_SERV_ORDER_LIST_ES, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getServOrderListES(ServOrderListESInVo  vo){
		UocMessage uocMessage = new UocMessage();
		try{
			uocMessage=esReportDataServDu.queryServOrderListES(vo);
		}catch(Exception e){
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("服务订单列表查询异常");
			return uocMessage;
		}
		return uocMessage;
	}
	/**
	 * 服务订单详情查询(ES版) UOC0075
	 */
	@RequestMapping(value = UrlsMappings.GET_ODER_DETAIL_ES, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOrderDetailES(String jsession_id,String order_no){
		UocMessage uocMessage = new UocMessage();
		try{
			uocMessage=esReportDataServDu.queryOrderDetailES(jsession_id, order_no);
		}catch(Exception e){
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("服务订单详情查询异常");
			return uocMessage;
		}
		return uocMessage;
	}
	/**
	 * 订单明细报表查询 UOC0076
	 */
	@RequestMapping(value = UrlsMappings.GET_ODER_DETAIL_REPORT, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOrderDetailReport(LogisticsReportVo vo){
		UocMessage uocMessage = new UocMessage();
		try{
			uocMessage=esReportDataServDu.queryOrderDetailReport(vo);
		}catch(Exception e){
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("订单明细报表查询异常");
			return uocMessage;
		}
		return uocMessage;
	}
}
