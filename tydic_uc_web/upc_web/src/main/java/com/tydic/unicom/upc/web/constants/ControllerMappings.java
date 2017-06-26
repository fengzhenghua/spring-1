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
package com.tydic.unicom.upc.web.constants;


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
	
	
	public static final String BUSI_REQ_CONTROLLER = NEED_AUTHORITY + "busiReq";

	public static final String UPC_BUSI = NEED_AUTHORITY + "upcBusi";
	
	public static final String APP_BUSI = NEED_AUTHORITY + "appBusi";
	
	
	public static final String TEST_REQ_CONTROLLER = NEED_AUTHORITY + "testReq";
	
	public static final String PAY_CONTROLLER = NEED_AUTHORITY + "pay";
	
	
	public static final String CASHIER_CONTROLLER = NEED_AUTHORITY + "cashier";
	
	public static final String PAY_CHECK_CONTROLLER = NO_AUTHORITY + "payCheck";
}
