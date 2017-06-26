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
 * @author yangfei 2014年9月12日 下午8:01:17
 * @ClassName UrlsMappings
 * @Description 用于Controller方法的URL常量
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月12日
 * @modify by reason:{方法名}:{原因}
 */
public class UrlsMappings {

	public static final String SELECT_CUSTOMER_INFO = "customerInfo";

	/** 常量------------------------------------------------------------------ */

	public static final String INDEX = "index";

	public static final String HOME = "home";

	public static final String CHOICE_OPER_NO = "choiceOperNo";
	public static final String CHOICE_SUB_OPER_INFO = "choiceSubOperInfo";
	public static final String GET_SUB_OPER_INFO = "getSubOperInfo";
	public static final String GET_AUTHORITYIDS = "getAuthorityIds";

	public static final String FICTITIOUS_REGISTER = "fictitiousRegister";
	public static final String WOACCOUNT_REGISTER = "WoAccountRegister";
	public static final String ENTITY_REGISTER = "entityRegister";
	public static final String QRY_PAY_TYPE = "qryPayType";
	public static final String QRY_OPER_NO = "qryOperNo";
	public static final String QRY_UCODE = "qryUcode";
	public static final String CHECK_IDENTITYNUMBER_ACCOUNTNUMBER = "checkIdentityNumberAccountNumber";
	public static final String CUSTOMER = "customerServ";
	/**
	 * @Fields GET_QUESTIONNAIRE_USE_INCLINATION : TODO 获取问卷调查推荐业务
	 */
	public static final String GET_QUESTIONNAIRE_RECOMMEND_PRODUCT = "getQuestionnaireRecommendProduct";

	/**
	 * @Fields GET_QUESTIONNAIRE_USE_INCLINATION : TODO 获取问卷调查问题与选项
	 */
	public static final String GET_QUESTIONNAIRE_QUESTION_OPTION = "getQuestionnaireQuestionAndOption";
	/**
	 * @Fields GET_QUESTIONNAIRE_USE_INCLINATION : TODO 获取问卷调查推荐业务详情
	 */
	public static final String GET_QUESTIONNAIRE_RECOMMEND_PRODUCT_INFO = "getQuestionnaireRecommendProductInfo";
	/**
	 * 登出 2014年9月18日17:39:06 haichao
	 */
	public static final String LOG_OUT = "logout";

	/**
	 * 登录
	 */
	public static final String LOGIN = "login";
	public static final String LOGINESS = "loginEss";


	public static final String SHOW_NUMBER_INFO = "showNumberInfo";
	/**
	 * @Fields SALE_SELECTED_CODE : TODO 销售-选择套餐
	 */
	public static final String SALE_SELECTED_CODE = "saleSelectedCode";

	/**
	 * @Fields GET_GROUP_SALE_CODE : TODO 组合销售
	 */
	public static final String GET_GROUP_SALE_CODE ="getGroupSaleCode";


	/**
	 * @Fields GET_PRODUCT_BAG : TODO 销售-查询套餐包
	 */
	public static final String GET_PRODUCT_BAG = "getProductBag";

	/**
	 * @Fields GET_DEFAULT_BAG_FOR_PRODUCT : TODO 查询产品下默认套餐
	 */
	public static final String GET_DEFAULT_BAG_FOR_PRODUCT = "getDefaultBagForProduct";

	/**
	 * @Fields SALE_SELECTED_CODE : TODO 销售-选择套餐
	 */
	public static final String SAVE_SIGN_INFO = "saveSignInfo";
	/**
	 * @Fields SALE_SELECTED_CODE : TODO 销售-选择套餐
	 */
	public static final String GET_SIGN_INFO = "getSignInfo";

	/**
	 * @Fields SALE_SELECTED_CODE : TODO 销售-选择2G套餐和活动
	 */
	public static final String SALE_SELECTED_CODE_2G = "saleSelectedCode2G";
	/**
	 * @Fields SALE_SELECTED_CODE : TODO 销售-选择3G套餐和活动
	 */
	public static final String SALE_SELECTED_CODE_3G = "saleSelectedCode3G";
	/**
	 * @Fields SALE_SELECTED_CODE : TODO 销售-选择4G套餐和活动
	 */
	public static final String SALE_SELECTED_CODE_4G = "saleSelectedCode4G";
	/**
	 * @Fields GET_SALE_SELECTED_CODE : TODO
	 */
	public static final String GET_SALE_SELECTED_CODE = "getSaleSelectedCode";
	/**
	 * @Fields GET_VERIFY_CODE : TODO 获得验证码
	 */
	public static final String GET_VERIFY_CODE = "getVerifyCode";

	/**
	 * @Fields GET_SESSION_ID : TODO 获得SESSIONID
	 */
	public static final String GET_SESSION_ID = "getSessionId";

	public static final String SALE_INDEX = "saleIndex";
	public static final String SALE_INDEX_NEW= "saleIndexNew";
	public static final String OFR_CODE_CONFIG= "ofrCodeConfig";
	public static final String OFR_QUERY_CONFIG= "ofrCodeUpdate";

	public static final String GET_DEPTINFOLIST = "getDeptInfoList";
	public static final String GET_OPERINFOLIST = "getOperInfoList";

	public static final String DEPARTMENT_MANAGE = "departmentManage";
	public static final String NUMBER_DATA= "numberData";
	public static final String PREFECTURE_SET_MEAL = "prefectureSetMeal";
	public static final String TRANSFER_23_TO_4  ="transfer23To4";
	public static final String OPEN_ACCOUNT_PC  ="openAccountPC";
	public static final String WOJIA_INDEX  ="wojiaIndex";
	public static final String ZONG_HE_BIAN_GENG  ="zongheBiangeng";
	public static final String CQ_PAYMENT_PC  ="cqPaymentPC";
	public static final String HN_SALESLIST_PC  ="hnSalesList";
	public static final String HN_COMMISSIONLIST_PC  ="hnCommissionList";
	public static final String HN_SELFCOMMISSIONLIST_PC  ="hnSelfHelpCommissionList";
	public static final String HN_COMMISSIONEDIT_PC  ="hnCommissionEdit";
	public static final String HN_COMMISSIONMODIFY_PC  ="hnCommissionModify";
	public static final String CQ_QRYFEELOG_PC  ="unifiedLogQuery";

	public static final String PREDICT_ORDER_DEAL  ="predictOrderDeal";

	public static final String PREFECTURE_ACTIVITY = "prefectureActivity";

	public static final String QUERY_NUMBER = "queryNumber";
	public static final String MOVE_TERMINAL = "moveTerminal";
	public static final String PARTS_AREA = "partsArea";
	public static final String GROUP_BUSINESS = "groupBusiness";

	public static final String ORDER_PROCESSING = "orderProsessing";
	public static final String ORDER_PROCESSING_PAYFEE = "orderProcessingPayFee";
	public static final String OPEN_ACC_WKPRING = "openAccWkprint";
	public static final String OPEN_WOJIA_WKPRINT = "openWojiaWkprint";
	public static final String OPEN_WJT_WKPRING = "openWjtWkprint";
	public static final String ARCHIVES_WKPRINT = "archivesWkprint";
	public static final String TRANSFER23TO4_WKPRINT = "transfer23To4Wkprint";
	public static final String TERMS_GOOD_WKPRING = "termsGoodWkprint";
	public static final String TERMS_PREFERENTIAL_WKPRING = "termsPreferential";
	public static final String TERMS_M165_WKPRING = "termsM165Wkprint";
	public static final String TERMS_LOCAL = "termsLocal";
	public static final String WZH_PRINT = "wzhPrint";

	public static final String SHOW_ORDER = "showOrder";
	public static final String SHOW_ORDER_FEE = "showOrderFee";
	public static final String SHOW_ORDER_CHANGE = "showOrderChange";
	public static final String SHOW_ORDER_ARCHIVES = "showOrderArchives";
	public static final String GET_LAST_DAY_OF_NEXT_MONTH = "getLastDayOfNextMonth";
	public static final String SUBMIT_ORDER = "submitOrder";
	public static final String GET_DEV_ORDER_REL = "getDevOrderRel";
	public static final String DEAL_GIVE = "dealGive";
	public static final String SHOW_WOORDER = "woOrderDetail";
	public static final String SUBMIT_WOORDER = "submitWoOrder";
	/**
	 * @Fields GET_SESSION_ID : TODO bss选号
	 */
	public static final String USS_WEB_SELECT_NUMBER = "selectNumber";

	public static final String USS_WEB_SALE_OPEN = "saleOpen";

	/**
	 * 身份证录入
	 */
	public static final String USS_WEB_IDCARD_ADD = "addIdCard";
	public static final String USS_WEB_MOBILE_IDCARD_ADD = "mobileAddIdCard";
	/**
	 * 订单提交
	 */
	public static final String USS_WEB_SALE_OPEN1 = "addOrderSubmit";
	/**
	 * 订单取消
	 */
	public static final String USS_WEB_SALE_CANCEL = "cancelOrder";

	public static final String USS_REST_SALE_OPEN = "orderSubmit";

	public static final String USS_WEB_SELECT_NUMBER_DATA = "selectNumberData";
	public static final String USS_WEB_GET_FEE_DATA = "getFeeData";
	public static final String USS_WEB_SELECT_NUMBER_DATA_2G = "selectNumberData";// rest 2G选号
	public static final String GET_MON_LIMIT_BY_NUMBER = "getMonLimitByNumber";
	public static final String WX_AUDIT_QUERY_NUMBER = "WXAuditQueryNumber";
	public static final String CQ_WX_AUDIT_QUERY_NUMBER = "CqWXAuditQueryNumber";
	public static final String UPLOAD_ID_CARD = "uploadIdcard";
	public static final String WX_AUDIT_SUBMIT = "WXAuditSubmit";
	public static final String CQ_WX_AUDIT_SUBMIT = "CqWXAuditSubmit";
	public static final String WX_NAME_AND_ID_VERIFT = "wxNameAndIDVerify";
	public static final String WX_AUDIT_VERIFY_CODE = "WXAuditVerifyCode";
	public static final String CQ_WX_AUDIT_VERIFY_CODE = "CqWXAuditVerifyCode";
	public static final String USS_WEB_SELECT_NUMBER_DATA_3G = "selectNumberData3G";// rest 3G选号
	public static final String USS_WEB_SELECT_NUMBER_DATA_SH = "selectNumberDataSh";
	public static final String USS_WEB_GET_FEE_DATA_SH = "getFeeDataSh";

	public static final String USS_WEB_SELECT_NUMBER_OCCUPY = "numberOccupy";

	public static final String USS_WEB_SELECT_NUMBER_OCCUPY_2G = "numberOccupy";// rest 号码预占

