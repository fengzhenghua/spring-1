package com.tydic.unicom.apc.rest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.apc.business.pub.interfaces.OppoPayServiceServDu;
import com.tydic.unicom.apc.business.pub.vo.WXPayRestVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.OPPO_PAY_SERVICE_REST)
public class OppoPayServiceRest {

	private static Logger logger = Logger.getLogger(OppoPayServiceRest.class);
	
	@Autowired
	private OppoPayServiceServDu oppoPayServiceServDu;
	
	@RequestMapping(value = UrlsMappings.GET_WX_QR_CODE,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage getWxQrCode(WXPayRestVo reqVo,String oper_no){
		logger.info("==============生成微信二维码===============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoPayServiceServDu.getWxQrCode(reqVo, oper_no);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("生成微信二维码异常");
			return uocMessage;
		}	
	}
	
	
	/**
	 * 微信扫码支付通知
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value = UrlsMappings.WX_SCAN_NOTIFY,method=RequestMethod.POST)
	@ResponseBody
	public void wxpayScanNotify(HttpServletRequest req,HttpServletResponse resp){
		logger.info("微信扫码异步通知------------------------------------------");
		OutputStream out = null;
		try{
			out = resp.getOutputStream();
		//更新日志表已支付金额、支付状态等信息
		InputStream inStream = req.getInputStream();
		logger.info("微信支付回调==="+req+"------"+inStream);
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
		    outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		String resString  = new String(outSteam.toByteArray(),"utf-8");
		boolean flag = oppoPayServiceServDu.getWxCallBack(resString);
		
		//返回结果给微信
		String result = "SUCCESS";
		String msg = "OK";
		if(!flag){
			result = "FAIL";
			msg = "ERROR";
		}
		
		String responseStr = "<xml>"
		  +"<return_code><![CDATA["+result+"]]></return_code>"
		  +"<return_msg><![CDATA["+msg+"]]></return_msg>"
		  +"</xml>";
		
		out.write(responseStr.getBytes());
		}catch(Exception e){
			logger.error("更新日志表失败！");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 微信支付结果轮询
	 */
	@RequestMapping(value = UrlsMappings.GET_PAY_RESULT,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage getPayResult(String order_id,String oper_no){
		logger.info("==============微信支付结果轮询===============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoPayServiceServDu.getPayResult(order_id, oper_no);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("微信支付结果轮询异常");
			return uocMessage;
		}	
	}
	
	
	/**
	 * 微信退款
	 */
	@RequestMapping(value = UrlsMappings.WX_PAY_REFUND,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage wxpayRefund(String order_id,String oper_no){
		logger.info("==============微信退款===============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoPayServiceServDu.wxpayRefund(order_id, oper_no);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("微信退款异常");
			return uocMessage;
		}	
	}
	
	
}
