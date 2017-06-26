package com.tydic.unicom.uoc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.common.interfaces.StatisticalTaskOverdueRateServDu;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.ServiceOrderServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.STATISTICAL_SERVICE)
public class StatisticalServiceRest {

	private static Logger logger = Logger.getLogger(StatisticalServiceRest.class);

	@Autowired
	private StatisticalTaskOverdueRateServDu statisticalTaskOverdueRateServDu;
	@Autowired
	private ServiceOrderServDu serviceOrderServDu;

	@RequestMapping(value = UrlsMappings.GET_STATISTICAL_TASK_OVERDUE_RATE_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getStatisticalTaskOverdueRateInfo(ParaVo paraVo){
		logger.info("===========任务逾期率统计(rest)=============");

		UocMessage uocMessage =new UocMessage();
		try {
			uocMessage = statisticalTaskOverdueRateServDu.getStatisticalTaskOverdueRate(paraVo);
		}catch (UocException e){
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("任务逾期率统计异常");
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 服务订单竣工率统计 UOC0038
	 */
	@RequestMapping(value = UrlsMappings.GET_SERV_ORDER_COMPLETION_RATE, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getServiceOrderCompletionRate(ParaVo paraVo) {
		logger.info("===========服务订单竣工率统计(rest)=============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = serviceOrderServDu.getServiceOrderCompletionRate(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("订单竣工率统计异常");
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 服务订单撤单率统计 UOC0053
	 */
	@RequestMapping(value = UrlsMappings.GET_SERV_ORDER_CANCEL_RATE, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getServiceOrderCancelRate(ParaVo paraVo) {
		logger.info("===========服务订单撤单率统计(rest)=============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = serviceOrderServDu.getServiceOrderCancelRate(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("订单撤单率统计异常");
			return uocMessage;
		}
		return uocMessage;
	}
}