	public static final String USS_WEB_SELECT_NUMBER_OCCUPY_3G = "numberOccupy3G";// rest 3G号码预占

	public static final String USS_WEB_SELECT_NUMBER_OCCUPY_SH = "numberOccupySh";
	/* 查询需要处理的订单 */
	public static final String QUERY_ORDER_PROSESSING = "queryOrderProsessing";
	public static final String QUERY_ORDER_PROSESSING_FEE = "queryOrderFeeProsessing";
	public static final String QUERY_NUMBER_DATA = "queryNumberData";
	public static final String QUERY_ORDER_PROSESSING_FEE_POS = "queryOrderFeeProsessingPos";
	public static final String UPDATE_INFO_ORDER_PAY_FLAG = "updateInfoOrderPayFlag";
	/* REST 获得code_list */
	public static final String CODELIST = "codeList";
	/* 客户信息校验 */
	public static final String USS_WEB_CHECKCUSTINFO = "checkCustInfo";
	public static final String USS_WEB_CHECKCUSTINFO_SH = "checkCustInfoSh";
	/* PAD端客户资料和Fee保存 */
	public static final String USS_WEB_SAVE_CUSTANDFEE_INFO = "saveCustAndFeeInfo";
	/* 4G 成卡 卡校验 */
	public static final String USS_WEB_CARD_VERIF = "cardVerif";
	/* PAD端客户资料保存 */
	public static final String USS_WEB_SAVE_CUST_INFO = "saveCustInfo";
	/* PAD端Fee保存 */
	public static final String USS_WEB_SAVE_FEE_INFO = "saveFeeInfo";
	/* 订单缴费 */
	public static final String ORDER_PROCESSING_PAY_SUBMIT = "orderProPaySub";

	public static final String UPDATE_ORDER_FEE_SUB = "updateOrderFeeSub";
	public static final String UPDATE_ORDER_FEE = "updateOrderFee";
	/* 展示Dialog */
	public static final String SHOWDIALOG = "showdialog";

	public static final String USS_REST_CHECKCUSTINFO = "checkCustInfo";

	/* 待收费订单缴费 */
	public static final String ORDER_PROCESSING_PAY = "orderProcessingPay";
	public static final String ORDER_PROCESSING_PAY_FEE = "orderProcessingPayFee";
	/* 资源验证 */
	public static final String ORDER_TO_CHECK_RES_INFO_PAGE = "orderProcessingCard";
	public static final String ORDER_TO_CHECK_RES_INFO = "toCheckResInfo";
	public static final String ORDER_DO_CHECK_RES_INFO = "doCheckResInfo";
	public static final String ORDER_GET_CARD_DATA = "getCardData";
	public static final String ORDER_CARD_NOTIFY = "cardNotify";
	/**
	 * @Fields USS_WEB_GET_TELPHONE : TODO 选择购机入网送话费后显示的 可选活动和其他信息。（普通）
	 */
	public static final String USS_WEB_GET_TELPHONE = "getTelphone";

	/**
	 * @Fields USS_WEB_ACTIVITY_DETAIL : TODO 选择可选活动后显示活动描述（普通）
	 */
	public static final String USS_WEB_ACTIVITY_DETAIL = "getActivitDteail";

	/**
	 * @Fields USS_WEB_NO_SELF_IPHONE_ACTIVITY_TYPE : TODO 获取活动类型（iphone）(选择套餐是触发)
	 */
	public static final String USS_WEB_NO_SELF_IPHONE_ACTIVITY_TYPE = "getNoSelfIphoneActivitType";

	/**
	 * @Fields USS_WEB_NO_SELF_IPHONE_ACTIVITY_TYPE_2 : TODO 获取活动类型（iphone）
	 */
	public static final String USS_WEB_NO_SELF_IPHONE_ACTIVITY_TYPE_NORMAL = "getNoSelfIphoneActivitTypeNormal";
	/**/
	public static final String UNTREATED_ORDER_INFO_4G = "getUntreatedOrderInfo4G";
	/**/
	public static final String UNPAY_ORDER_INFO = "getUnPayOrderInfo";
	/**/
	public static final String QRY_TERMINAL_STOCK = "qryTerminalStock";
	/**/
	public static final String UPDATE_TERMINAL_ALLOT_STOCK = "updateTerminalAllotStock";//hww
	/**/
	public static final String QRY_TERMINAL_SELECT_STOCK = "qryTerminalSelectStock";//hww
	/**/
	public static final String START_CHOICE_GOODS_BY_ANSWER_QUESTION = "startChoiceGoodsByAnswerQuestion";
	/**/
	public static final String GET_QUESTION = "getQuestion";
	/**/
	public static final String EXECUTE_CHOICE_GOODS_BY_ANSWER_QUESTION = "executeChoiceGoodsByAnswerQuestion";
	/**/
	public static final String QRY_TERMINAL_PIC_BY_BRAND = "qryTerminalPicByBrand";
	/**/
	public static final String QRY_TERMINAL_ATTR_BY_BRAND = "qryTerminalAttrByBrand";
	/**/
	public static final String QRY_BSS_TERMINAL_STOCK = "qryBSSTerminalStock";
	/**/
	public static final String RESPONSE_TERMINAL_ALLOT = "responseTerminalAllot";
	/**/
	public static final String GET_TERMINAL_INFO_BY_TERMINAL_ID = "getTerminalInfoByTerminalId";
	/**/
	public static final String CHANGE_FOR_TERMINAL_ALLOT = "changeForTerminalAllot";
	/**/
	public static final String ORDER_GRANT_RECORD = "getOrderGrantRecord";
	/**/
	public static final String UPDATE_ORDER_GRANT_RECORD = "updateOrderGrantRecord";

	/**
	 * @Fields USS_WEB_GET_SALE_SELECTED_CODE_3G : TODO 获取3G的套餐，普通
	 */
	public static final String USS_WEB_GET_SALE_SELECTED_CODE_3G_COM = "getSaleSelectedCode3GCom";

	/**
	 * @Fields USS_WEB_GET_SALE_SELECTED_CODE_3G : TODO 获取3G的套餐，iphone
	 */
	public static final String USS_WEB_GET_SALE_SELECTED_CODE_3G_IHPONE = "getSaleSelectedCode3GIphone";

	/**
	 * @Fields USS_WEB_GET_SALE_SELECTED_CODE_3G : TODO 获取3G的套餐，终端
	 */
	public static final String USS_WEB_GET_SALE_SELECTED_CODE_3G_CONSOLE = "getSaleSelectedCode3GConsole";

	/**
	 * @Fields USS_WEB_GET_SALE_SELECTED_CODE_3G_REST : TODO rest 获取3G的套餐（普通，iPhone 终端）
	 */
	public static final String USS_WEB_GET_SALE_SELECTED_CODE_3G_REST = "getSaleSelectedCode3GRest";

	/**
	 * @Fields USS_WEB_CHECK_TERMINAL_REST : TODO 终端校验和终端预占
	 */
	public static final String USS_WEB_CHECK_TERMINAL_REST = "checkTerminalRest";
	/**
	 * 4G订单处理结果日志
	 */
	public static final String ORDER_PROCESS_LOG_4G = "orderProcessLog4G";
	/**
	 * 7	客户证件扫描信息查询
	 */
	public static final String IDCARD_INFO = "idcardInfo";

	public static final String NEXT_STEP_PAGE_UPDATE_ORDERATTR_BY_PARAMS = "nextStepPageUpdateOrderAttrByParams";
	/**
	 * 生成订单。9	订单提交
	 */
	public static final String ORDER_INFO_COMM = "orderInfoSubmit";

	/**
	 * 资费变更订单提交。
	 */
	public static final String  TARRIF_CHANGE_ORDER_SUBMIT= "tarrifChangeOrderSubmit";

	/**
	 * 	10	订单更新接口
	 */
	public static final String ORDER_INFO_ATTR_UPDATE = "orderInfoAttrUpdate";
	/**
	 * pos机查询订单金额
	 */
	public static final String POS_QUERY_ORDER_AMOUNT = "posQueryOrderAmount";
	/**
	 * pos机缴费成功
	 */
	public static final String POS_SUCCESS_ORDER_FEE = "posSuccessOrderFee";
	/**
	 * 		更新代办人信息updateCommissionAgent
	 */
	public static final String UPDATE_COMMISSION_AGENT = "updateCommissionAgent";
	/**
	 * 	查询积分
	 */
	public static final String GET_INTEGERAL_INFO = "getIntegralInfo";
	/**
	 * 		提交订单
	 */
	public static final String ORDER_INFO_UPDATE_STATUS = "orderInfoStatusUpdate";

	/**
	 * 订单查询
	 */
	public static final String GET_ORDERS_BY_OPERID = "getOrdersByOperId";
	/**
	 * @Fields USS_WEB_GET_NOSELF_IPHONE_GUARANTEE_TYPE : TODO 获取iPhone 预存话费送手机---》 担保类型
	 */
	public static final String USS_WEB_GET_NOSELF_IPHONE_GUARANTEE_TYPE = "getNoSelfIphoneGuaranteeType";

	/**
	 * @Fields USS_GET_NO_SELF_IPHONE_ACTIVITY : TODO 获取iphone 预存话费送手机---》无担保---》可选活动
	 */
	public static final String USS_GET_NO_SELF_IPHONE_ACTIVITY = "getNoSelfIphoneOptionalActivity";

	/**
	 * @Fields USS_GET_TERMINAL_NUM_CHECK : TODO 获取终端信息（普通）
	 */
	public static final String USS_GET_TERMINAL_NUM_CHECK = "getTerminalNumCheck";

	/**
	 * @Fields USS_SET_NORMAL_COMBO : TODO 设置普通套餐
	 */
	public static final String USS_SET_NORMAL_COMBO = "setNormalCombo";

	/**
	 * @Fields USS_SET_IPHONE_COMBO : TODO 设置Iphone套餐
	 */
	public static final String USS_SET_IPHONE_COMBO = "setIphoneCombo";

	/**
	 * @Fields USS_SET_TERIMINAL_COMBO : TODO 设置终端套餐
	 */
	public static final String USS_SET_TERIMINAL_COMBO = "setTeriminalCombo";

	/**
	 * @Fields USS_GET_TERIMINAL_IPHONE_NUM_CHECK : TODO iphone---非自备机入网---读取终端串号。
	 */
	public static final String USS_GET_TERIMINAL_IPHONE_NUM_CHECK = "getTerminalIphoneNumCheck";

