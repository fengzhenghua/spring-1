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

	/**
	 * (oppo)创建中台订单
	 */
	public static final String CREATE_UOC_ORDER = "createUocOrder";
	/**
	 * (oppo)微信退款
	 */
	public static final String WX_PAY_REFUND = "wxpayRefund";
	/**
	 * （oppo客户资料校验）
	 */
	public static final String CHECK_CUST_INFO="checkCustInfo";

	/**
	 * 一证五户
	 */
	public static final String QUERY_USER_NUMBER="queryUserNumber";

	/**
	 * 触点 同一身份证开卡数量校验
	 */
	public static final String CHECK_CUST_NUMS_FORAP = "checkCertNumsForAp";

	/**
	 * （oppo）活体生活
	 * */
	public static final String OPPO_LIVING_EXAMINE = "oppoLivingExamine";
	/**
	 * （oppo）更新中台订单
	 * */
	public static final String UPDATE_ORDER_INFO_FROM_UOC = "updateOrderInfoFromUoc";
	/**
	 * （oppo）获取唇语数据
	 * */
	public static final String GET_LIP_LANUAGE_INFO = "getLipLanguageInfo";
	/**
	 * (oppo)根据订单中心服务订单号查询触点订单号
	 */
	public static final String GET_APC_ORDER_ID = "getApcOrderId";

	/**
	 * (oppo)下载微信文件
	 */
	public static final String DOWNLOAD_WX_FILE = "downloadWXFile";

	/**
	 * (oppo)获取微信配置参数
	 */
	public static final String GET_WX_JS_CONFIG = "getWXJSConfig";
	/**
	 * (oppo)查询中台订单信息
	 */
	public static final String QUERY_ORDER_INFO_FROM_UOC = "queryOrderInfoFromUoc";

	/**
	 * (oppo)微信二维码
	 */
	public static final String GET_WX_QR_CODE = "getWxQrCode";

	/**
	 * (oppo)微信支付成功后通知微信
	 */
	public static final String WX_SCAN_NOTIFY = "wxpayScanNotify";

	/**
	 * (oppo)微信支付结果轮询
	 */
	public static final String GET_PAY_RESULT = "getPayResult";

	/**
	 * (oppo)获取地区信息
	 */
	public static final String GET_AREA_INFO = "getAreaInfo";
	/**
	 * (oppo)获取省份编码
	 */
	public static final String GET_PROVINCE_CODE = "getProvinceCode";
	
	/**
	 * 获取全国省份信息
	 */
	public static final String GET_ALL_PROVINCE_INFO = "getAllProvinceInfo";
	/**
	 * (oppo)国政通校验
	 */
	public static final String CHECK_GZT = "checkGZT";
	/**
	 * (oppo)创建订单
	 * */
	public static final String CREATE_OPPO_ORDER_INFO = "createOppoOrderInfo";
	
	/**
	 * 获取触点订单属性值
	 * */
	public static final String GET_AP_ORDER_ATTR_INFO = "getApOrderAttrInfo";
	
	/**
	 * 创建触点订单
	 * */
	public static final String CREATE_AP_ORDER = "createApOrder";

	/**
	 * (oppo)更新订单相关信息表
	 * */
	public static final String UPDATE_OPPO_ORDER_INFO = "updateOppoOrderInfo";

	/**
	 * (oppo)选号
	 * */
	public static final String OPPO_SELECT_NUMBER = "oppoSelectNumber";

	/**
	 * (oppo)号码预占
	 * */
	public static final String OPPO_OCCUPY_NUMBER = "oppoOccupyNumber";

	/**获取可选营业厅信息*/
	public static final String GET_BUSINESS_HALL_INFO = "getBusinessHallInfo";
	/**
	 * APC0002-获取可选工号-REST
	 */
	public static final String GET_OPTIONAL_OPER_INFO = "getOptionalOperInfo";

	/**
	 * APC0003-获取可选发展人-REST
	 */
	public static final String GET_OPTIONAL_DEVE_INFO = "getOptionalDeveInfo";

	/**
	 * APC0017-获取可选激励发展人-REST
	 */
	public static final String GET_REWARD_DEVELOP_INFO = "getRewardDevelopInfo";

	/**
	 * APC0004-获取可选商品-REST
	 */
	public static final String GET_OPTIONAL_GOODS_INFO = "getOptionalGoodsInfo";

	public static final String OPERATE_AP = "operateAp";
	public static final String OPERATE_AP_GOODS = "operateApGoods";
	public static final String QUERY_AP_LIST = "queryApList";
	public static final String QUERY_AP_MSG = "queryApMsg";
	public static final String QUERY_AP_DEVELOPERS = "queryApBulidQrcodeDevelopers";
	/**
	 * APC0011-获取可选触点
	 */
	public static final String GET_OPTIONAL_CONTACT="getOptionalContact";
	/**
	 * APC0012-获取可选渠道
	 */
	public static final String GET_OPTIONAL_AGENT_INFO = "getOptionalAgentInfo";
	/**
	 * APC0013-获取可选地区
	 */
	public static final String GET_OPTIONAL_REGION_INFO = "getOptionalRegionInfo";

	/**给中台回调更新触点订单信息*/
	public static final String CALL_BACK_UPDATE_APC_ORDER="callBackUpdateApcOrder";

	public static final String GET_APC_INFO_BY_SIM_AND_DEVICE_NUMBER="getApcInfoBySimAndDeviceNumber";

	/**
	 * APC统一服务入口
	 */
	public static final String APC_SERVICE="apcService";

	/** 获取CodeList通过typeCode */
	public static final String GET_CODE_LIST_BY_TYPE_CODE = "getCodeListByTypeCode";

	/** APC0014-触点属性维护-REST */
	public static final String SAVE_AP_ATTR_INFO = "saveApAttrInfo";

	/** APC0015-触点属性查询-REST */
	public static final String GET_AP_ATTR_INFO = "getApAttrInfo";

	/** 触点属性缓存写入-REST */
	public static final String REFRESH_AP_ATTR_REDIS = "refreshApAttrRedis";
	
	/**
	 * 从缓存中取触点属性
	 */
	public static final String QUERY_AP_ATTR_BY_REDIS="queryApAttrByRedis";
}

