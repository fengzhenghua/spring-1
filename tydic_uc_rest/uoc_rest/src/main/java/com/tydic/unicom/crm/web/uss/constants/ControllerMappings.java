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
 * @author yangfei 2014年9月12日 下午8:00:01
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

	//TODO

	public static final String USS_WEB_INDEX = NEED_AUTHORITY+"index";
	public static final String USS_WEB_CHOICE_OPER_NO = NEED_AUTHORITY+"choiceOperNo";
	//报表文件夹路径
	public static final String USS_WEB_REPORTFORMS = NEED_AUTHORITY + "reportForms";

	public static final String USS_WEB_CUSTOMER = NEED_AUTHORITY +"customer";

	public static final String USS_WEB_ORDER = NEED_AUTHORITY + "order";

	public static final String USS_WEB_WKPRINT = NEED_AUTHORITY + "wkprint";

	public static final String USS_WEB_QUERY_NUMBER = NEED_AUTHORITY+"index";

	public static final String USS_WEB_VERIFYCODE = NO_AUTHORITY+"verifyCode";
	/*bss busi*/
	public static final String USS_WEB_BSS_BUSI = NEED_AUTHORITY+"bssBusi";

	public static final String USS_WEB_IDCARD = NEED_AUTHORITY +"idcard";

	public static final String USS_WEB_ORDER_FEE = NEED_AUTHORITY +"orderFee";

	public static final String USS_WEB_DEALSHOWOREDER = NEED_AUTHORITY + "dealShowOrder";
	/*宁夏版实名返档审核*/
	public static final String USS_WEB_REALNAMECHECK = NEED_AUTHORITY + "realNameCheck";
	public static final String USS_CQ_REALNAMECHECK = NEED_AUTHORITY + "CqRealNameCheck";
	/**
	 * @Fields USS_WEB_SALE : TODO Web选择套餐
	 */
	public static final String USS_WEB_SALE = NEED_AUTHORITY + "sale";
	public static final String USS_WEB_REST_TO_CONTROLLER = NEED_AUTHORITY + "restToController";
	public static final String USS_REST_OPER = REST +"oper";

	public static final String USS_REST_ALIPAY = REST + "alipay";
	public static final String USS_REST_PAYCOMMON = REST + "payCommon";
	public static final String USS_WEB_ALIPAY = NEED_AUTHORITY + "alipay";

	public static final String USS_WEB_SELECT_NUMBER = NEED_AUTHORITY + "selectNumber";

	public static final String USS_WEB_SELECT_UNIFIEDLOGQUERY = NEED_AUTHORITY + "unifiedLogQuery";

	public static final String USS_WEB_NUMBER_DATA = NEED_AUTHORITY + "numberData";
	public static final String USS_REST_CODETYPE = REST +"codeType";
	/**
	 * 7	客户证件扫描信息查询
	 */
	public static final String USS_REST_ORDER_INFO = REST +"orderInfo";
	public static final String USS_REST_ORDER_UPDATE = REST +"orderUpdate";
	/**
	 * @Fields USS_REST_SALE : TODO REST选择套餐
	 */
	public static final String USS_REST_SALE= REST+"sale";
	/**
	 * @Fields USS_REST_SALE : TODO REST选择号码
	 */
	public static final String USS_REST_SELECT_NUMBER= REST+"selectNumber";

	/**
	 * @Fields USS_REST_SALE : TODO REST获取费用
	 */
	public static final String USS_REST_GET_FEE= REST+"getFee";
	/**
	 * USS_REST_CUSTOMER_VERIFY 客户信息校验
	 */
	public static final String USS_REST_CUSTOMER_VERIFY= REST+"customerVerify";

	/**
	 * USS_REST_CUSTOMER_VERIFY 客户信息校验
	 */
	public static final String USS_REST_CUSTOMER3G_VERIFY= REST+"customerVerify3G";
	/**
	 * USS_REST_SALE_OPEN pad端订单提交
	 */
	public static final String USS_REST_SALE_OPEN= REST+"saleOpen";
	/**
	 * USS_REST_SALE_REPORT app报表
	 */
	public static final String USS_REST_SALE_REPORT= REST+"saleReport";
	/**
	 * USS_REST_REGISTER 手机端店员注册
	 */
	public static final String USS_REST_REGISTER= REST+"register";
	/**
	 * USS_REST_REGISTER 手机端问卷调查
	 */
	public static final String USS_REST_QUESTIONNAIRE= REST+"questionnaire";
	/**
	 * USS_REST_GSALE_OPEN pad端3G订单提交
	 */
	public static final String USS_REST_GSALE_OPEN= REST+"saleOpen3G";

	/**
	 * rest缴费
	 */
	public static final String USS_REST_PAYMENTS = REST +"payment";

	/*资源验证*/
	public static final String ORDER_CHECK_RES_INFO = NEED_AUTHORITY+"checkResInfo";
	/*开户*/
	public static final String ACCOUNT_OPEN = NEED_AUTHORITY+"accountOpen";
	public static final String DO_ACCOUNT_OPEN = "doAccountOpen";
	public static final String DO_WOREST_ACCOUNT_OPEN = "doWoRestAccountOpen";
	public static final String DO_LANACCOUNT_OPEN = "doLanAccountOpen";
	public static final String ORDERSUB_PC = "orderSubpc";//pc端宽带单装正式提交
	/*4G为处理订单*/
	public static final String UNTREATED_ORDER_4G = REST+"untreatedOrder4G";
	/*4G为处理订单*/
	public static final String TERMINAL = REST+"terminal";
	public static final String GOODS_CHOICE = REST+"goodsChoice";
	/*订单赠款处理记录*/
	public static final String ORDER_GRANT_RECORD = REST+"orderGrantRecord";
	/*积分*/
	public static final String SCORE= REST + "score";
	/*微信实名认证*/
	public static final String WEI_XIN_AUDIT= REST + "weiXinAudit";
	/*签到*/
	public static final String SIGN= REST + "sign";
	/*消息中心*/
	public static final String INFO_MESSAGE_CENTER = REST+"infoMessageCenter";

	/*费用减免申请*/
	public static final String REASON_DISCOUNT = REST+"reason_discount";

	/*无纸化*/
	public static final String USS_REST_PAPERLESS = REST +"paperless";

	/*收费信息写属性表*/

	public static final String PAYMENT_INFO = REST+"paymentinfo";

	public static final String Specdinner_Change = REST +"specdinnerchange";
	/* 综合变更 */
	public static final String Integrated_Change = REST +"integratedChange";

	/*操作统计记录 */
	public static final String USS_REST_OPERATE_RECORD = REST + "operateRecord";

	public static final String PRODUCT = REST+"product";

	public static final String ORDER_DETAIL = REST + "get_order_detail";

	public static final String MEMCACHEDINFO = REST + "memcachedinfo";

	public static final String POSUPDATEINFO = REST + "posupdateinfo";

	public static final String USS_REST_GET_HEAT_OFR = REST + "getHeatCodeOfr";

	public static final String USS_WEB_EXCEPTION = NO_AUTHORITY + "error";

	public static final String CREATE_ORDER_ID = REST + "create_order_id";

	public static final String USIM = REST + "usim";

	public static final String USER = REST + "user";

	public static final String CUSTOMER = REST + "customer";
	/**
	 * 订单挂起
	 */
	public static final String HANGUP = REST + "hang_up";
	/**
	 * 订单取消
	 */
	public static final String CANCEL = REST +"cancel";
	/*宁夏全拿走*/
	public static final String ALLTAKE = REST +"allTake";
	/*营销活动*/
	public static final String USS_REST_MKT = REST +"mkt";
	/**
	 * 报表
	 */
	public static final String RPT_BUSI_LIST = NEED_AUTHORITY +"rptbusilist";
	public static final String RPT_TERMINAL_DETAIL = NEED_AUTHORITY +"rptTerminalDetail";
	public static final String RPT_SAMPLING_AUDIT = NEED_AUTHORITY +"rptSamplingAudit";
	public static final String RPT_MOBILE_NUMBER_LIST = NEED_AUTHORITY +"rptMobileNumberList";
	public static final String RPT_BUSI_FEE = NEED_AUTHORITY +"rptBusiFee";
	public static final String RPT_SELF_SERVICE = NEED_AUTHORITY +"rptSelfService";
	public static final String WBV_UTIL = NEED_AUTHORITY +"webUtil";

	public static final String  REPORT_CS = NEED_AUTHORITY +"ReportCs";

	public static final String  ACCOUNT_OPEN_PC = NEED_AUTHORITY+"accountOpen";

	public static final String  WOJIA_OPEN_PC = NEED_AUTHORITY+"woJiaOpen";

	public static final String  SALE_RECORD_PC = NEED_AUTHORITY+"saleRecord";

	public static final String  RULE_COMMISSION_PC = NEED_AUTHORITY+"ruleCommission";

	public static final String  RULE_QUOTA_PC = NEED_AUTHORITY+"ruleQuota";

	public static final String ACTIVITY_VIRTUAL_GROUP_PC = NEED_AUTHORITY+"activityVirtualGroup";

	public static final String  HN_COMMISSION_PC = NEED_AUTHORITY+"hnCommissionList";

	/**
	 *订单详询 页面营销活动
	 * **/
	public static final String USS_WEB_MKT = NEED_AUTHORITY + "mkt";
	public static final String QUERY_MKT ="querymkt";
	public static final String CHECK_MKT ="checkmkt";
	public static final String DOSUBMIT_MKT ="dosubmitmkt";
	/**
	 * 自助终端
	 */
	public static final String AUTO_SERVICE = "rest/selfservice";

	public static final String NINGXIA = REST + "nx";

	public static final String NINGXIA_CONTROLLER = NEED_AUTHORITY + "nx";

	/**
	 * 宽带新装
	 */
	public static final String LAN_ADDRESS = REST + "address";
	/**
	 * 手机端营销活动
	 */
	public static final String MKT_ACTIVITY = REST + "mktactivity";

	public static final String DO_WO_ACCOUNT_OPEN = "doWoAccountOpen";

	/*宁夏版实名返档审核*/
	/*微信实名认证*/
	/*实名自助返档报表查询*/
	public static final String USS_WEB_REALNAMEREPORT = NEED_AUTHORITY + "realNameReport";

	//19PAY
	public static final String ONE_9_PAY_REST = REST + "one9pay";
	//报表文件夹路径
	public static final String WEB_REPORTFORMS = NEED_AUTHORITY + "reportForms";
	//资金归集
	public static final String CAPITAL = REST + "capital";
	//第三方便民缴费
	public static final String USS_REST_THIRD = REST + "third";

	//流量订购
	public static final String USS_REST_TRAFFIC_ORDER = REST + "trafficOrder";

	//老用户销售业务提交
	public static final String USS_REST_OLDUSERPURCHASE_SUBMIT = REST + "OlduserPurchaseSubmit";

	//微信支付手机
	public static final String USS_REST_WXPAY = REST + "wxpay";

	//微信支付PC
	public static final String USS_WEB_WXPAY = NEED_AUTHORITY + "wxpay";
	/**
	 * m2536
	 * @Fields USS_REST_TARIFF_CHANGE_NUMBER : 资费变更
	 */
	public static final String USS_REST_TARIFF_CHANGE_NUMBER= REST+"tariffChange";
	/**
	 * m2536
	 * @Fields USS_REST_TARIFF_CHANGE_NUMBER : 资费变更
	 */
	public static final String USS_REST_THREE_PART_CHECK= REST+"threepartCheck";


	/**
	 * 流量加油包
	 */
	public static final String USS_REST_FLOWBAG_EX = REST + "flowbagEx";
	//串号校验
	public static final String CHECK_IMEI =   "checkIMEI";
	//开通接口
	public static final String OPEN_CONTRACT =   "openContract";
	//x99档位查询
	public static final String QUERY_X99LEVELS =   "queryX99levels";
	//x99档位查询
	public static final String QUERY_X99PRICE =   "queryX99Price";
	//补充协议生成
	public static final String UPLOAD_FORM_PDF_PREFERENTIAL ="uploadFormPdfPreferential";
	//预开户attr
	public static final String PRE_INFO_ORDER_ATTR_REST = REST +"PreInfoOrderAttr";

	public static final String RES_GSM_NUMBER_REST = REST +"ResGsmNumber";

	public static final String RULE_BUSINESS_CHARGE_REST = REST +"RuleBusinessCharge";

	public static final String PRE_RULE_BUSINESS_CHARGE_REST = REST +"PreRuleBusinessCharge";

	/**银联商务全民付线下版**/
	public static final String UNION_PAY_QMF = REST + "unionPayQMF";

	public static final String COMMISSION =REST + "commission";

	//沃创富相关服务
	public static final String USS_REST_WCF = REST + "wcf";
	//招行密码
	public static final String CODE_TOOL = REST + "codeTool";

	//沃家庭
	public static final String USS_WEB_WJT = NEED_AUTHORITY + "wjt";
	/**重庆PC端综合变更**/
	public static final String MILTIPLE_CHANGE = NEED_AUTHORITY + "index";

	//组织机构查询
	public static final String DEPARTMENT_MANAGE = NEED_AUTHORITY + "departmentManage";

	public static final String TRANSFER_23_TO_4 = NEED_AUTHORITY + "transfer23To4";

	public static final String GET_DEVICENUMBER =  "getDeviceNumber";
	public static final String TRANSFER_23_TO_4_OPER =  "transfer23To4Oper";
	public static final String ADD_INFOORDERATTER_INFOODER =  "addInfoOrderAttrAndInfoOrder";

	/**页面响应时间**/
	public static final String PAGE_RSP_TIME = REST + "pageRspTime";
	public static final String PAGE_RSP_TIME_PC = NEED_AUTHORITY + "pageRspTime";

	//产品活动维护ql
	public static final String PRODUCT_AND_ACTIVITY  =NEED_AUTHORITY+"productAndActivity";
	//代理商接收验证码
	public static final String USS_REST_AVR = REST+"agentVerifyRelation";
	//自助机终端视图
	public static final String AUTO_STATUS_LIST = NEED_AUTHORITY+"autoStatusList";

	public static final String QUR_ALL_NUMBER_LIST = "qryAllNumberList";

	public static final String GET_ALL_NUMBER_LIST = "getAllNumberList";

	public static final String CHK_ADDRESS_INFO = "checkAddressInfo";
	/**
	 * m2536
	 * @Fields AOP_PAYMENT : aop联通缴费
	 */
	public static final String AOP_PAYMENT= REST+"aopPayment";

	public static final String AOP_CHK_23TO4 = "aopCheck23to4";

	/**
	 * @Fields AOP_PAYMENT : 老用户合约办理
	 */
	public static final String OLDUSER_CONTRACT= REST+"oldUserContract";

	//产品打包
	public static final String PRODUCT_WEB_PAGE = NEED_AUTHORITY + "productPage";

	/**沃易得rest接口**/
	public static final String WYD_REST = REST + "WydRest";

	/**海南产品活动rest接口**/
	public static final String HN_PRODUCT_REST = REST + "hnProductRest";


	/**常见的问题项目与对应问题的解答（FAQ） **/
	public static final String SOLVE_FAQ_REST = REST + "FaqRest";

	/**在线帐号信息 **/
	public static final String INFO_ONLINE_ACCOUNT = REST + "onlineAccount";

	public static final String DEMO_ATTR_DEFINE_REST = REST + "demoAttrDefineRest";

	public static final String TEST_REST = REST + "testRest";

	public static final String XHYTEST_REST = REST + "xhyTestRest";
	//服务订单模板
	public static final String SERVICE_ORDER_REST = REST + "ordModServiceRest";
	//流程模板服务
	public static final String PROC_MOD_SERVICE = REST + "procModService";
	//销售订单
	public static final String SALE_ORDER_REST = REST + "saleOrder";
	//登录操作信息
	public static final String OPER_REST = REST + "oper";

	//人工任务
	public static final String ARTIFICIAL_TASK_REST = REST + "ArtificialTaskRest";

	//订单中心对外能力平台接口AbilityPlatformForYunWoRest
	public static final String ORDER_CENTER_REST = REST + "OrderCenter";

	//订单中心对外能力平台接口AbilityPlatformForYunWoRest
	public static final String EXPRESS_SERVICE = REST + "expressService";

	//获取可选环节信息，地区信息，可选工号信息等
	public static final String GET_INFO_SERVICE = REST + "getInfoService";

	//物流相关服务
	public static final String LOGISTICS_SERVICE = REST + "logisticsService";

	//信息校验
	public static final String CHECK_INFO_REST  = REST + "checkInfoRest";

	//任务逾期率统计
	public static final String STATISTICAL_SERVICE = REST+"StatisticalService";

	//订单撤销
	public static final String ORDER_CANCEL_APPLY = REST+ "orderCancelApplyRest";

	//系统服务
	public static final String SYSTEM_SERVICE_REST = REST + "systemServiceRest";

	//写卡结果记录、查询接口
	public static final String WRITE_CARD_RESULT_REST = REST+ "writeCardResult";

	// 赠款
	public static final String GRANT_FEE_PAY_REST = REST + "grantFeePay";
	//服务订单报表
	public static final String EXPROT_ORDER_INFO_REST = REST + "exprotOrderRest";
}
