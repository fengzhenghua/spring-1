package com.tydic.unicom.upc.service.pay.interfaces;

import java.util.List;
import java.util.Map;

import com.tydic.unicom.upc.alipay.model.ExtendParams;
import com.tydic.unicom.upc.alipay.model.GoodsDetail;
import com.tydic.unicom.upc.alipay.model.result.AlipayF2FRefundResult;

public interface AliPayServDu {

	/**
	 * 支付宝条码支付
	 * @param appid
	 * @param privateKey
	 * @param publicKey
	 * @param outTradeNo
	 * @param subject
	 * @param totalAmount
	 * @param authCode
	 * @param undiscountableAmount
	 * @param sellerId
	 * @param body
	 * @param operatorId
	 * @param storeId
	 * @param extendParams
	 * @param timeoutExpress
	 * @param goodsDetailList
	 */
	public void paymentScanPay(String appid, String privateKey, String publicKey, String outTradeNo, String order_id,
			String subject, String totalAmount, String authCode, String undiscountableAmount,
			String sellerId, String body, String operatorId,  String storeId, ExtendParams extendParams,
			String timeoutExpress, List<GoodsDetail> goodsDetailList, String pay_type);
	
	
	/**
	 * 支付宝扫码支付
	 * @param appid
	 * @param privateKey
	 * @param publicKey
	 * @param outTradeNo
	 * @param subject
	 * @param totalAmount
	 * @param notifyUrl
	 * @param undiscountableAmount
	 * @param sellerId
	 * @param body
	 * @param operatorId
	 * @param storeId
	 * @param extendParams
	 * @param timeoutExpress
	 * @param goodsDetailList
	 * @return
	 */
	public Map<String, String> qrCodePay(String appid, String privateKey, String publicKey, String outTradeNo,
			String subject, String totalAmount, String notifyUrl, String undiscountableAmount,
			String sellerId, String body, String operatorId,  String storeId, ExtendParams extendParams,
			String timeoutExpress, List<GoodsDetail> goodsDetailList);
	
	
	public boolean qrPayNotify(Map<String, String> params) throws Exception;
	
	
	/**
	 * 支付宝退款
	 * @param appid
	 * @param privateKey
	 * @param publicKey
	 * @param outTradeNo
	 * @param refundAmount
	 * @param outRequestNo
	 * @param refundReason
	 * @param storeId
	 * @return
	 */
	public Map<String, String> refund(String appid, String privateKey, String publicKey, String outTradeNo,
			String refundAmount, String outRequestNo, String refundReason, String storeId);
	
	
	/**
	 * 支付宝对帐单下载
	 * @param appid
	 * @param privateKey
	 * @param publicKey
	 * @param billType
	 * @param billDate
	 */
	public void downloadBill(String appid, String privateKey, String publicKey, String billType, String billDate);
}