	/**
	 * @Fields USS_GET_NO_SELF_IPHONE_PRESTORE : TODO iphone---》非自备机入网---》购机入网送话费---》活动担保类型和可选活动。
	 */
	public static final String USS_GET_NO_SELF_IPHONE_PRESTORE = "getNoSelfIphonePrestore";

	/**
	 * @Fields USS_GET_TERMINALSELF_IPHONE_NUM_CHECK : TODO Iphone---》非自备机入网---》购机入网送话费---》终端校验。
	 */
	public static final String USS_GET_TERMINALSELF_IPHONE_NUM_CHECK = "getTerminalSelfIphoneNumCheck";

	/**
	 * @Fields USS_WEB_GET_IPHONE_SELFPHONE_INFO : TODO Iphone---》自备机入网----》获取自备机类型，是否参加合约计划，可选活动。
	 */
	public static final String USS_WEB_GET_IPHONE_SELFPHONE_INFO = "getIphoneSelfphoneInfo";

	/**
	 * @Fields USS_GET_IPHONE_SELF : TODO Iphone 自备机入网 终端校验
	 */
	public static final String USS_GET_IPHONE_SELF = "getTerminalIphoneSelf";

	/**
	 * @Fields USS_GET_TEL_ACTIVITS : TODO 普通:--->参加合约计划--->活动类型：购手机入网送话费--->具体活动的详细描述
	 */
	public static final String USS_GET_TEL_ACTIVITS = "getTelActivits";

	/**
	 * @Fields USS_GET_NOSELF_IPHONE_DESCRIBE_ACTIVITY : TODO 3g iphone 非自备机入网 预存话费送手机---》设置可选活动
	 */
	public static final String USS_GET_NOSELF_IPHONE_DESCRIBE_ACTIVITY = "getNoSelfIphoneDescribeActivity";

	/**
	 * @Fields GET_TERMINAL_NO_SELF_ACTIVITY_TYPE : TODO 3G 终端 非自备机入网－－－》获取活动类型(选择套餐是触发)
	 */
	public static final String GET_TERMINAL_NO_SELF_ACTIVITY_TYPE = "getTerminalNoSelfActivityType";
	/**
	 * @Fields GET_TERMINAL_NO_SELF_ACTIVITY_TYPE : TODO 3G 终端 非自备机入网－－－》获取活动类型
	 */
	public static final String GET_TERMINAL_NO_SELF_ACTIVITY_TYPE_NORMAL = "getTerminalNoSelfActivityTypeNormal";

	/**
	 * @Fields GET_TERMINAL_NO_SELF_GURA_ACT : TODO 3G 终端 非自备机入网－－－》获取担保类型和可选活动
	 */
	public static final String GET_TERMINAL_NO_SELF_GURA_ACT = "getTerminalNoSelfGuaranteeAndActivityName";

	/**
	 * @Fields SET_TERIMINAL_NO_SELF_ACTIVITY_NAME : TODO 3G 终端 非自备机入网－－－》向Memcache设置可选活动
	 */
	public static final String SET_TERIMINAL_NO_SELF_ACTIVITY_NAME = "setTeriminalNoSelfActivityName";

	/**
	 * @Fields GET_TERIMINAL_NO_SELF_CHECK_TERID : TODO 3G 终端 非自备机入网－－－》校验终端串号
	 */
	public static final String GET_TERIMINAL_NO_SELF_CHECK_TERID = "getTeriminalNoSelfCheckTeriminalId";

	/**
	 * @Fields GET_TERIMINAL_SELF_PHONE_INFO : TODO 3G 终端 自备机入网－－－》获取 自备机类型，参加合约计划，和可选活动信息
	 */
	public static final String GET_TERIMINAL_SELF_PHONE_INFO = "getTeriminalSelfPhoneInfo";

	/**
	 * @Fields GET_TERIMINAL_SELF_PHONE_CHECK_TERIMINAL : TODO 3G 终端 自备机入网－－－》校验终端串号
	 */
	public static final String GET_TERIMINAL_SELF_PHONE_CHECK_TERIMINAL = "getTeriminalSelfPhoneCheckTeriminalId";

	/**
	 * @Fields GET_TERMINAL_NO_SELF_PRESTORED_GA : TODO 3G 终端 非自备机入网--->预存话费送手机－－－》获取担保类型
	 */
	public static final String GET_TERMINAL_NO_SELF_PRESTORED_GUARANTEE = "getTerminalNoSelfPreStoredGuarantee";

	/**
	 * @Fields GET_TERMINAL_NO_SELF_PRESTORED_ACTIVITY_NAME : TODO 3G 终端 非自备机入网--->预存话费送手机－－－》担保类型--->获取可选活动
	 */
	public static final String GET_TERMINAL_NO_SELF_PRESTORED_ACTIVITY_NAME = "getTerminalNoSelfPreStoredActivityName";

	/**
	 * @Fields SET_TERMINAL_NO_SELF_PRESTORED_ACTIVITY_NAME : TODO 3G 终端 非自备机入网--->预存话费送手机－－－》担保类型--->设置可选活动
	 */
	public static final String SET_TERMINAL_NO_SELF_PRESTORED_ACTIVITY_NAME = "setTerminalNoSelfPreStoredActivityName";

	/**
	 * @Fields GET_TERIMINAL_NO_SELF_PRESTORED_CHECK_ID : TODO 3G 终端 非自备机入网》－－－》预存话费送手机－－－》校验终端串号
	 */
	public static final String GET_TERIMINAL_NO_SELF_PRESTORED_CHECK_ID = "getTeriminalNoSelfPreStoredTCheckTeriminalId";

	/**
	 * @Fields GET_TERMINAL_NO_SELF_ACTIVITY_NAME : TODO 3G 终端 非自备机入网》－－－》购机入网送话费－－－》担保类型---》获取可选活动
	 */
	public static final String GET_TERMINAL_NO_SELF_ACTIVITY_NAME = "getTerminalNoSelfActivityName";

	public static final String QUERYCUSTOMERMESS = "queryCustomerMess";

	public static final String SELECTADDRONLINECQ = "selectAddrOnLineCq";

	public static final String SELECTRULEAREAHX = "selectRuleAreaHx";

	public static final String SELECTADDRONLINENX = "selectAddrOnLineNx";

	public static final String QUERYBALANCECQ = "queryBalanceCQ";
	public static final String QUERYUSERPRODUCTCQ = "queryUserProductCq";
	public static final String GETPRODUCTBAGCQ = "getProductBagCq";

	public static final String QUERYBROANDINFO = "queryBroadbandInfo";

	public static final String QUERYCUSTOMERCQMESS = "queryCustomerCQMess";

	public static final String QUERYUNIFIEDLOGQUERY = "queryUnifiedLogQuery";

	public static final String QUERYBUSSINFOCQMESS = "queryBussInfoCQMess";

	public static final String QUERYPREORDERSCQ = "queryPreOrdersCq";

	public static final String QUERYPREORDERATTRCQ = "queryPreOrderAttrCq";

	public static final String UPDATEPREORDERSTATUSCQ = "updatePreOrderStatusCq";

	public static final String PAYMENTINFOCQ = "paymentInfoCQ";

	public static final String ORDERDO = "orderdo";

	public static final String PAYMENTBILL = "paymentbill";


	public static final String PAYMENTORDERBILL = "paymentorderbill";
	/**
	 * @Fields USS_WEB_GET_ACTIVITY_TYPE : TODO 获取活动类型
	 */
	public static final String USS_WEB_GET_ACTIVITY_TYPE = "getActivityType";
	/**
	 * @Fields USS_WEB_GET_ACTIVITY_TYPE : TODO 获取所有活动类型
	 */
	public static final String USS_WEB_GET_ALL_ACTIVITY_TYPE = "getAllActivityType";
	/**
	 * @Fields USS_WEB_GET_GUARANTEE_TYPE : TODO 获取担保类型
	 */
	public static final String USS_WEB_GET_GUARANTEE_TYPE = "getGuaranteeType";

	/**
	 * 获取可选活动
	 */
	public static final String USS_WEB_GET_ACTIVITY_NAME = "getActivityName";// USS_WEB_GET_ACTIVITY_NAME_NO_TERMINAL
	/**
	 * 获取无终端可选活动
	 */
	public static final String USS_WEB_GET_ACTIVITY_NAME_NO_TERMINAL = "getActivityNameNoTerminal";
	/**
	 * 获取品牌
	 */
	public static final String USS_WEB_GET_TERMINAL_BRAND = "getTerminalBrand";
	/**
	 * 获取型号
	 */
	public static final String USS_WEB_GET_TERMINAL_MODEL = "getTerminalModel";
	/**
	 * 获取可活动信息 4G 没有终端情况
	 */
	public static final String USS_WEB_GET_ACTIVITY_INFOS = "getActivityInfos";
	/**
	 * 获取活动信息
	 */
	public static final String USS_WEB_GET_ACTIVITY_INFO = "getActivityInfo";

	//（海南）根据套餐和活动类型选默认活动
	public static final String USS_WEB_GET_RULE_PRODUCT_ACTIVITY = "getRuleProductActivity";

	/**
	 * 校验终端信息
	 */
	public static final String CHECK_TERMINAL = "CheckTerminal";
	/*用户状态等级查询*/
	public static final String QUERY_USER_STATUS_INFO ="queryUserStatusInfo";

	/*用户消费详情查询*/
	public static final String QUERY_USER_CONSUME_DETAIL ="queryConsumeDetail";

	/*身份证升位*/
	public static final String CUSTOMER_ID_15TO18 ="customerId15to18";
	/**
	 * 查询套餐详情信息（图片）
	 */
	public static final String USS_WEB_GET_PRODUCTPIC_INFO = "getPorductPicInfo";
	/**
	 * @Fields OCCUPY_TERMINAL : TODO 终端预占
	 */
	public static final String OCCUPY_TERMINAL = "occupyTerminal";
	/** 类变量---------------------------------------------------------------- */
	// ecaop开户申请
	public static final String USS_WEB_ACCOUNT_APPLY = "accountApply";

	public static final String LOGOUT = "logout";

	public static final String USS_WEB_DEV_QYR = "devQry";

	/**
	 * @Fields USS_IS_CAN_JOIN_ACTIVITY : TODO 获取是否可以参加合约计划得 标志
	 */
	public static final String USS_IS_CAN_JOIN_ACTIVITY = "isCanJoinActivity";

	/* bss主页 */
	public static final String BSS_INDEX = "bssIndex";

	public static final String IDCARD_INDEX = "idcardIndex";

	public static final String TERMINAL_MANAGEMENT= "terminalManagement";
	public static final String TERMINAL_IMPORT= "terminalImport";

