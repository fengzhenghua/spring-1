package com.tydic.unicom.apc.business.pub.interfaces;

import com.tydic.unicom.apc.business.pub.vo.WXPayRestVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface OppoPayServiceServDu {

	/**
	 * 微信二维码
	 * @param reqVo
	 * @return
	 * @throws Exception
	 */
	public UocMessage getWxQrCode(WXPayRestVo reqVo,String opper_no) throws Exception;
	
	/**
	 * 微信支付回调通知
	 */
	public boolean getWxCallBack(String resString) throws Exception;
	
	/**
	 * 微信支付结果轮询
	 */
	public UocMessage getPayResult(String cs_order_id,String oper_no) throws Exception;
	
	/**
	 * 微信退款
	 */
	public UocMessage wxpayRefund(String order_id,String oper_no) throws Exception;
}
