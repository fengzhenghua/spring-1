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
	
	/**
	 * 登陆
	 * */
	public static final String LOGIN = "login";
	/**
	 * 登出
	 * */
	public static final String LOGOUT = "logout";
	/**
	 * 获取jsessionId
	 * */
	public static final String GET_JSESSION_ID = "getJsessionId";
	/**
	 * 获取可选部门信息
	 * */
	public static final String GET_DEPART_INFO = "getDepartInfo";
	/**
	 * 获取可选营业厅信息
	 * */
	public static final String GET_BUSINESS_HALL_INFO = "getBusinessHallInfo";
	/**
	 * 转发rest
	 * */
	public static final String REST_CONTROLLER_SERVICE = "restControllerService";
	/**
	 * 获取可选工号信息
	 * */
	public static final String GET_OPER_INFO = "getOperInfo";
	/**
	 * 根据工号获取对应部门信息
	 * */
	public static final String GET_DEPART_INFO_BY_OPER_NO = "getDepartInfoByOperNo";
	
	/**
	 * 获取登录信息通过工号或验证字符串
	 */
	public static final String GET_BASE_OPER_INFO_BY_JSESSION_ID = "getBaseOperInfoByJsessionId";
	
	/**
	 * 获取可选工号通过工号或验证字符串
	 */
	public static final String GET_OPER_INFO_BY_DEPART_NO = "getOperInfoByDepartNo";
	
	
	/**
	 * 根据工号获取对应部门信息
	 * */
	public static final String OPERATE_RULE_OPER_ROLE = "operateRuleOperRole";
	/**
	 * 根据工号获取对应部门信息
	 * */
	public static final String QUERY_RULE_OPER_ROLE = "queryRuleOperRole";
	/**
	 * 根据工号获取对应部门信息
	 * */
	public static final String QUERY_INFO_AGENT_DEVELOPERS = "queryInfoAgentDevelopers";
	/**
	 * 获取可选部门UAC0007
	 */
	public static final String QUERY_DEPART_INFO = "queryDepartInfo";
	/** UAC0008REST-获取可选渠道 */
	public static final String QUERY_ZB_INFO_AGENTS = "queryZbInfoAgents";
	/** UAC0009REST-获取可选地区 */
	public static final String QUERY_REGION_INFO = "queryRegionInfo";
}