	public static final String TERMINAL_STOCK= "terminalStock";
	public static final String TERMINAL_MANAGEMENT_QUERY= "terminalManagementQuery";
	public static final String TERMINAL_REPORTBINDINFO= "terminalReportBindInfo";

	public static final String TERMINAL_SALESEARCH= "terminalSaleSearch";
	public static final String ORDER_FEE_INDEX = "orderFeeIndex";

	public static final String TERMINAL_MAKEUP= "terminalMakeup";
	public static final String ORDER_FEE_JUDGE = "orderFeeJudge";

	/* bss 页面初始化 */
	public static final String BSS_INIT_WEB = "bssInitWeb";

	 public static final String QUERY_INFO_TERMINAL_DETAIL = "queryInfoTerminalDetail";
	 public static final String QUERY_INFO_TERMINAL_IMPORT = "queryInfoTerminalImport";
	 public static final String QUERY_INFO_TERMINAL_DETAIL_EXPROT = "queryInfoTerminalDetailExport";
	 public static final String QUERY_INFO_TERMINAL_IMPORT_EXPROT = "queryInfoTerminalImportExport";
	 public static final String QUERY_INFO_TERMINAL_IMPORT_SQL = "queryInfoTerminalImportSql";


	 public static final String QUERY_TERMINAL_BIND_INFO = "queryTerminalBindInfo";
	 public static final String QUERY_TERMINAL_BIND_INFO_EXPROT = "queryTerminalBindInfoExport";
	 public static final String QUERY_TERMINAL_STOCK = "queryTerminalStock";
	 public static final String QUERY_TERMINAL_STOCK_EXPROT = "queryTerminalStockExport";
	 public static final String QUERY_TERMINAL_SALESEARCH = "queryTerminalSaleSearch";
	 public static final String QUERY_TERMINAL_SALESEARCH_EXPROT = "queryTerminalSaleSearchExport";
	 public static final String QUERY_TERMINAL_REPORTBINDINFO = "queryTerminalReportBindInfo";

	 public static final String QUERY_TERMINAL_MAKEUP = "queryTerminalMakeup";
	/**
	 * payment
	 */
	public static final String PAY = "pay";

	/**
	 * 计算积分
	 */
	public static final String GETSCORE = "getscore";

	/**
	 * 储存信息
	 */
	public static final String INSERTMESSAGE = "insertmessage";

	public static final String GETMESAGE = "getmesage";

	public static final String GETMESAGEATTR = "getmessageattr";

	public static final String UPDATEMESSAGEFLAG = "updatemessageflag";

	public static final String GETALLOTFLAG = "getallotflag";

	public static final String USS_GETUSERLIST = "getUserList";

	public static final String QUERYBROADBANDRENEWAL = "queryBroadbandRenewal";

	public static final String SALERECORD = "saleRecord";

	public static final String QUERYSALERECORDBYOPERID = "querySaleRecordByOperId";

	public static final String QUERYSALERECORDPAGE = "querySaleRecordPage";

	public static final String QUERYCOMMISSIONLISTBYOPERID = "queryCommissionListByOperId";

	public static final String QRYSALEREPORTBYBRANCH = "qrySaleReportByBranch";

	public static final String QRYSALEREPORTALL = "qrySaleReportAll";

	public static final String QRYSALEDAILYREPORTALL = "qrySaleDailyReportAll";

	public static final String QRYSALEDAILYDETAILREPORT = "qrySaleDailyDetailReport";

	public static final String QRYORDERTYPELIST = "qryOrderTypeList";

	public static final String QRYTERMINALMODELLIST = "qryTerminalModelList";

	public static final String QRYPRODUCTLMODELLIST = "qryProductModelList";

	public static final String QRYORDESUBRTYPELIST = "qryOrderSubTypeList";

	public static final String CREATERULECOMMISSION = "createRuleCommission";

	public static final String MODIFYRULECOMMISSION = "modifyRuleCommission";

	public static final String REMOVERULECOMMISSION = "removeRuleCommission";

	public static final String QUERYRULECOMMISSIONLISTBYOPERID = "queryRuleCommissionListByOperId";

	public static final String QUERYRULECOMMISSIONLISTPAGE = "queryRuleCommissionListPage";

	public static final String QUERYRULECOMMISSIONLISTBYRULEID = "queryRuleCommissionListByRuleId";

	public static final String QUERYSELFHELPCOMMISSIONLISTBYOPERID = "querySelfHelpCommissionListByOperId";

	public static final String QUERYSELFHELPCOMMISSIONLISTBYOPERIDPAGE = "querySelfHelpCommissionListByOperIdPage";

	public static final String QUERYSELFHELPCOMMISSIONLISTBYDEPTNO = "querySelfHelpCommissionListByDeptNo";

	public static final String QUERYSELFHELPCOMMISSIONLISTBYBRANCHID = "querySelfHelpCommissionListByBranchId";

	public static final String QUERYSELFHELPCOMMISSIONLISTBYBRANCHIDPAGE = "querySelfHelpCommissionListByBranchIdPage";

	public static final String QUERYSELFHELPCOMMISSIONLISTBYCHNLCODE = "querySelfHelpCommissionListByChnlCode";

	public static final String QUERYSELFHELPCOMMISSIONLISTBYCHNLCODEPAGE = "querySelfHelpCommissionListByChnlCodePage";

	public static final String  QRYINFOGRIDLISTBYDEPTNO = "qryInfoGridListByDeptNo";

	public static final String  QRYINFOBRANCHLISTBYPROVINCENO = "qryInfoBranchListByProvinceNo";

	public static final String  QRYINFOBRANCHLISTBYPROVINCENOPAGE = "qryInfoBranchListByProvinceNoPage";

	public static final String  QRYINFOCHNLCODELISTBYGRIDID = "qryInfoChnlCodeListByGridId";

	public static final String USS_GETUSERLISTLAN = "getUserListLan";

	public static final String USS_GETUSEROLDELIST = "getUserOldeList";

	public static final String QRYRULEQUOTA = "qryRuleQuota";

	public static final String QRY_ACTIVITY_VIRTUAL_GROUP = "qryActivityVirtualGroup";

	/**
	 * 无纸化
	 */
	public static final String UPLOAD_FORM_PDF = "uploadFormPdf";

	/**发起重签 */
	public static final String UPLOAD_FORM_PDF_REPEAT = "uploadFormPdfRepeat";

	public static final String UPLOAD_FORM_PDF_GOOD = "uploadFormPdfGood";
	public static final String UPLOAD_FORM_PDF_PREFERENTIAL_ = "uploadFormPdfPreferential";

	public static final String PDF_SIGN = "pdfSign";
	public static final String PDF_SIGNFORCQ = "pdfSignForCQ";
	public static final String GET_PDF = "getPdf";
	public static final String PDF_IS_SIGN = "pdfIsSign";
	public static final String GET_PDFFORCQ = "getPdfForCQ";
	public static final String UPDATE_PAPERLESS_STATE = "updatePaperlessState";
	public static final String PDF_PUSHMAIL = "pdfPushMail";
	public static final String PDF_QRYPAPERLESSINFO = "qryPaperlessInfo";

	/**
	 * 减免申请
	 */
	public static final String GETRESSIONOFDISCOUTN = "getressionofdiscoutn";
	/**
	 * 费用详情
	 */
	public static final String GETFEEDETAILS = "getFeeDetails";

	public static final String GETBALANCEDETAILS = "getBalanceDetails";

	/**
	 * 获取变更费用
	 */
	public static final String GET_FEE_DATA_FOR_BIAN_GENG = "getFeeDataForBianGeng";
	/**
	 * 收费信息写属性表
	 */
	public static final String  PAYMENT = "payment";

	public static final String GET_PAYMENT_INFO = "get_payment_info";

	public static final String QRY_USER_PRODUCT = "qryUserProduct";

	public static final String ORDERDETAIL = "orderdetail";

	public static final String MEM = "mem";

	public static final String GET_POSPUDATEINFO = "get_pospudateinfo";
	/**
	 * 获得身份证图片
	 */
	public static final String GET_ID_PIC = "getIDPic";

	public static final String USS_WEB_GET_HEAT_OFR = "getHeatOfr";

	public static final String GET_OPER_NO = "getOperNo";


	public static final String MAKE_SURE_JESSIONID = "makeSureJessionid";

	public static final String GET_ORDER_ID = "get_order_id";

	public static final String CREATE_ORDER = "create_order";

	public static final String CREATE_ORDER_INSERT_FEE = "create_order_insert_fee";

	public static final String UNLOCK_USIM = "unlockUsim";
	public static final String QRY_USIM_BY_DEVICE = "qryUsimByDevice";
	public static final String Spec_CHANGE = "specDinnerChange";
	public static final String GET_Exchange_InfoScore = "getExchangeInfoScore";
	public static final String Confirm_Exchange_InfoScore = "ConfirmExchangeInfoScore";
	public static final String Query_XimInfo ="QueryXimInfo";
	public static final String Change_InfoCall = "ChangeInfoCall";

	public static final String GUAQI = "guaqi";

	public static final String QUXIAO = "quxiao";
	public static final String get_pages_authority = "getPagesAuthority";

	public static final String UPDATE_PASSWORD = "update_password";
	public static final String QUERY_TERMINAL_BIND_RELATION = "queryTerminalBindRelation";
	public static final String INSERT_TERMINAL_BIND_RELATION_AND_LOG = "insertTerminalBindRelationAndLog";
	public static final String TERMINAL_DETAIL_QUERY = "terminalDetailQuery";
	public static final String TERMINAL_MKAEUP_SUBMIT = "terminalMakeupSubmit";

	public static final String UNBUND_HOT_TERMINAL_RELATION = "unbundHotTerminalRelation";
	public static final String REPLACE_TERMINAL_BIND_RELATION = "replaceTerminalBindRelation";

	public static final String DELETE_REST_LOGIN_MEM="deleteRestLoginMem";
	/*蓝牙获取身份证写入数据库*/

	public static final String LOGIN_CHECK_CODE = "login_check_code";

	/**
	 * 进入宁夏版实名返档审核页 YUN-424
	 */
	public static final String GO_TO_REALNAME_REPORT = "realNameRecordReport";
	public static final String REAL_NAME_REPORT_EXCEL = "qryRealNameReportExcel";
	public static final String UPDATE_IDCARD = "updateIDCardOrderId";


	public static final String NEW_REGISTER_SMS = "new_register_sms";

	public static final String NEW_INFO_OPER_4_REGISTER = "newInfoOper4Register";

