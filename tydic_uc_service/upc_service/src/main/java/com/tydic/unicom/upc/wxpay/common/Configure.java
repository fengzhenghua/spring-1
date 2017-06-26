package com.tydic.unicom.upc.wxpay.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.tydic.unicom.upc.vo.UpcPropertiesVo;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:40
 * 这里放置各种配置数据
 */
@Component
public class Configure {

	@Autowired
	private UpcPropertiesVo upcPropertiesVo;
	
	//是否使用异步线程的方式来上报API测速，默认为异步模式
	private static boolean useThreadToDoReport = true;

	//机器IP
	private static String ip = "";
	
	//货币类型
	private static String feeType = "CNY";

	//以下是几个API的路径：
	//1）被扫支付API
	public static String PAY_API;// = "https://api.mch.weixin.qq.com/pay/micropay";

	//2）被扫支付查询API
	public static String PAY_QUERY_API;// = "https://api.mch.weixin.qq.com/pay/orderquery";

	//3）退款API
	public static String REFUND_API;// = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	//4）退款查询API

	public static String REFUND_QUERY_API;// = "https://api.mch.weixin.qq.com/pay/refundquery";

	//5）撤销API
	public static String REVERSE_API;// = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

	//6）下载对账单API
	public static String DOWNLOAD_BILL_API;// = "https://api.mch.weixin.qq.com/pay/downloadbill";

	//7) 统计上报API
	public static String REPORT_API;// = "https://api.mch.weixin.qq.com/payitil/report";
	
	//统一下单
	public static String UNIFIED_ORDER_API;// = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static boolean isUseThreadToDoReport() {
		return useThreadToDoReport;
	}

	public static String HttpsRequestClassName;// = "com.tydic.unicom.upc.wxpay.common.HttpsRequest";

	/***********将变量赋值*************/
	@PostConstruct
	public void init(){
		PAY_API = upcPropertiesVo.getPAY_API();
		PAY_QUERY_API = upcPropertiesVo.getPAY_QUERY_API();
		REFUND_API = upcPropertiesVo.getREFUND_API();
		REFUND_QUERY_API = upcPropertiesVo.getREFUND_QUERY_API();
		REVERSE_API = upcPropertiesVo.getREVERSE_API();
		DOWNLOAD_BILL_API = upcPropertiesVo.getDOWNLOAD_BILL_API();
		REPORT_API = upcPropertiesVo.getREPORT_API();
		UNIFIED_ORDER_API = upcPropertiesVo.getUNIFIED_ORDER_API();
		HttpsRequestClassName =upcPropertiesVo.getHttpsRequestClassName();
	}
	
	public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
		Configure.useThreadToDoReport = useThreadToDoReport;
	}
	public static String getFeeType() {
		return feeType;
	}
	
	public static void setIp(String ip) {
		Configure.ip = ip;
	}

	public static String getIP(){
		return ip;
	}
	
}
