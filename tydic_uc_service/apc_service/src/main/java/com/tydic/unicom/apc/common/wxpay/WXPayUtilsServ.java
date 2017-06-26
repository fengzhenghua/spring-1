package com.tydic.unicom.apc.common.wxpay;

public interface WXPayUtilsServ {

	public String unifiedOrderNative(String deviceInfo, String body,
			String outTradeNo, int totalFee, String productId, String expireTime, String mchtFlag) throws Exception;
	
	public NotifyReqData scanNotify(String reqString) throws Exception;
	
	public RefundResData refund(String deviceInfo, String transactionId,String outTradeNo, String outRefundNo, int totalFee, int refundFee, String opOserId, String mchtFlag) throws Exception;
}
