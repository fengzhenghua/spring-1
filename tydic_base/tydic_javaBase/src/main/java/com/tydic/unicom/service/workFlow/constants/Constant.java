package com.tydic.unicom.service.workFlow.constants;

public class Constant {
		public static String 		SUCCESS_FLAG 								= "OK";
		public static String 		FAIL_FLAG 											= "FAIL";
		
		/*工作流流程定义*/
		public static String 		WF_ORDER 										=	"WF_ORDER";								//订单流程定义
		public static String		WF_FLAG												=	"WF_FLAG";									//工作流返回成功/失败标记(KEY)
		
		/*订单状态*/
		public static String		ORDER_STATUS_TO_BE_PAID					=	"A10";												//待支付
		public static String		ORDER_STATUS_SYS_CONFIRM			=	"A20";												//系统确认
		public static String		ORDER_STATUS_PRINT								=	"A30";												//打印完成
		public static String		ORDER_STATUS_PICKING							=	"A40";												//捡货完成
		public static String		ORDER_STATUS_PACK								=	"A50";												//打包完成
		public static String		ORDER_STATUS_SEND								=	"A60";												//已发货
		public static String		ORDER_STATUS_END									=	"A90";												//订单完成
		public static String		ORDER_STATUS_CANCLE							=	"A99";												//订单取消
		public static String		ORDER_STATUS_LOCK								=	"A98";												//订单锁定
		
		/*支付方式*/
		public static String 		PAY_TYPE_FTRST								= "PAY_FIRST";								//在线支付
		public static String 		PAY_TYPE_LAST									= "PAY_LAST";								//货到付款
		
		
		public static String OFR_TYPE_MOB = "101";
		public static String OFR_TYPE_DINNER = "201";
}
