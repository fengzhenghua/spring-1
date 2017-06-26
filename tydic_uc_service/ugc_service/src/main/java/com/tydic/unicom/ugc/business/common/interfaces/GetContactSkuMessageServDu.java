package com.tydic.unicom.ugc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface GetContactSkuMessageServDu {
	
	/**
	 * 根据触点ID获取触点sku信息
	 * @param ap_id
	 * @return
	 * @throws Exception
	 */
	public UocMessage queryContactSku(String ap_id) throws Exception;
	/**
	 * 
	 * 获取可选商品信息 (维护时用)
	 */
	public UocMessage getOptionalGoods(String jsession_id);
	 
}
