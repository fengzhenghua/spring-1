package com.tydic.unicom.upc.service.pay.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.upc.base.database.inst.interfaces.TransWXPayServ;
import com.tydic.unicom.upc.base.database.po.inst.TransWXPayPo;
import com.tydic.unicom.upc.service.activemq.interfaces.CustNotifyActivemqServDu;
import com.tydic.unicom.upc.service.code.interfaces.InfoPayParaAttrServDu;
import com.tydic.unicom.upc.service.inst.interfaces.InfoOrderServDu;
import com.tydic.unicom.upc.service.inst.interfaces.OrderPayTransServDu;
import com.tydic.unicom.upc.service.pay.interfaces.WXPayServDu;
import com.tydic.unicom.upc.vo.UpcPropertiesVo;
import com.tydic.unicom.upc.vo.code.InfoPayParaAttrValueVo;
import com.tydic.unicom.upc.vo.inst.InfoOrderVo;
import com.tydic.unicom.upc.vo.inst.OrderPayTransVo;
import com.tydic.unicom.upc.wxpay.WXPay;
import com.tydic.unicom.upc.wxpay.business.ScanPayBusiness;
import com.tydic.unicom.upc.wxpay.business.DownloadBillBusiness.ResultListener;
import com.tydic.unicom.upc.wxpay.common.Signature;
import com.tydic.unicom.upc.wxpay.common.Util;
import com.tydic.unicom.upc.wxpay.protocol.downloadbill_protocol.DownloadBillReqData;
import com.tydic.unicom.upc.wxpay.protocol.downloadbill_protocol.DownloadBillResData;
import com.tydic.unicom.upc.wxpay.protocol.notify_protocol.NotifyReqData;
import com.tydic.unicom.upc.wxpay.protocol.pay_protocol.ScanPayReqData;
import com.tydic.unicom.upc.wxpay.protocol.pay_protocol.ScanPayResData;
import com.tydic.unicom.upc.wxpay.protocol.refund_protocol.RefundReqData;
import com.tydic.unicom.upc.wxpay.protocol.refund_protocol.RefundResData;
import com.tydic.unicom.upc.wxpay.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tydic.unicom.upc.wxpay.protocol.unified_order_protocol.UnifiedOrderResData;
import com.tydic.unicom.webUtil.UocMessage;

public class WXPayServiceImpl implements WXPayServDu {

	private static final Logger logger = Logger.getLogger(WXPayServiceImpl.class);
	
	@Autowired
	private InfoOrderServDu infoOrderServDu;
	
	@Autowired
	private InfoPayParaAttrServDu infoPayParaAttrServDu;
	
	@Autowired
	private OrderPayTransServDu orderPayTransServDu;
	
	@Autowired
	private CustNotifyActivemqServDu custNotifyActivemqServDu;
	
	@Autowired
	private UpcPropertiesVo upcPropertiesVo;
	
	@Autowired
	private TransWXPayServ transWXPayServ;
	
