package com.tydic.unicom.uoc.service.sms.vo;


public enum EnSmsSentFlag {
	/**
	 * 待发送
	 */
	READY,
	/**
	 * 发送成功
	 */
	OK,
	/**
	 * 发送失败
	 */
	FAILED,
	/**
	 * 单条短信发送时间间隔限制（如向某手机发送短信，两条间隔不能小于1分钟）
	 */
	FAILED_INTERVAL_LIMIT,
	/**
	 * 一定分钟内超过指定次数（如向某手机发送短信，15分钟内不能超过3条）
	 */
	FAILED_TIMES_OF_MINUTES_LIMIT,
	/**
	 * 超过一天指定的次数（如向某手机发送短信，一天不能超过20条）
	 */
	FAILED_TIMES_OF_DAY_LIMIT,
	/**
	 * 短信超过指定长度（如长短信内容不能超过500字符）
	 */
	FALIED_SMS_LENGTH_LIMIT,
	/**
	 * 短信号码指定个数（如长短号码不能超过500个）
	 */
	FALIED_SMS_MOBILE_NUM_LIMIT;
}
