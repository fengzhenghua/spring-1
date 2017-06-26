package com.tydic.unicom.uoc.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderCancelApplyServDu;
import com.tydic.unicom.uoc.business.order.service.vo.OrderCancelApplyVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;
@Controller
@RequestMapping(value = ControllerMappings.ORDER_CANCEL_APPLY)
public class OrderCancelApplyRest {

	@Autowired
	private OrderCancelApplyServDu orderCancelApplyServDu;

	@RequestMapping(value = UrlsMappings.GET_ORDER_CANCEL_APPLY , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOrderCancelApply(OrderCancelApplyVo vo){
		UocMessage message=new UocMessage();
		try{
			message=orderCancelApplyServDu.createOrderCancelApply(vo);
			return message;
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("订单撤销申请失败");
			return message;
		}
	}

	/*
	 * 订单撤销审核 UOC0058
	 */
	@RequestMapping(value = UrlsMappings.CHECK_INFO_ORDER_CANCEL, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage checkInfoOrderCancel(ParaVo paravo) {
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = orderCancelApplyServDu.checkInfoOrderCancel(paravo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("订单撤销审核异常");
			return uocMessage;
		}
		return uocMessage;
	}
}