	@Override
	public void paymentScanPay(String appid, String mchid, String key, String authCode, final String order_id, String body, String detail,
			final String outTradeNo, int totalFee, String deviceInfo, String spBillCreateIP, String timeStart,
			String timeExpire, String attach, final String pay_type) throws Exception {
		
		ScanPayReqData scanPayReqData = new ScanPayReqData(appid, mchid, key, authCode, body,attach,outTradeNo,totalFee,deviceInfo, spBillCreateIP,timeStart, timeExpire, "");
    	WXPay.doScanPayBusiness(scanPayReqData,new ScanPayBusiness.ResultListener() {
			
			@Override
			public void onSuccess(ScanPayResData scanPayResData) {
				logger.info("订单"+outTradeNo+"支付成功!"+scanPayResData.getTransaction_id());
				updateScanPayResult(scanPayResData, order_id, outTradeNo, pay_type);
			}
			
			@Override
			public void onFailBySignInvalid(ScanPayResData scanPayResData) {
				logger.error("订单"+outTradeNo+"支付失败："+scanPayResData.getReturn_msg());
				updateScanPayResult(scanPayResData, order_id, outTradeNo, pay_type);
			}
			
			@Override
			public void onFailByReturnCodeFail(ScanPayResData scanPayResData) {
				logger.error("订单"+outTradeNo+"支付失败："+scanPayResData.getReturn_msg());
				updateScanPayResult(scanPayResData, order_id, outTradeNo, pay_type);
			}
			
			@Override
			public void onFailByReturnCodeError(ScanPayResData scanPayResData) {
				logger.error("订单"+outTradeNo+"支付失败："+scanPayResData.getReturn_msg());
				updateScanPayResult(scanPayResData, order_id, outTradeNo, pay_type);
			}
			
			@Override
			public void onFailByMoneyNotEnough(ScanPayResData scanPayResData) {
				logger.error("订单"+outTradeNo+"支付失败："+scanPayResData.getReturn_msg());
				updateScanPayResult(scanPayResData, order_id, outTradeNo, pay_type);
			}
			
			@Override
			public void onFailByAuthCodeInvalid(ScanPayResData scanPayResData) {
				logger.error("订单"+outTradeNo+"支付失败："+scanPayResData.getErr_code_des());
				updateScanPayResult(scanPayResData, order_id, outTradeNo, pay_type);
			}
			
			@Override
			public void onFailByAuthCodeExpire(ScanPayResData scanPayResData) {
				logger.error("订单"+outTradeNo+"支付失败："+scanPayResData.getErr_code_des());
				updateScanPayResult(scanPayResData, order_id, outTradeNo, pay_type);
			}
			
			@Override
			public void onFail(ScanPayResData scanPayResData) {
				logger.error("订单"+outTradeNo+"支付失败："+scanPayResData.getErr_code_des());
				updateScanPayResult(scanPayResData, order_id, outTradeNo, pay_type);
			}
		});

	}
	
	
	public void updateScanPayResult(ScanPayResData scanPayResData, String order_id, String outTradeNo, String pay_type){
		//根据order_id，获取info_order数据
		InfoOrderVo infoOrderVo = new InfoOrderVo();
		infoOrderVo.setOrder_id(order_id);
		
		infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
		
		OrderPayTransVo orderPayTransVo = new OrderPayTransVo();
		orderPayTransVo.setPay_order_id(outTradeNo);
		orderPayTransVo = orderPayTransServDu.queryByPayOrderId(orderPayTransVo);
		if(orderPayTransVo == null){
			logger.error("找不到支付订单号为"+outTradeNo+"的数据");
			return;
		}
		
		if(infoOrderVo == null){
			logger.error("找不到订单号为"+order_id+"的数据");
			return;
		}
		else{
			
			//收到返回的时间
			infoOrderVo.setPay_notify_time(new Date());
			
			if(scanPayResData.getReturn_code().equals("SUCCESS")){
				/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date trade_time = new Date();
				try {
					trade_time = sdf.parse(scanPayResData.getTime_end());
					logger.info("交易时间为：" + trade_time);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}*/
				String trade_time = scanPayResData.getTime_end();
				if(scanPayResData.getResult_code().equals("SUCCESS")){
						infoOrderVo.setPay_notify_code("0");
						infoOrderVo.setPay_notify_trans_id(scanPayResData.getTransaction_id());
						infoOrderVo.setOrder_status("A10");
						infoOrderVo.setTrade_time(trade_time);
				}
				else{
					infoOrderVo.setPay_notify_code("1");
					infoOrderVo.setPay_notify_msg(scanPayResData.getErr_code_des());
					infoOrderVo.setOrder_status("A20");
					infoOrderVo.setTrade_time(trade_time);
				}
			}
			else{
				infoOrderVo.setPay_notify_code("1");
				infoOrderVo.setPay_notify_msg(scanPayResData.getReturn_msg());
				infoOrderVo.setOrder_status("A20");
			}
			
			infoOrderVo.setPay_order_id(outTradeNo);
			infoOrderVo.setCust_notify_code("-");
			infoOrderVo.setPay_type(pay_type);
			infoOrderServDu.updateInfoOrder(infoOrderVo);
			
			
			//更新OrderPayTrans， 用来提供给支付状态查询
			orderPayTransVo.setOrder_status(infoOrderVo.getOrder_status());
			orderPayTransVo.setPay_msg(infoOrderVo.getPay_notify_msg());
			orderPayTransServDu.updateOrderPayTrans(orderPayTransVo);
			
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
			
		}
	}


