/**
 * @ProjectName: uss_web
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年9月12日 下午8:00:01
 * @Title: ControllerMappings.java
 * @Package com.tydic.unicom.crm.web.uss.constants
 * @Description: TODO
 */
package com.tydic.unicom.crm.web.uss.constants;


/**
 * <p></p>
 * @author yuhao 2017年01月12日 下午8:00:01
 * @ClassName ControllerMappings
 * @Description 用于ControllerMapping的常量配置
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月12日
 * @modify by reason:{方法名}:{原因}
 */
public class ControllerMappings {

	/**常量------------------------------------------------------------------*/
	public static final String NEED_AUTHORITY = "authority/";
	public static final String NO_AUTHORITY = "noauthority/";

	public static final String REST = "rest/";

	// 触点商品服务接口
	public static final String APGOODS_REST = REST + "apGoodsRest";

	//商品中心对外能力平台接口
    public static final String COMMODITY_CENTER_REST = REST + "CommodityCenter";
    //触点商品维护
    public static final String AP_GOODS_REST = REST + "apGoods";
    //商品SKU维护
    public static final String SKU_REST = REST + "skuServiceRest";
    
    /** 获取可选sku RestController */
    public static final String GET_OPTIONAL_SKUR_EST= REST + "GetOptionalSkuRest";
    //获取codeList
    public static final String CODE_LIST = REST + "CodeList";
}