	public static final String GET_INFO_OPER = "getInfoOper";
	public static final String QUERY_RPT_SAMPLING_AUDIT = "QueryRptSamplingAudit";
	public static final String AUDIT_RPT_SAMPLING_AUDIT = "auditRptSamplingAudit";
	public static final String QUERY_RPT_BUSI_LIST = "QueryRptBusiList";
	public static final String QUERY_RPT_BUSI_LIST_EXCEL = "QueryRptBusiListExcel";
	public static final String QUERY_RPT_BUSI_LIST_ALL = "QueryRptBusiListAll";
	public static final String QUERY_RPT_TERMINAL_DETAIL = "QueryRptTerminalDetail";
	public static final String QUERY_RPT_TERMINAL_DETAIL_EXCEL ="QueryRptTerminalDetailExcel";
    public static final String QUERY_RPT_BUSI_FEE_DETAIL_EXCEL = "QueryRptBusiFeeDetailExcel";
	public static final String QUERY_RPT_TERMINAL_DETAIL_ALL = "QueryRptTerminalDetailAll";
    public static final String QUERY_RPT_BUSI_FEE_DETAIL = "QueryRptBusiFeeDetail";
    public static final String QUERY_RPT_BUSI_FEE_SUM = "QueryRptBusiFeeSum";
    public static final String QUERY_RPT_BUSI_FEE_SUM_EXCEL = "QueryRptBusiFeeSumExcel";
	public static final String QUERY_RPT_AUDIT_LIST_EXCEL = "QueryRptAuditListExcel";
    public static final String QUERY_LOCAL_NET = "queryLocalNet";
    public static final String QUERY_DEPT_BY_LOCAL_NET = "queryDeptByLocalNet";
    public static final String QUERY_INFO_OPER_BY_DEPTNO_OPERNO = "queryInfoOperByDeptOperNo";
    public static final String QUERY_INFO_DEPT_BY_CONS = "queryInfoDeptByCons";
    public static final String QUERY_ORDER_CS = "queryOrderCs";
    public static final String QUERY_LOG_PAY_CS = "queryLogPayCs";
    public static final String QUERY_RPT_BUSI_STATISTICS = "QueryRptBusiStatistics";
    public static final String QUERY_RPT_BUSI_STATISTICS_EXCEL ="QueryRptBusiStatisticsExcel";
    public static final String QUERY_RPT_SELF_SERVICE_LIST = "queryRptSelfServiceList";
    public static final String QUERY_RPT_SELF_SERVICE_LIST_EXCEL = "queryRptSelfServiceListExcel";
    /**
	 * 稽核报表(CS)
	 */
	public static final String REPORT_AUDIT_CS = "auditCs";
	/**
	 * 稽核报表
	 */
	public static final String REPORT_AUDIT = "audit";
	/**
	 * 稽核报表
	 */
	public static final String REPORT_AUDIT_SP = "auditSalesperson";
	/**
	 * 自助终端报表
	 */
	public static final String REPORT_AUDIT_AUTO = "auditAutoList";

	/**
	 * 返档
	 */
	public static final String FAN_DANG = "fandang";

	/**
	 * 预销户
	 */
	public static final String PRE_CANCEL_DEVICE = "pre_cancel_device";

	 /** 预销户反馈
	 */
	public static final String PRE_CANCEL_DEVICE_FANKUI ="pre_cancel_device_fankui";
	/**
	/**
	 * 遮罩层
	 */
	public static final String REPORT_COVERIFRAME = "coverIframe";
	/* YUN-143 */
	public static final String BIND = "bind";
	public static final String CHOICE_INFO_AGENT_DEVELOPERS = "choiceInfoAgentDevelopers";
	public static final String QRY_INFO_AGENT_DEVELOPS_FOR_BINDING = "queryInfoAgentDevelopersForBinding";
	/* YUN-143 end*/
	/* 存费送业务 业务包查询*/
	public static final String USS_WEB_GET_GIFT_ACTIVITY = "get_Gift_activity";
	/*YUN-172*/
	public static final String UNLOCK_NUMBER_DATA = "numberUnlock";
	/* YUN-143 bss*/
	public static final String BSSBIND = "bssbind";
	/**
	 * 进入宁夏版实名返档审核页 YUN-424
	 */
	public static final String GO_TO_REALNAME_CHECK = "realNameRecordCheck";
	public static final String CQ_GO_TO_REALNAME_CHECK = "cqRealNameRecordCheck";
	public static final String REARCH_RESULT = "searchResult";
	public static final String CQ_REARCH_RESULT = "cqSearchResult";
	public static final String GET_CARD_PHOTO = "getCardPhoto/{photoState}/{orderId}";
	public static final String CQ_GET_CARD_PHOTO = "cqGetCardPhoto/{photoState}/{orderId}";
	public static final String RETURN_RECORD = "returnRecord";
	public static final String CQ_RETURN_RECORD = "cqReturnRecord";
	public static final String CHECK_VETO = "checkVeto";
	public static final String CQ_CHECK_VETO = "cqCheckVeto";

	public static final String REAL_NAME_AUDIT = "realNameAudit"; // YUN-437 GX_20150118_(宁夏版)实名返档页面(PC端)_杨枝蕃
	public static final String CQ_REAL_NAME_AUDIT = "cqRealNameAudit"; // YUN-437 GX_20150118_(宁夏版)实名返档页面(PC端)_杨枝蕃

	/**
	  * pdf状态码
	  */
	 public static final String PDF_STATUSCODE = "pdfStatusCode";

	/**
	 * 自助终端一键登录
	 */
	public static final String ONE_KEY_LOGIN = "oneKeyLogin";

	 public static final String USS_WEB_GET_WBFEE_DATA = "orderBusiFeeQry";//宽带查询费用

	 public static final String USS_WEB_GET_LANONLINE_DATA = "IsUseFixM165";//宽带查询上网账号

	 public static final String USS_WEB_GET_LANYWONLINE_DATA = "lanYwNumber";//宽带查询上网账号

	 public static final String USS_WEB_GET_LANADDUSER_DATA = "lanAddUser";//宽带成员纳入

	 public static final String USS_WEB_GET_LANOCCPUY_DATA = "lanYwOccpuy";//业务号码预占
	 /*
	  * 菜单操作记录
	  */
	 public static final String MENU_OPERATE_RECORD = "menuOperateRecord";


	 public static final String USS_WEB_GET_WOFEE_DATA = "orderWoBusiFeeQry";//智慧沃家新装    查询费用

	 //自助终端 begin --

	 public static final String CHOOSE_PACKAGE = "choosePackage";//查询套餐
	 public static final String CHOOSE_NUMBER = "chooseNumber";//查询号码
	 public static final String GET_ORDERATTR = "getOrderAttr";//查询订单信息
	 public static final String GET_HALFAUTOFEE = "getHalfAutoFee";//半自助终端查询订单费用

	 public static final String QUERY_PHONE_PACKAGE_BIND_ACTIVITY = "queryPhonePackageBindActivitys"; //查询捆绑活动信息
	 public static final String ADD_PHONE_PACKAGE_BIND_ACTIVITY = "addPhonePackageBindActivitys"; //增加捆绑活动信息

	 public static final String GET_INFO_AUTO_STORE_SERVICE = "getInfoAutoStoreService"; //获取门店信息
	 public static final String GET_INFO_AUTO_TERMINAL = "getInfoAutoTerminal"; //查询"自助与终端串号"
	 public static final String ADD_INFO_AUTO_TERMINAL = "addInfoAutoTerminal"; //增加"自助与终端串号"
	 public static final String QUERY_PHONE_PACKAGE_TERMINAL = "queryPhonePackageTerminal"; //取自助终端的终端设备信息
	 public static final String GEN_QR_CODE = "genQrCode"; //生成二维码

	 public static final String GEN_HAND_ID_CAMERA = "genHandIDCamera"; //生成手持身份证照片

	 public static final String MAKE_RECEIPT = "makeReceipt"; //生成小票信息

	 public static final String GET_INFO_AUTO_ORDER_FACE = "getInfoAutoOrderFace";
	 public static final String ADD_INFO_AUTO_ORDER_FACE = "addInfoAutoOrderFace";

	 public static final String SEND_AIP_SCHEME = "sendAipScheme"; //上海 发送AIP活动接口

	 //自助终端 end --

	 public static final String NINGXIA_CHARGE = "nxcharge";   //宁夏缴费
	 public static final String NINGXIA_AGENT_SIGNED = "nxQueryAgentSigned";  //宁夏代理商签约查询

	 public static final String NINGXIA_CHARGE_PC = "nxchargepc";   //宁夏缴费PC端页面

	 public static final String NINGXIA_CHARGE_PC_NEW = "nxChargePCNew";   //宁夏缴费PC端页面_优化

	 public static final String GET_DATE_TIPS="getDateTips";//提示
	 public static final String GET_LAN_ADDRESS="getAddressCode";//装机地址
	 public static final String GET_LAN_QRY_ADDRESS="qryAddressList";//查询装机地址
	 public static final String GET_LAN_CHECH_ADDRESS="checkAddress";//装机地址资源预判
	 public static final String LAN_SOLEBUSIACCNT="soleBusiAccnt";//宽带单装预提交
	 public static final String LAN_ORDERSUB="orderSub";//宽带单装正式提交

	 public static final String ALIPAY_PHONE = "alipayPhone";//支付宝手机网站支付

	 public static final String ALIPAY_PHONE_NOTIFY = "alipayPhoneNotify";//支付宝手机网站支付异步通知路径
	 public static final String ALIPAY_PHONE_RETURN = "alipayPhoneReturn";//支付宝手机网站同步通知路径
	 public static final String SEARCH_STATUS = "searchStatus";//支付宝查询支付状态
	 public static final String ALI_SEARCH_STATUS = "aliSearchStatus";//向支付宝系统查询支付宝支付状态
	 public static final String ALIPAY_SCAN="alipayScan";//支付宝二维码支付
	 public static final String ALIPAY_SCAN_NOTIFY="alipayScanNotify";//支付宝扫码支付异步通知路径
	 public static final String ALIPAY_TRADE_REFUND = "alipayTradeRefund";//支付宝退款
	 public static final String QRY_INFO_AD="queryInfoAd";//查询广告页面信息
	 /*宁夏全拿走*/
	 public static final String PHONE_NUM_CHECK = "phoneNumCheck";
	 public static final String GET_TERMINAL_INFO = "getTerminalInfo";
	 public static final String CHOOSE_TERMINALS = "chooseTerminals";
	 public static final String CHOOSE_ACTIVITY = "chooseActivity";
	 public static final String SAVE_ALL_TAKE_INFO = "saveAllTakeInfo";
	 public static final String GOTO_ALL_TAKE_SEARCH = "gotoAllTakeSearch";
	 public static final String SEARCH_ALL_TAKE = "searchAllTake";
	 public static final String DEAL_PREORDER = "dealPreOrder";
	 /*宁夏VPN套餐预受理*/
	 public static final String GET_VPN_PACKAGE = "getVpnPackage";
	 public static final String SAVE_INFO_VPN_ORDER = "saveInfoVpnOrder";
	 public static final String VPN_PHONE_NUM_CHECK = "vpnPhoneNumCheck";
	 public static final String QUERY_INFO_VPN_ORDER = "queryInfoVpnOrders";
	 public static final String DELETE_INFO_VPN_ORDER = "delInfoVpnOrder";
	 public static final String GOTO_VPN_DINNER_SEARCH = "gotoVpnDinnerSearch";
	 public static final String DEAL_INFOVPNORDER = "dealInfoVpnOrder";
	 /*宁夏移动端实名制返档*/
	 public static final String GET_IDNUMBER_INFO = "getIdNumberInfo";
	 public static final String REAL_NAME_PHONE_NUM_CHECK = "realNamePhoneNumCheck";
	 public static final String NX_RETURN_RECORD = "returnRecord";
	 /*营销活动*/
	 public static final String CHECK_MKT = "checkMkt";
	 public static final String DOSUBMIT_MKT ="dosubmitmkt";

