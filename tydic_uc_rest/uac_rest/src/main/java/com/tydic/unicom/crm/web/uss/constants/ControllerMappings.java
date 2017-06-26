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
	/**
	 * 登陆与登出rest
	 * */
	public static final String LOGIN_AND_LOGOUT_REST = REST + "loginAndLogoutRest";
	
	/**
	 * 获取用户信息rest
	 * */
	public static final String OPER_REST = REST + "operRest";
	
	/**
	 * 获取登录信息rest
	 */
	public static final String BASE_OPER_REST = REST+"baseOperRest";
	
	/**
	 * 获取其他信息rest
	 * */
	public static final String GET_OTHER_INFO_REST = REST + "getOtherInfoRest";
	/**
	 * 转发服务rest
	 * */
	public static final String REST_SERVICE_CONTROLLER_REST = REST + "restServiceControllerRest";
}