	@Override
	public Map<String, String> qrCodePay(String appid, String mchid, String key, String body, String detail, String outTradeNo,
			int totalFee, String deviceInfo, String spBillCreateIP, int expireTime, String trade_type, String notifyUrl, 
			String productId) throws Exception {
		UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(appid, mchid, key, deviceInfo, body, outTradeNo, totalFee, spBillCreateIP, productId, expireTime, trade_type, "", notifyUrl);
    	
		UnifiedOrderResData resData = WXPay.requestUnifiedOrderService(unifiedOrderReqData);
		
		Map<String, String> map = new HashMap<>();
		
		map.put("code_url", resData.getCode_url());
		
		return map;
	}


	@Override
	public Map<String, String> refund(String appid, String mchid, String key, String outTradeNo, String transactionID,
			String outRefundNo, String deviceInfo, int totalFee, int refundFee, String opUserID, String refundFeeType, String certName,
			String certPassword) throws Exception {
		
		String wxCertpath = upcPropertiesVo.getWx_cert_path();
		
		certName = wxCertpath + "/" + certName;
		
		RefundReqData refundReqData = new RefundReqData(appid, mchid, key, transactionID, outTradeNo, deviceInfo, outRefundNo, totalFee, refundFee, opUserID, refundFeeType);
    	
		RefundResData resData = WXPay.requestRefundService(refundReqData, certName, certPassword);
 
		Map<String, String> map = new HashMap<>();
		
		logger.info("微信退款成功时间为：" + resData.getRefund_success_time()); // 微信退款时间没有实时返回
		if(resData.getRefund_success_time() == null||"".equals(resData.getRefund_success_time())){
			resData.setRefund_success_time(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		}
		map.put("result_code", resData.getResult_code());
		map.put("transaction_id", resData.getTransaction_id());
		map.put("return_msg", resData.getReturn_msg());
		map.put("trade_time", resData.getRefund_success_time());
		
		return map;
	}


	@Override
	public boolean saveQrPayNotify(String reqString) throws Exception {
		NotifyReqData reqData = (NotifyReqData) Util.getObjectFromXML(reqString, NotifyReqData.class);
        String pay_order_id = reqData.getOut_trade_no();
        
        
        OrderPayTransVo orderPayTransVo = new OrderPayTransVo();
		orderPayTransVo.setPay_order_id(pay_order_id);
		orderPayTransVo = orderPayTransServDu.queryByPayOrderId(orderPayTransVo);
		if(orderPayTransVo == null){
			logger.error("找不到支付订单号为"+pay_order_id+"的数据");
			return false;
		}
		
        String order_id = orderPayTransVo.getOrder_id();
        //获取订单数据
		InfoOrderVo infoOrderVo = new InfoOrderVo();
		infoOrderVo.setOrder_id(order_id);
		infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
		if(infoOrderVo == null){
			logger.error("找不到订单为"+orderPayTransVo.getOrder_id()+"的数据!");
			return false;
		}
		else{
			String busi_id = infoOrderVo.getBusi_id();
			
			InfoPayParaAttrValueVo attrValueVo = infoPayParaAttrServDu.getPayParaValueByPayType(busi_id, orderPayTransVo.getPay_type());
			if(attrValueVo == null){
				logger.error("找不到对应的支付类型参数配置数据!");
				return false;
			}
			
	        //校验签名
			if(!Signature.checkIsSignValidFromResponseString(reqString, attrValueVo.getSignkey())){
				logger.error("签名验证失败");
				return false;
			}
			else{
				infoOrderVo.setPay_notify_time(new Date());
				infoOrderVo.setPay_type(orderPayTransVo.getPay_type());
				infoOrderVo.setPay_order_id(pay_order_id);
				if(reqData.getReturn_code().equals("SUCCESS")){
					infoOrderVo.setPay_notify_code("0");
					infoOrderVo.setPay_notify_trans_id(reqData.getTransaction_id());
					infoOrderVo.setOrder_status("A10");
					infoOrderVo.setTrade_time(reqData.getTime_end());
				}
				else{
					infoOrderVo.setPay_notify_code("1");
					infoOrderVo.setPay_notify_msg(reqData.getReturn_msg());
					infoOrderVo.setOrder_status("A20");
				}
				
				infoOrderVo.setCust_notify_code("-");
				infoOrderServDu.updateInfoOrder(infoOrderVo);
				
				
				//更新OrderPayTrans， 用来提供给支付状态查询
				orderPayTransVo.setOrder_status(infoOrderVo.getOrder_status());
				orderPayTransVo.setPay_msg(infoOrderVo.getPay_notify_msg());
				orderPayTransServDu.updateOrderPayTrans(orderPayTransVo);
				
				
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
				
				return true;
			}
		}
	}


	@Override
	public void genDownloadBill(String appid, String mchid, String key, String deviceInfo, final String billDate,
			String billType, final String busi_id, final String pay_type) throws Exception {
		DownloadBillReqData downloadBillReqData = new DownloadBillReqData(appid, mchid, key, deviceInfo, billDate, billType);
    	WXPay.doDownloadBillBusiness(downloadBillReqData, new ResultListener() {
			
			@Override
			public void onFailByReturnCodeFail(DownloadBillResData downloadBillResData) {
				logger.error("对帐单下载失败："+downloadBillResData.getReturn_msg());
			}
			
			@Override
			public void onFailByReturnCodeError(DownloadBillResData downloadBillResData) {
				logger.error("对帐单下载失败："+downloadBillResData.getReturn_msg());
			}
			
			@Override
			public void onDownloadBillSuccess(String response) {
				updateDownloadBill(response, busi_id, billDate, pay_type);
			}
			
			@Override
			public void onDownloadBillFail(String response) {
				logger.error("对帐单下载失败："+response);
			}
		});
		
	}
	
	
	private void updateDownloadBill(String response, String busi_id, String billDate, String pay_type){
//		交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,企业红包金额,微信退款单号,商户退款单号,退款金额,企业红包退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率
//		`2017-02-22 21:28:36,`wx581fd45b644746c1,`1249429501,`0,`13209514891,`4000162001201702220941336056,`NX201702222124181000000007585,`ofvrBvll8NmAvq9-0wkg5nqDuzxM,`NATIVE,`SUCCESS,`CFT,`CNY,`50.00,`0.00,`0,`0,`0.00,`0.00,`,`,`微信扫码支付,`,`0.30000,`0.60%
		
		String dataStrs[] = response.replace("\r\n", "\n").split("\n");
		
		if(dataStrs.length > 3){
			
			List<TransWXPayPo> poList = new ArrayList<>();
			
			for(int i=1; i<dataStrs.length-2; i++){
				String str = dataStrs[i];
				String strs[] = str.replace("`", "").split(",");
				
				TransWXPayPo po = new TransWXPayPo();
				
				po.setBill_date(billDate);
				po.setBill_check_flag("0");
				po.setTrans_time(strs[0]);
				po.setAppid(strs[1]);
				po.setMchid(strs[2]);
				po.setSub_mchid(strs[3]);
				po.setDevice_info(strs[4]);
				po.setTransaction_id(strs[5]);
				po.setOut_trade_no(strs[6]);
				po.setOpenid(strs[7]);
				po.setTrade_type(strs[8]);
				po.setResult_code(strs[9]);
				po.setBank(strs[10]);
				po.setFee_type(strs[11]);
				po.setTotal_fee(strs[12]);
				po.setRed_fee(strs[13]);
				po.setRefund_id(strs[14]);
				po.setOut_refund_no(strs[15]);
				po.setSettlement_refund_fee(strs[16]);
				po.setRed_refund_fee(strs[17]);
				po.setRefund_type(strs[18]);
				po.setRefund_code(strs[19]);
				po.setBody(strs[20]);
				po.setDetail(strs[21]);
				po.setMer_fee(strs[22]);
				po.setRate_fee(strs[23]);
				
				
				//交易时间+公众号+商户号+商户订单号+微信订单号
				String trans_time = po.getTrans_time().replaceAll("[^0-9]", "");
				po.setBill_trans_id(trans_time+po.getAppid()+po.getMchid()+po.getOut_trade_no()+po.getTransaction_id());
				
				
				poList.add(po);
			}
			
			transWXPayServ.addTransWXPay(poList);
			
			logger.info("微信对帐单下载，busi_id="+busi_id+", billDate="+billDate+", 下载成功!");
		}
		
	}

}
