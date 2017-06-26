package com.tydic.unicom.upc.service.pay.interfaces;

import java.util.Map;

import com.tydic.unicom.upc.wxpay.protocol.pay_protocol.ScanPayResData;
import com.tydic.unicom.upc.wxpay.protocol.refund_protocol.RefundResData;
import com.tydic.unicom.upc.wxpay.protocol.refund_query_protocol.RefundQueryResData;
import com.tydic.unicom.upc.wxpay.protocol.unified_order_protocol.UnifiedOrderResData;

/**
 * 因为DUBBO可能会访问不了外网地址
 * 所以把支付的服务写到WEB端
 * @author 吴川
 *
 */
public interface WXPayServDu {

	/**
	 * 条码支付
	 * @param appid
	 * @param mchid
	 * @param key
	 * @param authCode
	 * @param body
	 * @param detail
	 * @param outTradeNo
	 * @param totalFee
	 * @param deviceInfo
	 * @param spBillCreateIP
	 * @param timeStart
	 * @param timeExpire
	 * @param attach
	 * @throws Exception
	 */
	public void paymentScanPay(String appid, String mchid, String key, String authCode, String order_id,
			String body, String detail, String outTradeNo, int totalFee, String deviceInfo,
			String spBillCreateIP,String timeStart, String timeExpire, String attach, String pay_type) throws Exception;
	
	public void updateScanPayResult(ScanPayResData scanPayResData, String order_id, String outTradeNo, String pay_type) throws Exception;
	
	/**
	 * 扫码支付
	 * @param appid
	 * @param mchid
	 * @param key
	 * @param body
	 * @param detail
	 * @param outTradeNo
	 * @param totalFee
	 * @param deviceInfo
	 * @param spBillCreateIP
	 * @param expireTime
	 * @param trade_type
	 * @param notifyUrl
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> qrCodePay(String appid, String mchid, String key, String body, String detail, String outTradeNo, int totalFee, String deviceInfo,
			String spBillCreateIP,int expireTime, String trade_type, String notifyUrl, String productId) throws Exception;
	
	/**
	 * 处理回调信息
	 * @param reqString
	 * @return
	 */
	public boolean saveQrPayNotify(String reqString) throws Exception;
	
	/**
	 * 发起退款
	 * @param appid
	 * @param mchid
	 * @param key
	 * @param outTradeNo
	 * @param transactionID
	 * @param outRefundNo
	 * @param deviceInfo
	 * @param totalFee
	 * @param refundFee
	 * @param opUserID
	 * @param refundFeeType
	 * @param certPath
	 * @param certPassword
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> refund(String appid, String mchid, String key, String outTradeNo,
			String transactionID, String outRefundNo, String deviceInfo, int totalFee, int refundFee, String opUserID,
			String refundFeeType, String certPath, String certPassword) throws Exception;
	
	/**
	 * 对帐单下载
	 * @param appid
	 * @param mchid
	 * @param key
	 * @param deviceInfo
	 * @param billDate
	 * @param billType
	 * @throws Exception
	 */
	public void genDownloadBill(String appid, String mchid, String key, String deviceInfo,String billDate,String billType, final String busi_id, final String pay_type) throws Exception;
}
