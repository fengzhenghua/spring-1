/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年11月6日 上午11:30:30
 * @Title: Constant.java
 * @Package com.tydic.unicom.service.ecaop.constants
 * @Description: TODO
 */
package com.tydic.unicom.service.ecaop.constants;


/**
 * <p></p>
 * @author yangfei 2014年11月6日 上午11:30:30
 * @ClassName Constant
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月6日
 * @modify by reason:{方法名}:{原因}
 */
public class Constant {
	public final static String ECAOP_URL = "http://132.35.81.217:8000/aop/aopservlet";//"http://132.35.88.104:80/aop/aopservlet";//
	public final static String ECAOP_URL_TEST = "http://132.35.81.217:8000/aop/test";
	/**
	 * 默认BIZKEY
	 */
	public final static String ECAOP_DEFALUT_BIZKEY = "TS-3G-012";
	/**
	 * 用户资料检验三户资料返回接口BIZKEY
	 */
	public final static String ECAOP_USC_BIZKEY = "CS-017";
	public final static String ECAOP_SIGN_MD5 = "MD5";		
	public final static String ECAOP_SIGN_HMAC = "HMAC";
	
	/*******************************************************************分支切换*********************************************************/
//	public final static String ECAOP_APP_KEY = "uss.sub";//广西APP_KEY
	public final static String ECAOP_APP_KEY = "cqpre.sub";//重庆APP_KEY
	
	/*走能力平台或者直接连接 ，USS_AOP_BRANCH_CODE 为空时走默认方式（广西使用），“abilityTerrace” 为能力平台方式（重庆使用） */
	public final static String USS_AOP_BRANCH_CODE= "abilityTerrace";//重庆使用
	//public final static String USS_AOP_BRANCH_CODE= "";//广西使用
	
	/*重庆能力平台连接方式广西不使用*/
	/*重庆能力平台正式环境*/
//  public final static String USS_APP_KEY= "330";
//	public final static String USS_APP_PWD = "565b2d69f4bdeda8028e1050e0ce56fe";
//	public final static String USS_URL = "http://135.24.252.38:10083/tap_web/rs/callAppService/call";
	
	/*重庆能力平台测试环境*/
	public final static String USS_APP_KEY= "310";
	public final static String USS_APP_PWD = "a810cd0d6f1cc54304783ea33592c065";
	public final static String USS_URL = "http://135.24.252.29:10083/tap_web/rs/callAppService/call";
	
	/*******************************************************************分支切换*********************************************************/
	public final static String USS_APP_VER = "1";
	public final static String USS_OPER_NAME = "ecaop";
	
	public final static String PROVINCE_CODE_CQ= "cq";
	
	/**
	 * 套餐变更	ecaop.trades.sell.mob.oldu.product.chg  m2536

	 */
	public final static String ECAOP_TARIFFCHANGE_BIZKEY = "SS-CS-001";
	/**
	 * 三户校验	ecaop.trades.query.comm.user.threepart.check 	CS-012  m2536 

	 */
	public final static String ECAOP_THREEPART_CHECK_BIZKEY = "CS-012";
	/**
	 * 4G欠费查询	ecaop.trades.query.comm.user.threepart.check 	CS-012  m2536 

	 */
	public final static String ECAOP_QRY_OWE_BIZKEY = "QS-031";
	/**
	 * 4G余额查询	 	CS-012  m2536 

	 */
	public final static String ECAOP_QRY_BALANCE_BIZKEY = "QS-034";
	
	/**
	 * 优惠购机处理申请和提交	ecaop.trades.sell.mob.oldu.activity.app  m2536

	 */
	public final static String ECAOP_PERPURCHASE_BIZKEY = "SS-GG-001";
}
