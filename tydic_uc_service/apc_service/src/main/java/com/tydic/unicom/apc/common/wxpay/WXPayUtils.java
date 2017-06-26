package com.tydic.unicom.apc.common.wxpay;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tydic.unicom.apc.base.pub.interfaces.ApAttrServ;
import com.tydic.unicom.apc.base.pub.po.ApAttrPo;
import com.tydic.unicom.apc.service.common.interfaces.ApcRedisServiceServDu;
import com.tydic.unicom.apc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;



/**
 * 微信支付实际使用的接口
 * @author 吴川
 *
 */
@Service("WXPayUtilsServ")
public class WXPayUtils implements WXPayUtilsServ{
	
	private static Logger logger = Logger.getLogger(WXPayUtils.class);
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Autowired
	private ApcRedisServiceServDu apcRedisServiceServDu;
	@Autowired
	private ApAttrServ apAttrServ;
	/**
	 * 扫码支付模式二
	 * @param appid  微信分配的公众账号ID（企业号corpid即为此appId）
	 * @param mchid  微信支付分配的商户号
	 * @param deviceInfo  终端设备号
	 * @param body  商品或支付单简要描述
	 * @param outTradeNo  商户系统内部的订单号
	 * @param totalFee  订单总金额
	 * @param productId  此id为二维码中包含的商品ID
	 * @param expireTime  失效时间，以分为单位
	 * @return  返回支付码字符串
	 * @throws Exception  
	 */
	public String unifiedOrderNative(String deviceInfo, String body,
			String outTradeNo, int totalFee, String productId, String expireTime, String mchtFlag) throws Exception{

		//mchtFlag传ap_id的值，用于从缓存取微信配置参数
		System.out.println("=====null");
		//String appid = propertiesParamVo.getAppid();//WXPropertiesUtils.getProperties("appid", mchtFlag);
		//String mchid = propertiesParamVo.getMchid();//WXPropertiesUtils.getProperties("mchid", mchtFlag);
		//String key = propertiesParamVo.getSignKey();//WXPropertiesUtils.getProperties("sign_key", mchtFlag);
		
		String appid = "";
		String mchid = "";
		String key = "";
		String pay_expire = propertiesParamVo.getPayExpire();//WXPropertiesUtils.getProperties("pay_expire");
		String send_ip = propertiesParamVo.getSendIp();//WXPropertiesUtils.getProperties("send_ip");
		
		UocMessage uocMessage=apcRedisServiceServDu.queryForApAttr(mchtFlag);
		if(RespCodeContents.SUCCESS.equals(uocMessage.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String, Object> map=(Map<String, Object>) uocMessage.getArgs().get("apAttrInfo");
			appid = (String) map.get("appid_pay");
			mchid = (String) map.get("mchid");
			key = (String) map.get("sign_key");
		}else {
			throw new Exception("从缓存中获取微信配置失败！");
		}
		if(expireTime == null || expireTime.equals("")){
			expireTime = pay_expire;
		}
		
		int t = Integer.parseInt(expireTime);
		String notifyUrl = "";
		if("1".equals(propertiesParamVo.getPayExpire())){
			notifyUrl = propertiesParamVo.getNotifyUrlPc();
		}
		else{
			notifyUrl = propertiesParamVo.getNotifyUrlMobile();
		}
		//组发送包对象
		UnifiedOrderNativeReqData data = new UnifiedOrderNativeReqData(appid, mchid, deviceInfo, body, outTradeNo, totalFee, send_ip, productId, t, key,"NATIVE", "",notifyUrl);
		
		logger.info(data.toString());
		
		try{
			//发起请求，并获取返回结果
			String resultString = (new UnifiedOrderNativeService(propertiesParamVo.getUnifiedOrderApi()).request(data));
			
			logger.info("---------请求返回结果----------");
			logger.info(resultString);
				
			
			//转成结果对象
			UnifiedOrderResData resData = (UnifiedOrderResData) Util.getObjectFromXML(resultString, UnifiedOrderResData.class);
			
			//判断返回情况
			if(resData == null || resData.getReturn_code() == null){
				throw new Exception("获取不到返回信息");
			}
			else if(resData.getReturn_code().equals("SUCCESS")){
				if(resData.getResult_code().equals("SUCCESS")){
					
					if(!Signature.checkIsSignValidFromResponseString(resultString, key)){
						throw new Exception("签名验证失败");
					}
					
	    			String content = resData.getCode_url();
	    			
	    			String qrData = Util.qrImageData(content);
	    			
	    			return qrData;
	    		}
	    		else{
	    			throw new Exception(resData.getErr_code_des());
	    		}
			}
			else{
				throw new Exception(resData.getReturn_msg());
	    	}
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 * 微信统一下单
	 * @param deviceInfo
	 * @param body
	 * @param outTradeNo
	 * @param totalFee
	 * @param productId
	 * @param expireTime
	 * @param mchtFlag
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> unifiedorder(String deviceInfo, String body,
			String outTradeNo, int totalFee, String productId, String expireTime, String mchtFlag) throws Exception{
		String appid = "";//WXPropertiesUtils.getProperties("APPID", mchtFlag);
		String mchid = "";//WXPropertiesUtils.getProperties("MCHID", mchtFlag);
		String key = "";//WXPropertiesUtils.getProperties("SIGN_KEY", mchtFlag);
		logger.info("keykeykey"+key);
		String pay_expire = "";//WXPropertiesUtils.getProperties("pay_expire");
		String send_ip = "";//WXPropertiesUtils.getProperties("send_ip");
		
		if(expireTime == null || expireTime.equals("")){
			expireTime = pay_expire;
		}
		
		int t = Integer.parseInt(expireTime);
		
		String notifyUrl = "";
		if("1".equals(propertiesParamVo.getPayExpire())){
			notifyUrl = propertiesParamVo.getNotifyUrlPc();
		}
		else{
			notifyUrl = propertiesParamVo.getNotifyUrlMobile();
		}
		//组发送包对象
		UnifiedOrderNativeReqData data = new UnifiedOrderNativeReqData(appid, mchid, deviceInfo, body, outTradeNo, totalFee, send_ip, productId, t, key,"APP", "",notifyUrl);
		
		logger.info(data.toString());
		
		try{
			//发起请求，并获取返回结果
			String resultString = (new UnifiedOrderNativeService(propertiesParamVo.getUnifiedOrderApi()).request(data));
			
			logger.info("---------请求返回结果----------");
			logger.info(resultString);
				
			
			//转成结果对象
			UnifiedOrderResData resData = (UnifiedOrderResData) Util.getObjectFromXML(resultString, UnifiedOrderResData.class);
			Map<String,Object> map =new HashMap<String,Object>();
			//判断返回情况
			if(resData == null || resData.getReturn_code() == null){
				throw new Exception("获取不到返回信息");
			}
			else if(resData.getReturn_code().equals("SUCCESS")){
				if(resData.getResult_code().equals("SUCCESS")){
					
					if(!Signature.checkIsSignValidFromResponseString(resultString, key)){
						throw new Exception("签名验证失败");
					}
					
	    			map.put("object", resData);
	    		    return map;
	    		}
	    		else{
	    			throw new Exception(resData.getErr_code_des());
	    		}
			}
			else{
				throw new Exception(resData.getReturn_msg());
	    	}
		}catch(Exception e){
			throw e;
		}
	}
	
	
	/**
	 * 订单查询 
	 * @param appid  微信分配的公众账号ID（企业号corpid即为此appId）
	 * @param mchid  微信支付分配的商户号
	 * @param transactionId  微信的订单号，优先使用
	 * @param outTradeNo  商户系统内部的订单号，微信订单号、商户订单号二选一必传
	 * @return SUCCESS—支付成功
				REFUND—转入退款
				NOTPAY—未支付
				CLOSED—已关闭
				REVOKED—已撤销(刷卡支付)
				USERPAYING--用户支付中
				PAYERROR--支付失败(其他原因，如银行返回失败)
	 * @throws Exception
	 */
	public ScanPayQueryResData orderQuery(String transactionId,String outTradeNo, String mchtFlag) throws Exception{
		
		String appid = "";//propertiesParamVo.getAppid();
		String mchid = "";//propertiesParamVo.getMchid();
		String key = "";//propertiesParamVo.getSignKey();
		
		//组发送包对象
		ScanPayQueryReqData data = new ScanPayQueryReqData(appid, mchid, transactionId, outTradeNo, key);
		
		logger.info(data.toString());
		
		try{
		
			//发起请求，并获取返回结果
			String resultString = (new ScanPayQueryService(propertiesParamVo.getPayQueryApi()).request(data));
			
			
			logger.info(resultString);
			
			if(!Signature.checkIsSignValidFromResponseString(resultString, key)){
				throw new Exception("签名验证失败");
			}
			
			//转成结果对象
			ScanPayQueryResData resData = (ScanPayQueryResData) Util.getObjectFromXML(resultString, ScanPayQueryResData.class);
	
			logger.info(resData.toString());
			
			return resData;
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 处理异步通知返回的结果，如果正常则返回通知对象
	 * @param reqString
	 * @return
	 * @throws Exception 
	 */
	
	public NotifyReqData scanNotify(String reqString) throws Exception{
		if(reqString == null || "".equals(reqString)){
        	logger.error("无内容，处理结束!");
        	return null;
        }
		
		NotifyReqData data = (NotifyReqData) Util.getObjectFromXML(reqString, NotifyReqData.class);
		String appid=data.getAppid();
		//根据appid查到ap_id
		ApAttrPo apAttrPo=new ApAttrPo();
		apAttrPo.setAttr_id("appid_pay");
		apAttrPo.setAttr_value(appid);
		apAttrPo=apAttrServ.queryApId(apAttrPo);
		String ap_id=apAttrPo.getAp_id();
		String key="";
		UocMessage uocMessage=apcRedisServiceServDu.queryForApAttr(ap_id);
		if(RespCodeContents.SUCCESS.equals(uocMessage.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String, Object> map=(Map<String, Object>) uocMessage.getArgs().get("apAttrInfo");
			key = (String) map.get("sign_key");
		}else {
			throw new Exception("从缓存中获取微信配置失败！");
		}
        //校验签名
		if(!Signature.checkIsSignValidFromResponseString(reqString, key)){
			logger.error("签名验证失败");
			return null;
		}
		
		return data;
		
	}
	/**
	 * 申请退款
	 * @param deviceInfo  终端设备号
	 * @param transactionId 微信订单号
	 * @param outTradeNo  商户系统内部的订单号,transaction_id、out_trade_no二选一，如果同时存在优先级：transaction_id> out_trade_no
	 * @param outRefundNo  商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
	 * @param totalFee  订单总金额
	 * @param refundFee  退款总金额
	 * @param opOserId  操作员帐号
	 * @return  true表示申请退款成功
	 * @throws WXPayException
	 */
	public RefundResData refund(String deviceInfo, String transactionId,String outTradeNo, String outRefundNo, int totalFee, int refundFee, String opOserId, String mchtFlag) throws Exception{
		
//		String appid = propertiesParamVo.getAppid();//WXPropertiesUtils.getProperties("appid", mchtFlag);
//		String mchid = propertiesParamVo.getMchid();//WXPropertiesUtils.getProperties("mchid", mchtFlag);
//		String key = propertiesParamVo.getSignKey();//WXPropertiesUtils.getProperties("sign_key", mchtFlag);
		
		String appid = "";
		String mchid = "";
		String key = "";
		//此交易需要证书
		String certPath = "";
		String certPassword = "";
		UocMessage uocMessage=apcRedisServiceServDu.queryForApAttr(mchtFlag);
		if(RespCodeContents.SUCCESS.equals(uocMessage.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String, Object> map=(Map<String, Object>) uocMessage.getArgs().get("apAttrInfo");
			appid = (String) map.get("appid_pay");
			mchid = (String) map.get("mchid");
			key = (String) map.get("sign_key");
			certPath = (String) map.get("cert_path");
			certPassword = (String) map.get("cert_password");
		}else {
			throw new Exception("从缓存中获取微信配置失败！");
		}
		
		//组发送包对象
		RefundReqData data = new RefundReqData(appid, mchid, transactionId, outTradeNo, deviceInfo, outRefundNo, totalFee, refundFee, opOserId, "", key);
		
		logger.info("发送申请退款内容："+data.toString());
		
//		//此交易需要证书
//		String certPath = propertiesParamVo.getCertPath();//WXPropertiesUtils.getProperties("cert_path", mchtFlag);
//		String certPassword = propertiesParamVo.getCertPassword();//WXPropertiesUtils.getProperties("cert_password", mchtFlag);
			
		try{
			
			//发起请求，并获取返回结果
			String resultString = (new RefundService(certPath, certPassword,propertiesParamVo.getRefundApi()).request(data));
			
			
			logger.info("获取到申请退款返回内容："+resultString);
			
			if(!Signature.checkIsSignValidFromResponseString(resultString, key)){
				throw new Exception("签名验证失败");
			}
			
			//转成结果对象
			RefundResData resData = (RefundResData) Util.getObjectFromXML(resultString, RefundResData.class);
	
			logger.info(resData.toString());
			
			return resData;
		}catch(Exception e){
			throw e;
		}
			
	}
	
	public String unifiedOrderNative(String deviceInfo, String body,
			String outTradeNo, int totalFee, String productId, String expireTime) throws Exception{
		
		return unifiedOrderNative(deviceInfo, body, outTradeNo, totalFee, productId, expireTime, null);
	}
	
	public ScanPayQueryResData orderQuery(String transactionId,String outTradeNo) throws Exception{
		return orderQuery(transactionId, outTradeNo, null);
	}
	
	public UnifiedOrderResData unifiedOrderJSAPI(String deviceInfo, String body,
			String outTradeNo, int totalFee, String productId, String expireTime, String mchtFlag, String openid) throws Exception{
		String appid = "";//propertiesParamVo.getAppid();
		String mchid = "";//propertiesParamVo.getMchid();
		String key = "";//propertiesParamVo.getSignKey();
		
		String pay_expire = propertiesParamVo.getPayExpire();//WXPropertiesUtils.getProperties("pay_expire");
		String send_ip = propertiesParamVo.getSendIp();//WXPropertiesUtils.getProperties("send_ip");
		
		if(expireTime == null || expireTime.equals("")){
			expireTime = pay_expire;
		}
		
		int t = Integer.parseInt(expireTime);
		
		String notifyUrl = "";
		if("1".equals(propertiesParamVo.getPayExpire())){
			notifyUrl = propertiesParamVo.getNotifyUrlPc();
		}
		else{
			notifyUrl = propertiesParamVo.getNotifyUrlMobile();
		}
		//组发送包对象
		UnifiedOrderNativeReqData data = new UnifiedOrderNativeReqData(appid, mchid, deviceInfo, body, outTradeNo, totalFee, send_ip, productId, t, key,"JSAPI", openid,notifyUrl);
		
		logger.info(data.toString());
		
		try{
			//发起请求，并获取返回结果
			String resultString = (new UnifiedOrderNativeService(propertiesParamVo.getUnifiedOrderApi()).request(data));
			
			logger.info("---------请求返回结果----------");
			logger.info(resultString);
				
			
			//转成结果对象
			UnifiedOrderResData resData = (UnifiedOrderResData) Util.getObjectFromXML(resultString, UnifiedOrderResData.class);
			
			//判断返回情况
			if(resData == null || resData.getReturn_code() == null){
				throw new Exception("获取不到返回信息");
			}
			else if(resData.getReturn_code().equals("SUCCESS")){
				if(resData.getResult_code().equals("SUCCESS")){
					
					if(!Signature.checkIsSignValidFromResponseString(resultString, key)){
						throw new Exception("签名验证失败");
					}
					
	    			return resData;
	    		}
	    		else{
	    			throw new Exception(resData.getErr_code_des());
	    		}
			}
			else{
				throw new Exception(resData.getReturn_msg());
	    	}
		}catch(Exception e){
			throw e;
		}
	}
	
	public BrandWCPayData getBrandWCPayData(String prepay_id, String mchtFlag){
		BrandWCPayData brandWCPayData = new BrandWCPayData();
		
		brandWCPayData.setAppId("");
		brandWCPayData.setPackage_("prepay_id="+prepay_id);
		brandWCPayData.setTimeStamp(System.currentTimeMillis()/1000 + "");
		
		brandWCPayData.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));

        //根据API给的签名规则进行签名
        String sign = Signature.getSign(brandWCPayData.toMap(), "");
        
        brandWCPayData.setPaySign(sign);
        brandWCPayData.setSignType("MD5");
		
		return brandWCPayData;
	}
	
}
