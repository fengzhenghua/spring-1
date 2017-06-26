/**
 * @ProjectName: uss_web
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年9月15日 上午10:52:18
 * @Title: Views.java
 * @Package com.tydic.unicom.crm.web.uss.constants
 * @Description: TODO
 */
package com.tydic.unicom.crm.web.uss.constants;


/**
 * <p></p>
 * @author yangfei 2014年9月15日 上午10:52:18
 * @ClassName Views
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月15日
 * @modify by reason:{方法名}:{原因}
 */
public class Views {
	/**常量------------------------------------------------------------------*/
	public static final String WEB_CHOICE_OPER_NO = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.CHOICE_OPER_NO;
	/* YUN-143 */ 
	public static final String WEB_CHOICE_INFO_AGENT_DEVELOPERS = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.CHOICE_INFO_AGENT_DEVELOPERS;
	
	public static final String WEB_INDEX = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.INDEX;
	
	public static final String WEB_HOME = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.HOME;
	
	public static final String WEB_CUSTOMER = ControllerMappings.USS_WEB_CUSTOMER + "/" + UrlsMappings.CUSTOMER;
	
	public static final String SHOW_NUMBER_INFO = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.SHOW_NUMBER_INFO;
	
	/** 
	* @Fields SALE_SELECTED_CODE : TODO	销售-选择套餐和活动
	*/ 
	public static final String SALE_SELECTED_CODE=ControllerMappings.USS_WEB_SALE+"/"+UrlsMappings.SALE_SELECTED_CODE;
	/** 
	* @Fields SALE_SELECTED_CODE : TODO	销售-选择2G套餐和活动
	*/ 
	public static final String SALE_SELECTED_CODE_2G=ControllerMappings.USS_WEB_SALE+"/"+UrlsMappings.SALE_SELECTED_CODE_2G;
	/** 
	* @Fields SALE_SELECTED_CODE : TODO	销售-选择3G套餐和活动
	*/ 
	public static final String SALE_SELECTED_CODE_3G=ControllerMappings.USS_WEB_SALE+"/"+UrlsMappings.SALE_SELECTED_CODE_3G;
	/** 
	* @Fields SALE_SELECTED_CODE : TODO	销售-选择4G套餐和活动
	*/ 
	public static final String SALE_SELECTED_CODE_4G=ControllerMappings.USS_WEB_SALE+"/"+UrlsMappings.SALE_SELECTED_CODE_4G;

	public static final String SALE_INDEX = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.SALE_INDEX;
	public static final String SALE_INDEX_NEW = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.SALE_INDEX_NEW;
	public static final String OFR_CODE_CONFIG = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.OFR_CODE_CONFIG;
	public static final String OFR_UPDATE_CONFIG = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.OFR_QUERY_CONFIG;
	public static final String GOTO_PRE_ORDER_INDEX = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.GOTO_PRE_ORDER_INDEX;

	public static final String DEPARTMENT_MANAGE = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.DEPARTMENT_MANAGE;
	public static final String NUMBER_DATA = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.NUMBER_DATA;
	
	public static final String PREFECTURE_SET_MEAL = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.PREFECTURE_SET_MEAL;
	public static final String PREFECTURE_ACTIVITY = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.PREFECTURE_ACTIVITY;
	public static final String QUERY_NUMBER = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.QUERY_NUMBER;
	public static final String MOVE_TERMINAL = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.MOVE_TERMINAL;
	public static final String PARTS_AREA = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.PARTS_AREA;
	public static final String GROUP_BUSINESS = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.GROUP_BUSINESS;
	
	public static final String ORDER_PROCESSING = ControllerMappings.USS_WEB_ORDER + "/" + UrlsMappings.ORDER_PROCESSING;

	public static final String QUERY_ORDER_PROSESSING_FEE = ControllerMappings.USS_WEB_ORDER_FEE + "/" + UrlsMappings.QUERY_ORDER_PROSESSING_FEE;
	/*待收费订单缴费*/
	public static final String ORDER_PROCESSING_PAY = ControllerMappings.USS_WEB_ORDER + "/" + UrlsMappings.ORDER_PROCESSING_PAY;
	
	public static final String ORDER_PROCESSING_PAY_FEE = ControllerMappings.USS_WEB_ORDER_FEE + "/" + UrlsMappings.ORDER_PROCESSING_PAY_FEE;
	
