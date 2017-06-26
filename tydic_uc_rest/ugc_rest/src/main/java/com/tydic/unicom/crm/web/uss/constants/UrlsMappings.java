/**
 * @ProjectName: uss_web
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年9月12日 下午8:01:17
 * @Title: UrlsMappings.java
 * @Package com.tydic.unicom.crm.web.uss.constants
 * @Description: TODO
 */
package com.tydic.unicom.crm.web.uss.constants;

/**
 * <p>
 * </p>
 * @author yuhao 2017年01月12日 下午8:01:17
 * @ClassName UrlsMappings
 * @Description 用于Controller方法的URL常量
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月12日
 * @modify by reason:{方法名}:{原因}
 */
public class UrlsMappings {

	/** 常量------------------------------------------------------------------ */
	public static final String TEST_CREATE = "testCreate";
	public static final String TEST_UPDATE = "testUpdate";
	public static final String TEST_DELETE = "testDelete";
	public static final String TEST_QUERY = "testQuery";

	/*
	 * 通过能力平台调用订单中心
	 */
	public static final String COMMODITY_CENTER_SERVICE = "commodityCenterService";
	/*
	 * 获取可选商品信息
	 */
	public static final String GET_OPTIONAL_GOODS="getOptionalGoods";
	/*
	 * 触点商品查询
	 */
	public static final String APGOODS_QUERY = "apGoodsQuery";

	//触点商品维护
	public static final String OPERATE_AP_GOODS = "operateApGoods";

	/*
	 * 获取触点sku信息
	 */
	public static final String GET_CONTACT_SKU = "getContactSku";
	/*
	 * 商品维护
	 */
	public static final String OPERATE_GOODS_DEFINE = "operateGooodsDefine";
	/*
	 * sku维护
	 */
	public static final String OPERATE_SKU = "operateSku";
	/*
	 * sku维护
	 */
	public static final String OPERATE_SKU_NEW = "operateSkuNew";
	/*
	 * sku查询
	 */
	public static final String QUERY_SKUDEFINE_SKUATTR = "querySkuDefineAndSkuAttr";
	/*
	 * sku查询
	 */
	public static final String QUERY_SKUDEFINE_SKUATTR_NEW = "querySkuDefineAndSkuAttrNew";
	/*
	 * 商品查询
	 */
	public static final String QUERY_GOODS_DEFINE_LIST = "queryGoodsDefineList";

	/*
	 * UGC0010-获取可选sku
	 */
	public static final String GET_OPTIONAL_SKU_REST_METHOD = "getOptionalSkuRestMethod";

	/*
	 * 获取可选触点
	 */
	public static final String GET_OPTIONAL_CONTACT="getOptionalcontact";
	
	/*
	 * typeCode获取CodeList
	 */
	public static final String GET_CODE_LIST_BY_TYPE = "getCodeListByType";
	/*
	 * 刷新ugc缓存
	 */
	public static final String REFRESH_UGC_REDIS_ALL = "refreshUgcRedisAll";
}

