package com.tydic.unicom.util;

import com.tydic.unicom.security.BaseObject;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年5月9日 下午6:10:54
 * @ClassName ErrorCode
 * @Description 故障码
 * @version V1.0
 */
public class ErrorCode extends BaseObject{
	/**
	 *
	 */
	private static final long serialVersionUID = 118738386402649318L;

	/****** 返回请求结果成功或失败 ********/
	// 初始值
	public static final String CODE_DEFAULT = "0000";
	// 成功
	public static final String CODE_SUCCESS = "0000";
	// 链接网络失败
	public static final String CODE_LINK_FAILURE = "0001";

	/* 数据不存在 */
	public static final String CODE_DATA_NONEXIST = "0003";
	/* SQL错误 */
	public static final String CODE_DATA_SQL_ERR = "0004";
	/* 参数存在不能为空的字段 */
	public static final String CODE_NONEMPTY = "0005";
	/* 数据已存在 */
	public static final String CODE_DATA_EXIST = "0006";
	/* 数据不匹配 */
	public static final String CODE_DATA_NO_MATCH = "0007";
	// 请求超时
	public static final String CODE_LINK_TIME_OUT = "0008";
	// 特殊
	public static final String CODE_SPECIAL = "9999";
	// 校验码错误
	public static final String CODE_WRONG_AUTHCODE = "0009";

	/* 加解密失败 */
	public static final String SERCRETFAIL = "0008";
}
