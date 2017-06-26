package com.tydic.unicom.upc.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.tools.ResouresUtil;
import com.tydic.unicom.service.sequence.service.interfaces.SequenceServ;
import com.tydic.unicom.upc.alipay.model.ExtendParams;
import com.tydic.unicom.upc.alipay.model.GoodsDetail;
import com.tydic.unicom.upc.service.activemq.interfaces.CustNotifyActivemqServDu;
import com.tydic.unicom.upc.service.code.interfaces.InfoBusiServDu;
import com.tydic.unicom.upc.service.code.interfaces.InfoPayParaAttrServDu;
import com.tydic.unicom.upc.service.inst.interfaces.InfoOrderServDu;
import com.tydic.unicom.upc.service.inst.interfaces.OrderPayTransServDu;
import com.tydic.unicom.upc.service.pay.interfaces.AliPayServDu;
import com.tydic.unicom.upc.service.pay.interfaces.WXPayServDu;
import com.tydic.unicom.upc.vo.code.InfoBusiVo;
import com.tydic.unicom.upc.vo.code.InfoPayParaAttrValueVo;
import com.tydic.unicom.upc.vo.inst.InfoOrderVo;
import com.tydic.unicom.upc.vo.inst.OrderPayTransVo;
import com.tydic.unicom.upc.web.constants.ControllerMappings;
import com.tydic.unicom.upc.web.vo.UpcWebPropertiesVo;
import com.tydic.unicom.upc.wxpay.protocol.unified_order_protocol.UnifiedOrderResData;
import com.tydic.unicom.util.HttpUtil;
import com.tydic.unicom.util.RsaEncodeUtil;
import com.tydic.unicom.util.UpcEncodeUtil;
import com.tydic.unicom.webUtil.Message;
import com.tydic.unicom.webUtil.UocMessage;
import com.tydic.unicom.webUtil.Message.Type;

/**
 * 支付控制
 * 主要是用来对支付机构发起支付请求
 * 以及获取通知地址和发起通知消息
 * @author 吴川
 *
 */
@Controller
@RequestMapping(value = ControllerMappings.PAY_CONTROLLER)
public class PayController {

	private static Logger logger = Logger.getLogger(PayController.class);
	@Autowired
	private InfoBusiServDu infoBusiServDu;
	
	@Autowired
	private InfoOrderServDu infoOrderServDu;
	
	@Autowired
	private WXPayServDu wxPayService;
	
	@Autowired
	private AliPayServDu aliPayService;
	
	@Autowired
	private InfoPayParaAttrServDu infoPayParaAttrServDu;
	
	@Autowired
	private OrderPayTransServDu orderPayTransServDu;
	
	@Autowired
	private CustNotifyActivemqServDu custNotifyActivemqServDu;
	
	@Autowired
	private UpcWebPropertiesVo upcWebPropertiesVo;
	
