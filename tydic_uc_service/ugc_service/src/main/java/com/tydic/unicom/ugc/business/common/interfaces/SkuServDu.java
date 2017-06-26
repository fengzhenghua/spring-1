package com.tydic.unicom.ugc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

/**
 * 商品中心服务-sku相关接口<br>
 * UGC0005-商品维护<br>
 * UGC0006-商品查询<br>
 * UGC0007-sku查询<br>
 * UGC0008-sku维护<br>
 * @create ChenKang by 2017-03-31
 * @modify ZhangCheng by 2017-06-05
 */
public interface SkuServDu {
	/**
	 * UGC0005-商品维护
	 * @param jsession_id
	 * @param json_info
	 * @param oper_type
	 * @return
	 * @throws Exception
	 */
	public UocMessage createOperateGooodsDefine(String jsession_id, String json_info, String oper_type) throws Exception;
	/**
	 * UGC0006-商品查询
	 * @param jsession_id
	 * @param json_info
	 * @return
	 */
	public UocMessage queryGoodsDefineList(String jsession_id, String json_info) throws Exception;
	/**
	 * UGC0007-sku查询
	 * @param jsession_id
	 * @param json_info
	 * @return
	 */
	@Deprecated
	public UocMessage querySkuDefineAndSkuAttr(String jsession_id,String json_info)throws Exception;
	/**
	 * UGC0007-sku查询
	 * @param jsession_id
	 * @param json_info
	 * @return
	 */
	public UocMessage querySkuDefineAndSkuAttr(String jsession_id,String oper_sku,String json_info)throws Exception;
	/**
	 * UGC0008-sku维护
	 * @param jsession_id
	 * @param json_info
	 * @param oper_type
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public UocMessage createOperateSku(String jsession_id,String json_info,String oper_type) throws Exception;

	/**
	 * UGC0008-sku维护
	 * @param jsession_id
	 * @param json_info
	 * @param oper_sku
	 * @param oper_type
	 * @return
	 * @throws Exception
	 */
	public UocMessage createOperateSku(String jsession_id,String json_info,String oper_sku,String oper_type) throws Exception;

}