	public static final String OPEN_ACC_WKPRING = ControllerMappings.USS_WEB_WKPRINT + "/" + UrlsMappings.OPEN_ACC_WKPRING;
	public static final String OPEN_WOJIA_WKPRINT = ControllerMappings.USS_WEB_WKPRINT + "/" + UrlsMappings.OPEN_WOJIA_WKPRINT;
	
	public static final String ARCHIVES_WKPRINT = ControllerMappings.USS_WEB_WKPRINT + "/" + UrlsMappings.ARCHIVES_WKPRINT;
	
	public static final String WZH_PRINT = ControllerMappings.USS_WEB_WKPRINT + "/" + UrlsMappings.WZH_PRINT;
	
	public static final String SHOW_ORDER = ControllerMappings.USS_WEB_DEALSHOWOREDER + "/" + UrlsMappings.SHOW_ORDER;
	public static final String SHOW_ORDER_CHANGE = ControllerMappings.USS_WEB_DEALSHOWOREDER + "/" + UrlsMappings.SHOW_ORDER_CHANGE;
	public static final String SHOW_ORDER_FEE = ControllerMappings.USS_WEB_DEALSHOWOREDER + "/" + UrlsMappings.SHOW_ORDER_FEE;
	public static final String SHOW_ORDER_ARCHIVES = ControllerMappings.USS_WEB_DEALSHOWOREDER + "/" + UrlsMappings.SHOW_ORDER_ARCHIVES;
	public static final String PAYMENTBILL = ControllerMappings.USS_WEB_WKPRINT + "/" + UrlsMappings.PAYMENTBILL;
	
	public static final String OPEN_WJT_WKPRING = ControllerMappings.USS_WEB_WKPRINT + "/" + UrlsMappings.OPEN_WJT_WKPRING;
	
	public static final String BSS_SELECT_NUMBER = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.USS_WEB_SELECT_NUMBER;

	
	public static final String USS_WEB_SALE_OPEN = ControllerMappings.USS_WEB_SALE + "/" + UrlsMappings.USS_WEB_SALE_OPEN;
	
	/*资源验证*/
	public static final String USS_WEB_RES_CHECK = ControllerMappings.USS_WEB_ORDER + "/" + UrlsMappings.ORDER_TO_CHECK_RES_INFO_PAGE;

	public static final String BSS_INDEX_WEB = ControllerMappings.USS_WEB_BSS_BUSI+"/"+UrlsMappings.BSS_INDEX;

	public static final String IDCARD_INDEX_WEB = ControllerMappings.USS_WEB_IDCARD+"/"+UrlsMappings.IDCARD_INDEX;
	
	public static final String ORDER_FEE_INDEX_WEB = ControllerMappings.USS_WEB_ORDER_FEE+"/"+UrlsMappings.ORDER_FEE_INDEX;

	public static final String REPORT_AUDIT = ControllerMappings.USS_WEB_REPORTFORMS + "/" + UrlsMappings.REPORT_AUDIT;
	/*自助终端报表*/
	public static final String REPORT_AUDIT_AUTO = ControllerMappings.USS_WEB_REPORTFORMS + "/" + UrlsMappings.REPORT_AUDIT_AUTO;
	
	
	/*重庆版实名返档审核*/
	public static final String CQ_REAL_NAME_CHECK = ControllerMappings.NEED_AUTHORITY + "chongqing/" + UrlsMappings.GO_TO_REALNAME_CHECK;
	
	public static final String CQ_REAL_NAME_AUDIT = ControllerMappings.NEED_AUTHORITY + "chongqing/" + UrlsMappings.REAL_NAME_AUDIT; 
	/*实名返档报表查询*/
	public static final String REAL_NAME_REPORT = ControllerMappings.NEED_AUTHORITY + "chongqing/" + UrlsMappings.GO_TO_REALNAME_REPORT;
	public static final String REPORT_AUDIT_SP = ControllerMappings.USS_WEB_REPORTFORMS + "/" + UrlsMappings.REPORT_AUDIT_SP;
	
	public static final String REPORT_COVERTIFRAME = ControllerMappings.USS_WEB_REPORTFORMS + "/" + UrlsMappings.REPORT_COVERIFRAME;
	/*报表CS*/
	public static final String REPORT_AUDIT_CS = ControllerMappings.USS_WEB_REPORTFORMS + "/" + UrlsMappings.REPORT_AUDIT_CS;
	
	/*PC端开户*/
	public static final String OPEN_ACCOUNT_PC = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.OPEN_ACCOUNT_PC;
	
	/*智慧沃家开户*/
	public static final String WOJIA_INDEX = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.WOJIA_INDEX;
	
