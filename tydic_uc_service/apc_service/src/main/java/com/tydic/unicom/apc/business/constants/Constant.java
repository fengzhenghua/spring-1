package com.tydic.unicom.apc.business.constants;

public class Constant {
	/**
	 * 系统参数配置
	 */
	// aop method
	public final static String AOP_NUM_QRY = "ecaop.trades.query.comm.simpsnres.qry";
	public final static String AOP_NUM_CHG = "ecaop.trades.query.comm.simpsnres.chg";
	public final static String AOP_NUM_CHG_NEW = "ecaop.trades.query.comm.snres.chg"; //复杂版的号码预占

	public final static String ORIG_DOMAIN = "USS";
	public final static String ACTION_CODE = "0";
//	public final static String USS_USL_AJAX_HN = "http://133.71.24.117:15114/axis2/services/SimpleService/SimpleInOutMessageReceiver";
	
	public final static String USS_USL_AJAX_HN = "http://210.13.232.49:8092/test_interf_plat/services/SimpleService/SimpleInOutMessageReceiver";

	public final static String USS_ACTION = "urn:SimpleInOutMessageReceiver";
}