	 public static final String GET_INFO_MENU_MOBILE = "getInfoMenuMobileById";
	 public static final String GET_INFO_MENU_MOBILE_GX = "getInfoMenuMobileByIdForGX";

	//19pay
     public static final String ONE_9_PAY_SERVICE="one9PayService";//19pay支付


	 public static final String ONE_9_PAY_CALLBACK_SERVICE="one9PayCallbackService";//19pay回調

	 public static final String ONE_9_PAY_QR_CODE="One9PayQrCode";//19pay二維碼支付

	 public static final String ADD_AUTO_OPEN_LOG = "addAutoOpenLog"; //自助终端日志表
	 public static final String TRANS_FER = "transfer";
	 public static final String INFO_CUST_CARE_ORDER = "infoCustCareOrder";
	 public static final String CHECK_INFO_CUST_CARE_ORDER="checkInfoCustCareOrder";
	 public static final String CHECK_CHARGE_LIST = "checkChargeList";
	 public static final String INSERT_AUTO_STATUS_LOG = "insertAutoStatusLog";//自助终端心跳信息表
	 public static final String GET_RULE_MAC = "getRuleMac";//自助终端mac表
	 public static final String AUTO_STATUS_LIST = "autoStatusList";//自助终端视图
	 public static final String QUERY_AUTO_STATUS = "queryAutoStatus";//自助终端视图



	 /**
	 * 重庆稽核报表(CS)
	 */
	 public static final String CQ_REPORT_AUDIT_CS = "cqAuditCs";
	 public static final String QUERY_LOG_PAY_CS_EXCEL = "queryLogPayCsExcel";
	 public static final String QUERY_ORDER_CS_EXCEL = "queryOrderCsExcel";
	 public static final String QUERY_LOG_PAY_CS_ALL = "queryLogPayCsAll";
	 public static final String QUERY_LOG_PAY_CS_ALL_EXCEL = "queryLogPayCsAllExcel";
	 public static final String QUERY_ORDER_CS_ALL = "queryOrderCsAll";
	 public static final String QUERY_ORDER_CS_ALL_EXCEL = "queryOrderCsAllExcel";
	 public static final String QUERY_ALIPAY_RECORD_EXCEL = "queryAlipayRecordExcel";
	 public static final String QUERY_CHARGE_CHECK_EXCEL = "queryChargeCheckExcel";
	 public static final String QUERY_MONEY_FLOWS_CHECK_EXCEL = "queryMoneyFlowsCheckExcel";
	 public static final String QUERY_ALIPAY_RECORD = "queryAlipayRecord";
	 public static final String QUERY_CHARGE_CHECK = "queryChargeCheck";
	 public static final String QUERY_MONEY_FLOWS_CHECK = "queryMoneyFlowsCheck";
	 public static final String QUERY_INFO_CUST_CARE_ORDER_MONTHLY = "queryInfoCustCareOrderMonthly";
	 public static final String QUERY_INFO_CUST_CARE_ORDER_MONTHLY_EXCEL = "queryInfoCustCareOrderMonthlyExcel";
	 public static final String QUERY_INFO_CUST_CARE_ORDER = "queryInfoCustCareOrder";
	 public static final String QUERY_INFO_CUST_CARE_ORDER_EXCEL = "queryInfoCustCareOrderExcel";
	 public static final String QUERY_RPT_COMMISSION_LIST = "queryRptCommissionList";
	 public static final String QUERY_RPT_COMMISSION_LIST_EXCEL = "queryRptCommissionListExcel";
	 public static final String QUERY_RPT_COMMISSION_LIST_ALL = "queryRptCommissionListAll";
	 public static final String QUERY_RPT_COMMISSION_LIST_ALL_EXECL = "queryRptCommissionListAllExcel";
	 //资金归集
	 public static final String CAPITAL_ACCUMULATION = "capitalAccumulation";

	 //资金归集
	 public static final String CAPITAL_ACCUMULATION_REFID = "capitalAccumulationRefid";
	 //默认推荐功能包、活动
	 public static final String GET_BUSISYSTEMPARA = "getBusiSystemPara";

	//第三方便民缴费

	/**
	 * 创建待缴费订单
	 */
	public static final String USS_REST_THIRD_CREATE_ORDER = "createOrder";

	public static final String USS_REST_THIRD_UPDATE_ORDER = "updateOrder";

	public static final String USS_REST_THIRD_PAY = "pay";

	/**	广电查询	 */
	public static final String USS_REST_THIRD_QUERY_GD = "queryGD";

	/**	广电销帐	 */
	public static final String USS_REST_THIRD_CONFIRM_GD = "confirmGD";

	/**	 电网查询	 */
	public static final String USS_REST_THIRD_QUERY_DW = "queryDW";

	/**	电网销帐	 */
	public static final String USS_REST_THIRD_CONFIRM_DW = "confirmDW";

	/**	 水费查询	 */
	public static final String USS_REST_THIRD_QUERY_WATER = "queryWater";

	public static final String 	QRY_TARIFF="qryTariffByOperDept";
	//串号校验
	public static final String CHECK_IMEI =   "checkIMEI";
	//开通接口
	public static final String OPEN_CONTRACT =   "openContract";




	/**密码变更重置*/
	public static final String PASSWORD_CHANGE_RESET = "passwordChangeRest";



	public static final String QUERY_TRAFFIC = "queryTraffic";
	public static final String ORDER_TRAFFIC = "orderTraffic";

	public static final String OLDUSER_SUBMIT = "oldUserSubmit";
	public static final String WX_QRCODE = "wxQrCode";
	public static final String WX_SCAN_NOTIFY = "wxpayScanNotify";

	//老用户优惠购机
	public static final String OLDUSERDISCOUNTPURCHASEAPPLY= "oldUserDiscountPurchaseApply";

	public static final String OLDUSERDISCOUNTPURCHASECHECK = "oldUserDiscountPurchaseCheck";
	/*预受理*/
	 public static final String SAVE_PRE_INFO_ORDER = "savePreInfoOrder";
	 public static final String SAVE_PRE_INFO_ORDER_ATTR = "preInfoOrderAttrUpdate";
	public static final String GOTO_PRE_OPEN_SEARCH = "gotoPreOpenSearch";

	public static final String UPDATE_RES_GSM_NUMBER_BY_DEVICE_NUMBER = "updateResGsmNumbertByDeviceNumber";
	public static final String QUERY_RES_GSM_NUMBER_BY_ALL_CONDITION = "queryResGsmNumberByAllCondition";

	public static final String QUERY_PRE_RULE_BUSINESS_CHARGE_BY_ALL_CONDITION = "queryPreRuleBusinessChargeByAllCondition";
	public static final String QUERY_PRE_RULE_BUSINESS_CHARGE_GROUPBY_PROFUCTINFO = "queryPreRuleBusinessChargeGroupByProductInfo";
	public static final String QUERY_PRE_OPEN_ORDERS = "queryPreOpenOrders";
	public static final String SIMULATION_LOGIN = "simulationLogin";
		/**	m2536  资费变更	 */
	public static final String USS_WEB_TARIFF_CHANGE = "tariffChange";
	/**	m2536  资费变更	  */
	public static final String USS_WEB_THREEPART_CHECK = "threepartCheck";

	/**加油包*/
	public static final String QUERY_FLOW_BAG = "queryFlowbag";
	public static final String QUERY_Rate = "queryRate";
	public static final String QUERY_Flow_PRODUCT_DATA = "queryFlowProductData";
	public static final String RESOURCE_BUY = "resourceBuy";
	public static final String FLOW_BAG_PAY = "pay";
	public static final String PRODUCT_CHANGE = "productChange";


	public static final String QUERY_UNIFORM_INFOOPER = "queryUniformInfoOper";
	public static final String NEW_LOGIN = "newLogin";
	public static final String LOGIN_OTHER_OPER = "LoginOtherOper";
	public static final String LOGIN_OTHER_OPER_XT = "LoginOtherOperXT";
	/*互斥校验接口*/
	public static final String QUERY_RULE_PRODUCT_LIMIT = "queryRuleProductLimitByAllCondition";
	/*查询号码实时话费*/
	public static final String QUERY_NUMBER_BALANCE = "queryNumberBalance";
	/*预后付费判断*/
	public static final String QUERY_PRE_PAY_JUDGE = "queryPrePayJudge";
	public static final String LIAN_PAY_NOTICE = "lianPayNotice";
	//查询营业员角色
	public static final String QUERY_RULE_OPER_ROLE = "queryRuleOperRole";
	//查询角色支付方式
	public static final String QUERY_OPER_PAY = "queryOperPay";

	/**获取子工号绑定的发展人信息**/
	public static final String GET_AGENT_DEVELOPER = "getAgentDeveloper";
	/**查询发展人**/
	public static final String QUERY_AGENT_DEVELOPERS = "queryAgentDevelopers";
	/**通过产品查询发展人**/
	public static final String AGENT_DEVELOPERS_BY_OFRID = "queryInfoAgentDevelopersByOfrId";
	/*广西短信接口*/
	public static final String GX_SEND_SMS = "gxSendSms";
	/*开户记录佣金*/
	public static final String OPEN_COMMISSION = "openCommission";
	/*缴费记录佣金*/
	public static final String PAY_COMMISSION = "payCommission";
	/*查询记录佣金*/
	public static final String QRY_COMMISSION = "queryCommission";


	/**全民付收银台通知消息**/
	public static final String QMF_NOTIFY = "qmfNotify";

