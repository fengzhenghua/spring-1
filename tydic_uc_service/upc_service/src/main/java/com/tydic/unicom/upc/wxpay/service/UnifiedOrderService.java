package com.tydic.unicom.upc.wxpay.service;

import com.tydic.unicom.upc.wxpay.common.Configure;
import com.tydic.unicom.upc.wxpay.protocol.unified_order_protocol.UnifiedOrderReqData;

public class UnifiedOrderService extends BaseService {

	public UnifiedOrderService()
			throws ClassNotFoundException, IllegalAccessException, InstantiationException, SecurityException,
			IllegalArgumentException, NoSuchMethodException{
		super(Configure.UNIFIED_ORDER_API);
	}

	/**
	 * 请求支付服务
	 * 
	 * @param scanPayReqData
	 *            这个数据对象里面包含了API要求提交的各种数据字段
	 * @return API返回的数据
	 * @throws Exception
	 */
	public String request(UnifiedOrderReqData unifiedOrderReqData) throws Exception {

		// --------------------------------------------------------------------
		// 发送HTTPS的Post请求到API地址
		// --------------------------------------------------------------------
		String responseString = sendPost(unifiedOrderReqData);

		return responseString;
	}

}