	@RequestMapping(value = "queryPayOrder" , method = RequestMethod.POST)
	@ResponseBody
	public Message queryPayOrder(String order_id, String key, String pay_order_id){
		Message message = new Message();
		
		try{
			if(!validateKey(order_id, key)){
				message.setType(Type.error);
				message.setContent("订单数据非法，请重新发起支付请求。!");
				return message;
			}
			
			//获取支付记录
			OrderPayTransVo orderPayTransVo = new OrderPayTransVo();
			orderPayTransVo.setPay_order_id(pay_order_id);
			orderPayTransVo = orderPayTransServDu.queryByPayOrderId(orderPayTransVo);
			if(orderPayTransVo == null){
				message.setType(Type.error);
				message.setContent("找不到对应的支付订单号数据!");
				return message;
			}
			
			//支付成功
			if(orderPayTransVo.getOrder_status().equals("A10")){
				message.setType(Type.success);
				message.addArg("result", "SUCCESS");
			}
			//支付失败
			else if(orderPayTransVo.getOrder_status().equals("A20")){
				message.setType(Type.success);
				logger.info("支付失败!");
				message.addArg("result", "FAIL");
				String pay_msg = orderPayTransVo.getPay_msg();
				if(pay_msg == null || pay_msg.equals("")){
					pay_msg = "请重新输入条码串号进行支付!";
				}
				message.addArg("msg", pay_msg);
			}
			//支付中
			else if(orderPayTransVo.getOrder_status().equals("A00")){
				message.setType(Type.success);
				message.addArg("result", "PAYING");
			}
			else{
				message.setType(Type.error);
				message.setContent("支付查询失败，订单状态异常!");
			}
			
		}catch(Exception e){
			logger.error("支付结果查询失败!"+e.getMessage(), e);
			
			message.setType(Type.error);
			message.setContent("支付结果查询失败!"+e.getMessage());
		}
		
		return message;
	}
	
	
	@RequestMapping(value = "scanPay" , method = RequestMethod.POST)
	@ResponseBody
	public Message scanPay(String order_id, String auth_code, String pay_type, String key){
		//支付请求安全， 还需要再考虑一下，第一时间段先不管了
		
		Message message = new Message();
		
		try{
			
			if(!validateKey(order_id, key)){
				message.setType(Type.error);
				message.setContent("订单数据非法，请重新发起支付请求。!");
				return message;
			}
		
			//条码支付，串号是18位长度
			if(auth_code == null || auth_code.length() != 18){
				message.setType(Type.error);
				message.setContent("条码串号输入不正确，请重新输入!");
				return message;
			}
			
			//微信和支付宝， 条码的串号是不同的， 如果没有传支付类型进来，那么通过串号来确定
			//10、11、12、13、14、15是微信
			if(pay_type == null || pay_type.equals("")){
				if(auth_code.startsWith("10") || auth_code.startsWith("11") || auth_code.startsWith("12") 
						|| auth_code.startsWith("13") || auth_code.startsWith("14") || auth_code.startsWith("15")){
					pay_type = "10";
				}
				else{
					pay_type = "20";
				}
			}
			
			//获取订单数据
			InfoOrderVo infoOrderVo = new InfoOrderVo();
			infoOrderVo.setOrder_id(order_id);
			infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
			if(infoOrderVo == null){
				message.setType(Type.error);
				message.setContent("找不到对应的支付订单号数据!");
				return message;
			}
			
			String busi_id = infoOrderVo.getBusi_id();
			
			InfoPayParaAttrValueVo attrValueVo = infoPayParaAttrServDu.getPayParaValueByPayType(busi_id, pay_type);
			if(attrValueVo == null){
				message.setType(Type.error);
				message.setContent("找不到对应的支付类型参数配置数据!");
				return message;
			}
			
			
			//发起支付请求
			if(pay_type.equals("10")){
				String detail = "";
				int totalFee = infoOrderVo.getReal_fee().multiply(new BigDecimal(100)).intValue();
				String deviceInfo = "88888888";
				String attach = "";
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		    	
		    	Calendar cal = Calendar.getInstance();
		    	cal.setTime(new Date());
		    	cal.add(Calendar.MINUTE, 3);		//默认三分钟失效
		    	
		    	String timeStart = sdf.format(new Date());
		    	String timeExpire = sdf.format(cal.getTime());
		    	logger.info("微信请求时间为" + timeStart);
		    	
		    	
		    	//创建支付记录
		    	String outTradeNo = createPayTrans(order_id, pay_type);
				
				wxPayService.paymentScanPay(attrValueVo.getAppid(), attrValueVo.getMchid(), attrValueVo.getSignkey(), 
						auth_code, infoOrderVo.getOrder_id(),infoOrderVo.getDetail_name(), detail, outTradeNo, 
						totalFee, deviceInfo, infoOrderVo.getCreate_ip_address(), timeStart, timeExpire, attach, pay_type);
				
				message.addArg("pay_order_id", outTradeNo);
				message.setType(Type.success);
				return message;
			}
			else{
				
				String totalAmount = infoOrderVo.getReal_fee().setScale(2).toString();
				String undiscountableAmount = "0.00";
				String sellerId = "";
				String body = infoOrderVo.getDetail_name();
				String operatorId = busi_id;
				String storeId = busi_id;
				
				ExtendParams extendParams = null;
				String timeoutExpress = "3m";		//超时时间
				List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
				
				String outTradeNo = createPayTrans(order_id, pay_type);
				
				aliPayService.paymentScanPay(attrValueVo.getAppid(), attrValueVo.getPrivateKey(), attrValueVo.getPublicKey(), outTradeNo,
						infoOrderVo.getOrder_id(), infoOrderVo.getDetail_name(), totalAmount, auth_code, 
						undiscountableAmount, sellerId, body, operatorId, storeId, extendParams, timeoutExpress, goodsDetailList, pay_type);
				
				message.addArg("pay_order_id", outTradeNo);
				message.setType(Type.success);
				return message;
			}
		}catch(Exception e){
			logger.error("条码支付失败!"+e.getMessage(), e);
			
			message.setType(Type.error);
			message.setContent("条码支付失败!"+e.getMessage());
		}
		
		return message;
	}
	
	
	/**
	 * 微信生成二维码
	 * @param order_id
	 * @param auth_code
	 * @param pay_type
	 * @return
	 */
	@RequestMapping(value = "wxQrCodePay" , method = RequestMethod.POST)
	@ResponseBody
	public Message wxQrCodePay(String order_id, String pay_type, String key){
		Message message = new Message();
		try{
			
			if(!validateKey(order_id, key)){
				message.setType(Type.error);
				message.setContent("订单数据非法，请重新发起支付请求。!");
				return message;
			}
			
			if(pay_type == null || pay_type.equals("")){
				message.setType(Type.error);
				message.setContent("pay_type参数必须传值");
				return message;
			}
			else{
				//获取订单数据
				InfoOrderVo infoOrderVo = new InfoOrderVo();
				infoOrderVo.setOrder_id(order_id);
				infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
				if(infoOrderVo == null){
					message.setType(Type.error);
					message.setContent("找不到对应的支付订单号数据!");
					return message;
				}
				
				String busi_id = infoOrderVo.getBusi_id();
				
				InfoPayParaAttrValueVo attrValueVo = infoPayParaAttrServDu.getPayParaValueByPayType(busi_id, pay_type);
				if(attrValueVo == null){
					message.setType(Type.error);
					message.setContent("找不到对应的支付类型参数配置数据!");
					return message;
				}
				
				String detail = "";
				int totalFee = infoOrderVo.getReal_fee().multiply(new BigDecimal(100)).intValue();
				String deviceInfo = "88888888";
				String trade_type = "NATIVE";
				int expireTime = 2;		//超时时间
				
				String productId = "PID"+infoOrderVo.getOrder_id();
				
				String notifyUrl = upcWebPropertiesVo.getWx_notify_url();
				
				String outTradeNo = createPayTrans(order_id, pay_type);
				
				Map<String, String> resultMap = wxPayService.qrCodePay(attrValueVo.getAppid(), attrValueVo.getMchid(), attrValueVo.getSignkey(), 
						infoOrderVo.getDetail_name(), detail, outTradeNo, totalFee, 
						deviceInfo, infoOrderVo.getCreate_ip_address(), expireTime, trade_type, notifyUrl, productId);
				
				String content = resultMap.get("code_url");
    			
				message.addArg("pay_order_id", outTradeNo);
    			message.addArg("qrData", content);
    			
				message.setType(Type.success);
				return message;
				
			}
		}catch(Exception e){
			logger.error("微信扫码支付失败!"+e.getMessage(), e);
			
			message.setType(Type.error);
			message.setContent("微信扫码支付失败!"+e.getMessage());
		}
		
		return message;
	}
	
