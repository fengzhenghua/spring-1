package com.tydic.unicom.aspect.log;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月28日 上午11:31:08
 * @ClassName EnSystemAuditLogType
 * @Description 日志记录类型
 * @version V1.0
 */
public enum EnSystemAuditLogType {
	/**
	 * 数据库
	 */
	DATABASE("database"),
	/**
	 * 日志文件
	 */
	LOGFILE("logfile"),
	/**
	 * 数据库和日志
	 */
	DATABASEANDLOGFILE("databaseAndLogfile");
	
	private String code;
	
	public String getCode() {
		return this.code;
	}
	
	EnSystemAuditLogType(String code) {
		this.code = code;
	}
}