	/**全民付收银台记录缴费信息**/
	public static final String QMF_LOG_PAY = "qmfLogPay";


	/**补卡**/
	public static final String CHG_RE_CARD = "chg_re_card";

	/**三户校验**/
	public static final String CHECK_THREE_PART = "checkThreePart";
	/**三折购，订单提交**/
	public static final String THREE_DISCOUNTSUBMIT = "threeDiscountSubmit";
	/**3G/4G补换卡，订单提交**/
	public static final String CARD_CHANGESUBMIT = "cardChangeSubmit";

	public static final String CHECK_INFO_CUST_CARE_ORDER_PRINT="checkInfoCustCareOrderPrint";

	/**根据场景查询发展人信息**/
	public static final String QUERY_AGENT_DEVELOPERS_BY_SCENETYPE = "queryAgentDevelopersBySceneType";
	/**根据场景查询默认发展人信息**/
	public static final String GET_DEFAULT_AGENT_DEVELOPER_BY_SCENETYPE = "getDefaultAgentDeveloperBySceneType";

	//沃创富融合
	public static final String ADD_WCF_ORDER = "addWcfOrder";
	public static final String GET_WCF_ORDER = "getWcfOrder";
	public static final String SUBMIT_WCF_ORDER = "submitWcfOrder";

	//补卡读写卡
	public static final String CARD_NOTIFY_REMAKE = "cardNotifyRemake";
	public static final String CARD_QRY_REMAKE = "cardQryRemake";

	/**BSS的缴费**/
	public static final String CHARGE_BSS = "chargeBSS";
	//找回密码
	public static final String GENERATE_VERIFY_CODE = "generateVerifyCode";  //生成验证码
	public static final String GENERATE_VERIFY_CODE_FOR_LOGIN = "generateVerifyCodeForLogin";//生成登录验证码
	public static final String CHECK_VERIFY_CODE_FOR_LOGIN = "checkVerifyCodeForLogin";//验证登录验证码
	public static final String FIND_PASSWORD = "findPassword";
	//沃家庭手机收费
	public static final String SUBMIT_WJTORDER = "submitWjtOrder";
	//变更PDF
	public static final String UPLOAD_FORM_PDF_CHANGE = "uploadFormPdfChange";

	//子工号信息查询，沃创富调用
	public static final String QUERY_INFO_OPER = "queryInfoOper";
	//活动查询，供沃创富调用
	public static final String QUERY_INFO_ACTIVITY = "queryInfoActivity";

	//PC版协同开户
	public static final String OPEN_ACCOUNT_PCXT  ="openAccountPCXT";

	/**电子协议**/
	public static final String UPLOAD_FROM_PDFELECTRIC = "uploadFormPdfElectric";

	/**清库机查询**/
	public static final String GET_TERMINAL_CLEAR_STORE = "getTerminalClearStore";

	/**老用户销售提交**/
	public static final String OLDUSERPURCHASE_SUBMIT = "oldUserPurchaseSubmit";

	/**宁夏2G靓号查询**/
	public static final String NX_GET_MON_LIMIT_BY_NUMBER = "nxGetMonLimitByNumber";
	//上传证件照
	public static final String UPLOAD_CERT_IMAGES ="uploadCertImages";
	/**重庆PC端综合变更**/
	public static final String MULTI_CHANGE = "cqPcMultipleChange";

/*	public static final String SELECTRULEAREAHX = "selectRuleAreaHx";

	public static final String  SELECTADDRONLINENX = "selectAddrOnLineNx";*/

	public static final String SETORDERCOMMIT = "setOrderIdAndCommitInfoOrderCq";
	//客户校验增加BSS信息录入
	public static final String ADD_LOG_ID_VALIDITY ="addLogIdValidity";
	//NX预受理订单处理
    public static final String GOTO_PRE_ORDER_INDEX ="gotoPreOrderIndex";

    public static final String TARRIFCHANGEORDERSUBMIT = "tarrifChangeOrderSubmitCq";

    public static final String SALESELECTEDCODE = "saleSelectedCodeCq";
    public static final String TARIFFCHANGECQ = "tariffChangeCq";
    //通过地市查询infoOper
    public static final String QUERY_INFOOPER_BY_REGION_ID = "queryInfoOperByLocalNet";
    public static final String QUERY_INFOOPER_BY_OPERNAME = "queryInfoOperByOperName";
	/**响应开始时间**/
	public static final String RSP_TIME_START = "rspTimeStart";

	/**响应截止时间**/
	public static final String RSP_TIME_END = "rspTimeEnd";

	/**
	 * 产品活动维护相关
	 */
	public static final String  PRODUCT_AND_ACTIVITY="productAndActivity";
	public static final String  ACTIVITY_SCENE_TYPE="activitySceneType";//获取活动代码列表(上架活动的选项)
	public static final String  ACTIVITY_SELECTED="activitySelected";//代码列表(上架活动的选项)
	public static final String  QUERY_TERMINALCOMBINEDRELATION="queryTerComRelation";//终端组合业务关系表查询
	public static final String  INSERT_TERMINALCOMBINEDRELATION="insertTerComRelation";//终端组合业务关系表插入数据
	public static final String  UPDATE_TERMINALCOMBINEDRELATION="updateTerComRelation";//终端组合业务关系表更新数据
	/**
	 * 重庆产品变更与活动订购合法性验证与费用计算接口
	 */
	public static final String GETPRODUCTANDACTIVITYFEE = "getProductAndActivityFee";

	/**缴费充值接口**/
	public static final String RECV_FEE = "recvFee";
	public static final String CHARGE_PC = "chargepc";

	/**宁夏电子发票生成**/
	public static final String MAKE_ELECTRON_INVOICE = "makeElectronInvoice";
	public static final String MAKE_ELECTRON_INVOICE_4G = "makeElectronInvoice4G";
	public static final String MAKE_ELECTRON_INVOICE_OPEN = "makeElectronInvoiceOpen";
	public static final String PERPURCHASE = "perPurchase";
	public static final String PERPURCHASESUBMIT = "perPurchaseSubmit";
	public static final String QRYDEVICEGUISHU = "qryDeviceGuishu";
	public static final String QRY_DEVICE_GUISHU_FOR_UNI = "qryDeviceGuishuForUni";
	/**app信息推送**/
	public static final String APP_MESSAGE = "appMessage";
	/**国政通实名认证**/
	public static final String GZT_VALID_CUSTOMER_INFO = "gztValidCustomerInfo";
	/**统一工号信息维护接口**/
	public static final String QRY_UNIFORM_FOR_CHANGE = "queryUniformInfoOperForChange";

	/**菜单权限控制**/
	public static final String MENU_AUTHORITY_JUDGEMENT = "menuAuthorityJudgement";

	/***产品打包**/
	public static final String PRODUCT_PAGE = "productPage";
	/***产品打包上下架**/
	public static final String PRODUCT_PAGE_UPANDDOWN = "productPageUpAndDown";
	/**海南ESS客户校验**/
	public static final String ESS_CHECK_CUST = "essCheckCust";
	/**三户信息查询**/
	public static final String CHECK_USER_INFO = "checkUserInfo";
	/**用户档案信息修改**/
	public static final String MOD_REAL_NAME_CUST_INFO = "modRealNameCustInfo";
	/**产品变更预提交**/
	public static final String SGL_PRODUCT_CHG = "sglProductChg";
	/**订单提交**/
	public static final String ORDER_SUB = "orderSub";
	/**订单返销**/
	public static final String ORDER_CANCEL = "cancelOrder";
	/**微信退款**/
	public static final String WXPAY_REFUND = "wxpayRefund";
	/**查询预提交产品信息**/
	public static final String QUERY_INFO_SGL_PRODUCT = "queryInfoSglProduct";
	/**查询预提交产品身份数量**/
	public static final String QUERY_PRODUCT_IDCARDS = "queryProductIDCards";

	/**沃易得查询银行冻结信息**/
	public static final String WYD_QUERY_BANK = "queryBank";
	/**沃易得冻结**/
	public static final String WYD_FREEZE_BANK = "freezeBank";

	/**获取FAQ**/
	public static final String QUESTION_ANSWERS = "questionAnswers";

	/**在线帐号信息操作**/
	public static final String QUERY_ALL_ACCOUNT = "queryAllAccount";
	public static final String QUERY_ACCOUNT_DEFAULT = "queryAccountDefault";
	public static final String UDAPTE_ACCOUNT = "updateAccount";
	public static final String BIND_ACCOUNT = "bindAccount";
	public static final String ADD_ACCOUNT = "addAccount";
	public static final String DEL_ACCOUNT = "delAccount";

	/**海南产品活动提交**/
	public static final String CHECK_PRO_AND_ACT = "checkProAndAct";
	public static final String QUERY_PRO_ACTIVITY = "queryProActivity";
	public static final String PRE_PRO_AND_ACT = "preProAndAct";
	public static final String ORDER_SUB_PRO_AND_ACT = "orderSubProAndAct";
	/**海南产品活动提交**/
	public static final String ORDER_ROLLBACK_2G = "orderRollBack2G";
	/**写卡方式**/
	public static final String WRITE_WAY="writeWay";

	public static final String DEMO_ATTR_DEFINE_CREATE = "demoAttrDefineCreate";

	public static final String DEMO_ATTR_DEFINE_QUERY = "demoAttrDefineQuery";

	public static final String DEMO_ATTR_DEFINE_DELETE = "demoAttrDefineDelete";

	public static final String DEMO_ATTR_DEFINE_UPDATE = "demoAttrDefineUpdate";

	public static final String TEST_URL = "testUrl";

	public static final String XHYTEST_URL = "xhyTestUrl";
	/*
	 * 服务订单状态关系校验
	 */
	public static final String CHECK_SERVICE_ORDER_STATE = "checkServiceOrderState";
	/*
	 * 服务订单环节关系校验
	 */
	public static final String CHECK_SERVICE_ORDER_TACHE = "checkServiceOrderTache";
	/*
	 * 销售订单创建
	 */
	public static final String CREATE_SALE_ORDER = "createSaleOrder";
	public static final String UPDATE_SALE_ORDER = "updateSaleOrder";
	public static final String CONFIRM_SALE_ORDER = "confirmSaleOrder";
	public static final String SUBMIT_SALE_ORDER = "submitSaleOrder";
	public static final String DEAL_SALE_ORDER = "dealSaleOrder";
	public static final String UPDATE_SALE_ORDER_BATCH = "updateSaleOrderBatch";
	public static final String CANCEL_ORDER = "cancelOrder";
	public static final String ADVANCE_ORDER_START = "advanceOrderStart";
	public static final String PAY_FOR_NOTIFY_INFO_SALE_ORDER = "payForNotifyInfoSaleOrder";
	public static final String CREATE_INFO_PAY_ORDER = "createInfoPayOrder";
	public static final String IS_LOGIN = "isLogin";
	/*
	 * 服务订单状态关系维护
	 */
	public static final String OPERATE_ORD_MOD_STATE_RULE= "operateOrdModStateRule";
	/*
	 * 服务订单环节关系维护
	 */
	public static final String OPERATE_ORD_MOD_TACHE_RULE= "operateOrdModTacheRule";
	/*
	 * 服务订单状态关系查询，分页
	 */
	public static final String GET_ORD_MOD_STATE_RULE= "getOrdModStateRule";
	/*
	 * 服务订单环节关系查询。分页
	 */
	public static final String GET_ORD_MOD_TACHE_RULE= "getOrdModTacheRule";