	/**
	 * 现金支付
	 */
	@RequestMapping(value = "cashPay" , method = RequestMethod.POST)
	@ResponseBody
	public Message cashPay(String order_id, String key, String pay_type){
		Message message = new Message();
		try{
			if(!validateKey(order_id, key)){
				message.setType(Type.error);
				message.setContent("订单数据非法，请重新发起支付请求。!");
				return message;
			}
			if(pay_type == null || pay_type.equals("")){
				message.setType(Type.error);
				message.setContent("pay_type参数必须传值");
				return message;
			}else{
				InfoOrderVo infoOrderVo = new InfoOrderVo();
				infoOrderVo.setOrder_id(order_id);
				infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
				if(infoOrderVo == null){
					message.setType(Type.error);
					message.setContent("找不到对应的支付订单号数据!");
					return message;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				infoOrderVo.setPay_notify_time(new Date());
				infoOrderVo.setPay_notify_code("0");
				infoOrderVo.setOrder_status("A10");
				infoOrderVo.setCust_notify_code("-");
			    infoOrderVo.setPay_type(pay_type);
			    infoOrderVo.setTrade_time(sdf.format(new Date()));
			    infoOrderServDu.updateInfoOrder(infoOrderVo);
			    
				//发起通知回调
				logger.info("发起消息队列"+order_id);
				UocMessage msg = new UocMessage();
				msg.addArg("order_id", order_id);
				int result = custNotifyActivemqServDu.sendMessage(msg);
				if(result == 0){
					logger.info("发起消息队列"+order_id+", 成功!");
				}
				else{
					logger.error("发起消息队列"+order_id+", 失败!"+result);
				}
			    
			    message.setType(Type.success);
				message.setContent("现金收费成功");
				
			}
		}catch(Exception e){
			
			logger.error("现金写订单失败!"+e.getMessage(), e);
			
			message.setType(Type.error);
			message.setContent("现金支付失败!"+e.getMessage());
		}
		return message;
	}
	
	/**
	 * 支付宝生成二维码
	 * @param order_id
	 * @param auth_code
	 * @param pay_type
	 * @return
	 */
	@RequestMapping(value = "aliQrCodePay" , method = RequestMethod.POST)
	@ResponseBody
	public Message aliQrCodePay(String order_id, String pay_type, String key){
		Message message = new Message();
		try{
			if(!validateKey(order_id, key)){
				message.setType(Type.error);
				message.setContent("订单数据非法，请重新发起支付请求。!");
				return message;
			}
			
			if(pay_type == null || pay_type.equals("")){
				message.setType(Type.error);
				message.setContent("pay_type参数必须传值");
				return message;
			}
			else{
				//获取订单数据
				InfoOrderVo infoOrderVo = new InfoOrderVo();
				infoOrderVo.setOrder_id(order_id);
				infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
				if(infoOrderVo == null){
					message.setType(Type.error);
					message.setContent("找不到对应的支付订单号数据!");
					return message;
				}
				
				String busi_id = infoOrderVo.getBusi_id();
				
				InfoPayParaAttrValueVo attrValueVo = infoPayParaAttrServDu.getPayParaValueByPayType(busi_id, pay_type);
				if(attrValueVo == null){
					message.setType(Type.error);
					message.setContent("找不到对应的支付类型参数配置数据!");
					return message;
				}
				
				String totalAmount = infoOrderVo.getReal_fee().setScale(2).toString();
				String undiscountableAmount = "0.00";
				String sellerId = "";
				String body = infoOrderVo.getDetail_name();
				String operatorId = busi_id;
				String storeId = busi_id;
				
				ExtendParams extendParams = null;
				String timeoutExpress = "2m";		//超时时间
				List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
				
				String notify_url = upcWebPropertiesVo.getAlipay_notify_url();
				
				String outTradeNo = createPayTrans(order_id, pay_type);
				
				Map<String, String> map = aliPayService.qrCodePay(attrValueVo.getAppid(), attrValueVo.getPrivateKey(), attrValueVo.getPublicKey(), 
						outTradeNo, infoOrderVo.getDetail_name(), totalAmount, notify_url, 
						undiscountableAmount, sellerId, body, operatorId, storeId, extendParams, timeoutExpress, goodsDetailList);
				
				String code = map.get("code");
				if(code.equals("10000")){
					message.addArg("qrData", map.get("qr_code"));
					
					message.addArg("pay_order_id", outTradeNo);
					
					message.setType(Type.success);
				}
				else{
					message.setType(Type.error);
					message.setContent("支付失败："+map.get("msg"));
				}
				return message;
				
			}
		}catch(Exception e){
			logger.error("支付宝扫码支付失败!"+e.getMessage(), e);
			
			message.setType(Type.error);
			message.setContent("支付宝扫码支付失败!"+e.getMessage());
		}
		
		return message;
	}
	
	
	@RequestMapping(value = "wxQrPayNotify",method=RequestMethod.POST)
	@ResponseBody
	public void wxQrPayNotify(HttpServletRequest request,HttpServletResponse resp){
		logger.info("微信扫码异步通知------------------------------------------");
		OutputStream out = null;
		try{
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
			    outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			String reqString  = new String(outSteam.toByteArray(),"utf-8");
			
	        
	        logger.info("----接收到notify_url的返回，内容为：----------");
	        logger.info(reqString);
	        
	        boolean flag = wxPayService.saveQrPayNotify(reqString);
		
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
			out = resp.getOutputStream();
			out.write(responseStr.getBytes());
		}catch(Exception e){
			logger.error("微信扫码异步通知处理失败！"+e.getMessage(), e);
		}
	}
	
	
	@RequestMapping(value = "aliQrPayNotify",method=RequestMethod.POST)
	@ResponseBody
	public void aliQrPayNotify(HttpServletRequest request,HttpServletResponse resp){
		logger.info("支付宝扫码异步通知------------------------------------------");
		try{
			StringBuilder builder = new StringBuilder();
			Map<String, String> params = new HashMap<String, String>();
	        if(null != request){
	            Set<String> paramsKey = request.getParameterMap().keySet();
	            for(String key : paramsKey){
	            	String value = request.getParameter(key);
	                params.put(key, value);
	                
	                builder.append(key+"="+value+"&");
	            }
	        }
	        logger.info(builder.toString());
	        
	        //处理异步通知
	        boolean flag = aliPayService.qrPayNotify(params);
	        
	        OutputStream out = resp.getOutputStream();
	        if(flag){
				out.write("success".getBytes());
			}else{
				out.write("fail".getBytes());
			}
			out.flush();
			out.close();
		}catch(Exception e){
			logger.error("支付宝扫码异步通知处理失败！"+e.getMessage(), e);
		}
	}
	
	
	/**
	 * 校验订单号和key
	 * @param id
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private boolean validateKey(String id, String key) throws Exception{
		String redisOrderId = ResouresUtil.getPutKey(key);
		return id.equals(redisOrderId);
	}
	
	//创建支付记录
	private String createPayTrans(String order_id, String pay_type){
    	OrderPayTransVo orderPayTransVo = new OrderPayTransVo();
    	orderPayTransVo.setOrder_id(order_id);
    	orderPayTransVo.setOrder_status("A00");
    	orderPayTransVo.setPay_type(pay_type);
    	String pay_order_id = orderPayTransServDu.addOrderPayTrans(orderPayTransVo);
    	return pay_order_id;
	}
	
}
