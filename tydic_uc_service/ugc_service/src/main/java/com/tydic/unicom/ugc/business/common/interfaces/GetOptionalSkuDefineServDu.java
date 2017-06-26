package com.tydic.unicom.ugc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>UGC商品中心业务服务类</p>
 * UGC0010-获取可选sku服务
 * @author ZhangCheng
 * @date 2017-03-31
 * @version V1.0
 */
public interface GetOptionalSkuDefineServDu {
	
	/**
	 * UGC0010-获取可选sku服务
	 * @param jsession_id 验证字符串
	 * @param sku_id sku标识
	 * @param sku_name sku名称
	 * @return
	 * @throws Exception
	 */
	UocMessage getSkuDefineBySkuIdOrSkuName(String jsession_id,String sku_id,String sku_name) throws Exception;

}