	/*
	 * 服务订单处理
	 */
	public static final String SERVICE_ORDER_OPERATE= "serviceOrderOperate";
	/*
	 * 服务订单修改
	 */
	public static final String SERVICE_ORDER_CHANGE= "serviceOrderChange";
	/*
	 * 订单模板维护
	 */
	public static final String ORDER_MOD_MAINTAN= "orderModMainten";
	/*
	 * 订单模板应用表维护
	 */
	public static final String ORDER_MOD_APP_MAINTAN = "orderModAppMainten";
	/*任务包维护
	 */
	public static final String CODE_TASK_PKG_MAINTAN = "codeTaskPkgMainten";
	/*任务包查询 分页
	 */
	public static final String GET_CODE_TASK_PKG = "getCodeTaskPkg";
	/*任务包查询
	 */
	public static final String GET_CODE_TASK_PKG_DESIGN = "getCodeTaskPkgDesign";
	/*
	 * 流程模板维护
	 */
	public static final String PROC_MOD_MAINTEN = "procModMainten";
	/*
	 * 环节配置维护
	 */
	public static final String PROC_MOD_TACHE_MAINTEN = "procModTacheMainten";
	/*
	 * 流程模板应用查询
	 */
	public static final String GET_PROC_MOD_APP = "getProcModApp";
	/*
	 * 环节查询
	 */
	public static final String GET_TACHE_MOD = "getTacheMod";
	/*
	 * 获取流程图信息
	 */
	public static final String GET_PROC_VIEW_INFO = "getProcViewInfo";
	/*
	 * 人工任务处理
	 */
	public static final String CREATE_ARTIFICIAL_TASK = "createHandingArtificialTask";

	/*
	 * 人工任务列表获取
	 */
	public static final String GET_ARTIFICIAL_TASK_LIST = "getArtificialTaskList";

	/*
	 * 任务分配
	 */
	public static final String CREATE_TASK_ASSIGNMENT = "createTaskAssignment";

	/*
	 * 获取任务详情
	 */
	public static final String GET_TASK_DETAILINFO = "getTaskDetailInfo";

	/*
	 * 订单详情查询
	 */
	public static final String GET_ORDER_DETAIL = "getOrderDetail";

	/*
	 * 人工任务列表查询条件获取
	 */
	public static final String GET_TASK_QUERY_INFO = "getTaskQueryInfo";

	/*
	 * 获取可选任务包
	 */
	public static final String GET_OPTIONAL_TASK_PKG_LIST = "getOptionalTaskPkgList";

	/*
	 * 领取任务包
	 */
	public static final String GET_TASK_PACKAGE = "getTaskPackage";

	/*
	 * 订单模板查询
	 */
	public static final String GET_ORD_MOD = "getOrdMod";

	/*
	 * 订单模板应用表查询
	 */
	public static final String GET_ORD_MOD_APP = "getOrdModApp";

	/*
	 * 销售订单列表查询
	 */
	public static final String QUERY_SALE_ORDER_LIST = "querySalesOrderList";

	/*
	 * 通过能力平台调用订单中心
	 */
	public static final String ABILITY_PLATFORM_SERVICE = "orderCenterService";

	/*
	 * 服务订单列表查询
	 */
	public static final String QUERY_SERV_ORDER_LIST = "queryServOrderList";

	/*
	 * ⦁	获取顺丰纸质单信息
	 */
	public static final String GET_INFO_FOR_SF = "getInfoForSF";
	public static final String GET_SEND_INFO = "getSendInfo";
	public static final String SAVE_SEND_INFO = "saveSendInfo";

	/*
	 * 获取可选工号信息
	 * */
	public static final String GET_OPER_INFO = "getOperInfo";

	/*
	 * 获取默认省份，城市和区域信息
	 * */
	public static final String GET_DEFAULT_PROVINCE_CITY_AREA_INFO = "getDefaultProvinceCityAreaInfo";

	/*
	 * 获取可选部门信息
	 * */
	public static final String GET_DEPART_INFO = "getDepartInfo";

	/*
	 * 获取获取地区信息
	 * */
	public static final String GET_REGION_INFO = "getRegionInfo";

	/*
	 * 获取可选环节
	 * */
	public static final String GET_OPTIONAL_TACHE = "getOptionalTache";

	/*
	 * 获取顺丰物流信息
	 * */
	public static final String GET_SF_LOGISTICS_ROUTER_INFO = "getSFLogisticsRouterInfo";

	/*
	 * 顺丰发货
	 * */
	public static final String SEND_SF_LOGISTICS_INFO = "sendSFLogisticsInfo";

	/*
	 * 顺丰发货(无流程)
	 * */
	public static final String SEND_SF_LOGISTICS_INFO_NO_PROCESS = "sendSFLogisticsInfoNoProcess";

	//国政通校验
	public static final String GZT_VALID = "gztValid";

	//活体自动审核结果通知
	public static final String LIVING_CHECK_RESULT = "livingChecKResult";
	//获取证件信息
	public static final String GET_CERT_INFO = "getCertInfo";

	/*
	 * 服务订单环节接口调用
	 */
	public static final String GET_ORDER_TACHE_CALL = "getOrderTacheCall";

	/*
	 * 任务逾期率统计
	 */
	public static final String GET_STATISTICAL_TASK_OVERDUE_RATE_INFO = "getStatisticalTaskOverdueRateInfo";

	/*
	 * 服务订单竣工率统计
	 */
	public static final String GET_SERV_ORDER_COMPLETION_RATE = "getServiceOrderCompletionRate";

	/*
	 * 服务订单撤单率统计
	 */
	public static final String GET_SERV_ORDER_CANCEL_RATE = "getServiceOrderCancelRate";

	/*
	 * 订单撤销审核
	 */
	public static final String CHECK_INFO_ORDER_CANCEL = "checkInfoOrderCancel";

	/*
	 * 任务环节统计
	 */
	public static final String TASK_STATISTICAL = "taskStatistical";
	/*
	 * 订单撤销申请
	 */
	public static final String GET_ORDER_CANCEL_APPLY="getCancelOrderApply";
	/*
	 * 获取服务订单号
	 */
	public static final String GET_SERV_ORDER_NO="getServOrderNo";
	/*
	 * 刷新缓存
	 */
	public static final String REFRESH_REDIS_ALL="refreshRedisAll";

	/**
	 * 退出
	 */
	public static final String LOGIN_OUT = "loginOut";

	/**
	 * 任务默认分配规则维护
	 */
	public static final String TASK_ASSIGN_RULE_MAINTEN="taskAssignRuleMainten";
	/**
	 * 任务默认分配规则查询
	 */
	public static final String GET_TASK_ASSIGN_RULE="getTaskAssignRule";
	/**
	 * 任务默认分配规则查询工号组
	 */
	public static final String QUERY_OPER_NO_GROUP = "queryOperNoGroup";

	/*
	 * 物流明细报表中间数据生成
	 */
	public static final String CREATE_LOGISTICS_REPORT_DATA="createLogisticsReportData";

	/*
	 * 写卡结果查询
	 */
	public static final String GET_WRITE_CARD_RESULT="getWriteCardResult";

	/*
	 * 写卡结果记录
	 */
	public static final String CREATE_WRITE_CARD_RESULT="createWriteCardResult";

	/*
	 * 订单数据备份
	 */
	public static final String CREATE_ORDER_BACKUP="createOrderBackUp";
	/*
	 * 物流明细报表查询
	 */
	public static final String QUERY_LOGISTICS_REPORT_DATA="queryLogisticsReportData";

	/*
	 * 开户证件上传
	 */
	public static final String SEND_PHOTO_SERV="sendPhotoServ";
	/*
	 * 服务订单详情查询（触点专用）
	 */
	public static final String GET_ORDER_ENQUIRY_CONTACT="getOrderEnquiryContact";


	/*
	 * 获取顺丰物流信息（重庆本地）
	 * */
	public static final String GET_SF_LOGISTICS_ROUTER_INFO_CQ = "getSFLogisticsRouterInfoCq";

	/*
	 * 顺丰发货（重庆本地）
	 * */
	public static final String SEND_SF_LOGISTICS_INFO_CQ= "sendSFLogisticsInfoCq";
	//	获取可选营业厅
	public static final String GET_SHELLING_INFO= "getShellingInfo";

	/*
	 * 顺丰发货(无流程)（重庆本地）
	 * */
	public static final String SEND_SF_LOGISTICS_INFO_NO_PROCESS_CQ = "sendSFLogisticsInfoNoProcessCq";
	/*
	 * 服务订单列表查询 (ES版)
	 */
	public static final String GET_SERV_ORDER_LIST_ES="getServOrderListES";
	/*
	 * 服务订单详情查询(ES版)
	 */
	public static final String GET_ODER_DETAIL_ES="getOrderDetailES";
	/*
	 * 订单明细报表查询
	 */
	public static final String GET_ODER_DETAIL_REPORT="getOrderDetailReport";
	/*
	 * 订单明细报表查询
	 */
	public static final String CTREATE_NEW_LIVING_CHECK="createNewLivingChecK";

	/*
	 * 加入/退出部门任务组
	 */
	public static final String JOIN_OR_EXIT_DEPART_TASK_FORCE="joinOrExitDepartTaskForce";

	/*
	 * AOP赠款 UOC0080
	 */
	public static final String PROM_FEE_FOR_AOP = "promFeeForAop";
	/**
	 * 导出订单
	 */
	public static final String EXPROT_AUDIT_ORDER_INFO = "exportAuditOrderInfo";
	/**
	 * 查询订单
	 */
	public static final String QUERY_AUDIT_ORDER_INFO = "queryAuditOrderInfo";
	/*
	 * AOP订购半年包
	 */
	public static final String GET_AOP_ORDER_HALF_YEAR_PKG = "getAopOrderHalfYearPkg";
}

