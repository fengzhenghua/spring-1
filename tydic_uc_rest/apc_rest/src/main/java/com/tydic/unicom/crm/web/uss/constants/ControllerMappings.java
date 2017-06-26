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
	
	public static final String DEMO_REST = REST + "demoRest";
	
	public static final String AP=REST+"ap";
	
	public static final String GET_INFO_REST = REST + "getInfoRest";
	
	/**
	 * @see OptionalOperRest
	 */
	public static final String OPTIONAL_OPER_REST = REST + "optionalOper/";
	/**
	 * (oppo)中台相关服务
	 */
	public static final String OPPO_UOC_ORDER_SERVICE_REST = REST + "oppoUocOrderServiceRest";

	/**
	 * (oppo)订单服务rest
	 * */
	public static final String OPPO_ORDER_SERVICE_REST = REST + "oppoOrderServiceRest";
	
	/**
	 * (oppo)号码服务rest
	 * */
	public static final String OPPO_NUMBER_SERVICE_REST = REST + "oppoNumberServiceRest";
	
	/**
	 * (oppo)验证服务rest
	 * */
	public static final String OPPO_CHECK_SERVICE_REST = REST + "oppoCheckServiceRest";
	
	/**
	 * (oppo)公用服务rest
	 * */
	public static final String OPPO_COMMON_SERVICE_REST = REST + "oppoCommonServiceRest";
	
	/**
	 * (oppo)支付服务rest
	 * */
	public static final String OPPO_PAY_SERVICE_REST = REST + "oppoPayServiceRest";
	
	/**
	 * (oppo)活体审核服务rest
	 * */
	public static final String OPPO_EXAMINE_SERVICE_REST = REST + "oppoExamineServiceRest";
	
	/**
	 * APC服务统一入口
	 * */
	public static final String APC = REST + "apc";
	
	/** CodeList服务 */
	public static final String CODE_LIST_REST = REST + "codeList";
	/** APC0014-触点属性维护、 APC0015-触点属性查询 */
	public static final String OPERATE_AP_ATTR_REST = REST + "operateApAttrRest";
}