	/*2/3G转4G处理*/
	public static final String TRANSFER_23_TO_4 = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.TRANSFER_23_TO_4;
	
	/*PC端预订单处理*/
	public static final String PREDICT_ORDER_DEAL = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.PREDICT_ORDER_DEAL;
	/*宁夏版实名返档审核*/
	public static final String REAL_NAME_CHECK = ControllerMappings.NEED_AUTHORITY + "ningxia/" + UrlsMappings.GO_TO_REALNAME_CHECK;
	
	public static final String REAL_NAME_AUDIT = ControllerMappings.NEED_AUTHORITY + "ningxia/" + UrlsMappings.REAL_NAME_AUDIT; // YUN-437 GX_20150118_(宁夏版)实名返档页面(PC端)_杨枝蕃
	
	public static final String NX_CHARGE_PC = ControllerMappings.NEED_AUTHORITY + "ningxia/" + UrlsMappings.NINGXIA_CHARGE_PC; //YUN-626 NX_PC端：新增缴费功能_杨枝蕃

	public static final String NX_CHARGE_PC_NEW = ControllerMappings.NEED_AUTHORITY + "ningxia/" + UrlsMappings.NINGXIA_CHARGE_PC_NEW; //YUN-987 NX_PC端：缴费优化_张巍

	
	//重庆 
	public static final String TRANS_FER = ControllerMappings.WEB_REPORTFORMS + "/" + UrlsMappings.TRANS_FER;
	//客响报表
	public static final String INFO_CUST_CARE_ORDER = ControllerMappings.WEB_REPORTFORMS + "/" + UrlsMappings.INFO_CUST_CARE_ORDER;
	public static final String CHECK_INFO_CUST_CARE_ORDER = ControllerMappings.WEB_REPORTFORMS + "/" + UrlsMappings.CHECK_INFO_CUST_CARE_ORDER;
	//对账报表
	public static final String CHECK_CHARGE_LIST = ControllerMappings.WEB_REPORTFORMS + "/" + UrlsMappings.CHECK_CHARGE_LIST;
	/*稽核报表*/
	public static final String CQ_REPORT_AUDIT_CS = ControllerMappings.USS_WEB_REPORTFORMS + "/" + UrlsMappings.CQ_REPORT_AUDIT_CS;

	
	public static final String SHOW_WOORDER = ControllerMappings.USS_WEB_DEALSHOWOREDER + "/" + UrlsMappings.SHOW_WOORDER;
	
	public static final String CHECK_INFO_CUST_CARE_ORDER_PRINT = ControllerMappings.WEB_REPORTFORMS + "/" + UrlsMappings.CHECK_INFO_CUST_CARE_ORDER_PRINT;
	/*PC端缴费*/
	public static final String CQ_PAYMENT_PC = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.CQ_PAYMENT_PC;
	/*PC端销售录入管理*/
	public static final String HN_SALELIST_PC = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.HN_SALESLIST_PC;
	/*PC端佣金规则管理*/
	public static final String HN_COMMISSIONLIST_PC = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.HN_COMMISSIONLIST_PC;
	public static final String HN_COMMISSIONEDIT_PC = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.HN_COMMISSIONEDIT_PC;
	public static final String HN_COMMISSIONMODIFY_PC = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.HN_COMMISSIONMODIFY_PC;
	/*PC端自助佣金查询*/
	public static final String HN_SELFHELP_COMMISSIONLIST_PC = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.HN_SELFCOMMISSIONLIST_PC;
	/*PC端缴费日子查询*/
	public static final String CQ_QRYFEELOG_PC = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.CQ_QRYFEELOG_PC;
	
	/**重庆PC端综合变更**/
	public static final String MULTIPLE_CHANGE = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.MULTI_CHANGE;
	
	/*PC端产品活动维护*/
	public static final String PRODUCT_AND_ACTIVITY = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.PRODUCT_AND_ACTIVITY;
	
	/*自助终端视图*/
	public static final String AUTO_STATUS_LIST = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.AUTO_STATUS_LIST;

	
	/**联通缴费充值页面**/
	public static final String CHARGE_PC = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.CHARGE_PC;
	
	/**产品打包**/
	public static final String PRODUCT_PAGE = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.PRODUCT_PAGE;
	/**产品打包上下架**/
	public static final String PRODUCT_PAGE_UPANDDOWN = ControllerMappings.USS_WEB_INDEX + "/" + UrlsMappings.PRODUCT_PAGE_UPANDDOWN;
	
}
