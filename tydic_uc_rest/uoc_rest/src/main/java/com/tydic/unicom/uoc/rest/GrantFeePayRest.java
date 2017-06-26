package com.tydic.unicom.uoc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.common.interfaces.AopOrderHalfYearPkgServDu;
import com.tydic.unicom.uoc.business.common.vo.AopHalfYearPkgVo;
import com.tydic.unicom.uoc.business.order.pay.interfaces.GrantFeePayServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.GRANT_FEE_PAY_REST)
public class GrantFeePayRest {
	Logger logger = Logger.getLogger(GrantFeePayRest.class);

	@Autowired
	private GrantFeePayServDu grantFeePayServDu;
	@Autowired
	private AopOrderHalfYearPkgServDu  aopOrderHalfYearPkgServDu;

	/*
	 * AOP赠款 UOC0080
	 */
	@RequestMapping(value = UrlsMappings.PROM_FEE_FOR_AOP, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage promFeeForAop(String jsession_id, String serv_order_no, String prom_fee, String acc_nbr) {
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = grantFeePayServDu.promFeeForAop(jsession_id, serv_order_no, prom_fee, acc_nbr);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("调用AOP赠款系统异常");
			return uocMessage;
		}
	}
	/*
	 * AOP订购半年包
	 */
	@RequestMapping(value = UrlsMappings.GET_AOP_ORDER_HALF_YEAR_PKG, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getAopOrderHalfYearPkg(AopHalfYearPkgVo vo) {
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = aopOrderHalfYearPkgServDu.getAopHalfYearPkg(vo);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("AOP订购半年包异常");
			return uocMessage;
		}
	}

}
