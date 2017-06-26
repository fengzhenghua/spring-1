package com.tydic.unicom.apc.business.pub.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.apc.base.order.interfaces.LogPayCsServ;
import com.tydic.unicom.apc.base.order.po.LogPayCsPo;
import com.tydic.unicom.apc.business.common.interfaces.IApcServiceHandler;
import com.tydic.unicom.apc.business.pub.interfaces.WxpayRefundServDu;
import com.tydic.unicom.apc.common.wxpay.RefundResData;
import com.tydic.unicom.apc.common.wxpay.WXPayUtilsServ;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.apc.service.order.interfaces.LogPayCsServDu;
import com.tydic.unicom.exception.BusinessException;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class WxpayRefundServDuImpl implements WxpayRefundServDu,IApcServiceHandler {

	Logger logger = Logger.getLogger(WxpayRefundServDuImpl.class);
	
	@Autowired 
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;
	@Autowired 
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	private LogPayCsServ logPayCsServ;
	@Autowired
	private WXPayUtilsServ wXPayUtilsServ;
	@Autowired
	private LogPayCsServDu logPayCsServDu;
	
	@Override
	public UocMessage createWxpayRefund(String jsession_id, String serv_order_no){
		logger.info("wxpayRefund-微信退款------------------------------------------");
		UocMessage message =new UocMessage();
		if( StringUtils.isEmpty(jsession_id)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jsession_id不能为空！");
			return message;
		}
		
		if( StringUtils.isEmpty(serv_order_no)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("serv_order_no不能为空！");
			return message;
		}
		
		try{
			String oper_no="";
			Map<String,Object> oper_info =new HashMap<String,Object>();
			/*
			 * 调用UAC0005校验登录
			 */
			String json_info = "{\"SERVICE_NAME\":\"getBaseOperInfo\",\"param\":{\"jsession_id\":\"" + jsession_id + "\"}}";
			UocMessage res = apcAbilityPlatformServDu.CallApcAbilityPlatform(json_info, "", "UAC");
			logger.info("wxpayRefund-微信退款------------校验该工号是否登录-----------------------");
			if (res != null) {
				String code = (String) res.getArgs().get("code");
				if (code != null && "200".equals(code)) {
					String json_info_out = (String) res.getArgs().get("json_info");
					Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info_out);
					oper_info = (Map<String, Object>) map.get("oper_info");
					if (oper_info == null) {
						logger.info("----------无对应工号信息----------");
						message.setRespCode(RespCodeContents.PARAM_ERROR);
						message.setContent("无对应工号信息");
						return message;
					}else {
						oper_no = (String) oper_info.get("oper_no");
					}
					logger.info("----------oper_info----------" + oper_info.toString());
				} else {
					logger.info("----------能力平台调用失败----------");
					message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
					return res;
				}
			}
			
			//查看支付日志
			LogPayCsPo logPayCsPo = new LogPayCsPo();
			logPayCsPo.setServ_order_no(serv_order_no);
			logPayCsPo =logPayCsServ.queryLogPayCsByservOrderNo(serv_order_no);
			if(logPayCsPo!=null&&logPayCsPo.getFlag().equals("1") ){
				logger.info("退款，从表中取到的标志ap_id===="+logPayCsPo.getMcht_flag());
				RefundResData data =wXPayUtilsServ.refund(logPayCsPo.getDevice_number(), logPayCsPo.getOper_sn(), "", logPayCsPo.getPay_cs_id(),  (int)(Float.parseFloat(logPayCsPo.getTotal_fee())*100),  (int)(Float.parseFloat(logPayCsPo.getPayed_fee())*100), oper_no,logPayCsPo.getMcht_flag());
				logger.info("微信退款结果result++++++++"+data.getResult_code()+"++++++++++++");
				
				if("SUCCESS".equals(data.getResult_code())){
					//退款成功后更新支付日志表
					logPayCsPo.setFlag("-1");
					//2016-11-22起不再修改原纪录状态，新增修改记录
					if( !StringUtils.isEmpty( logPayCsPo.getTotal_fee() ) ){
						logPayCsPo.setTotal_fee( "-"+logPayCsPo.getTotal_fee() );
					}
					if( !StringUtils.isEmpty( logPayCsPo.getPayed_fee() )  ){
						logPayCsPo.setPayed_fee( "-"+logPayCsPo.getPayed_fee() );
					}
					logPayCsServ.insertLogPayCs(logPayCsPo);
					
					/*LogPayCsVo logPayCsVo=(LogPayCsVo) ParaTool.copyProperties(logPayCsPo, LogPayCsVo.class);
					//logPayCsServDu.insertLogPayCs(logPayCsVo);
					logPayCsServDu.updateLogPayCs(logPayCsVo);*/
					
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("退款成功！");
					return message;	
				}else{
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("退款失败！");
					return message;
					
				}
				
				
			  }else {
				  message.setRespCode(RespCodeContents.SUCCESS);
				  message.setContent("无支付成功数据，不需退款！");
				  return message;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("退款出现异常！");
			return message;
		}
	}

	
	@Override
	public UocMessage getMessage(String json_in, String method_name) throws BusinessException {
		
		UocMessage message=new UocMessage();
		Map<String, Object> map=null;
		try {
			map = jsonToBeanServDu.jsonToMap(json_in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw (BusinessException) e;
		}
		if("createWxpayRefund".equals(method_name)){
			String jsession_id="";
			String serv_order_no="";
			if(map.containsKey("jsession_id")){
				jsession_id=(String) map.get("jsession_id");
			}
			if(map.containsKey("serv_order_no")){
				serv_order_no=(String) map.get("serv_order_no");
			}
			
			message=createWxpayRefund(jsession_id,serv_order_no);
			
		}else{
			message.setContent("方法名错误");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			return message;
		}
		return message;
	}
	
}
