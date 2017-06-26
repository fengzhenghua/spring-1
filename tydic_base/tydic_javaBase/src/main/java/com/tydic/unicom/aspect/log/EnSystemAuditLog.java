package com.tydic.unicom.aspect.log;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月28日 上午11:26:30
 * @ClassName EnSystemAuditLog
 * @Description 系统日志类型枚举
 * @version V1.0
 */
public enum EnSystemAuditLog {
	DEFUALT_SYS_LOG("默认调用类型"),
	CALL_UOC_REST("调用UOC支撑服务"), 
	CALL_UOC_BUSI_SERVICE("调用UOC业务服务"), 
	CALL_UAC_REST("调用UAC支撑服务"),
	CALL_UAC_BUSI_SERVICE("调用UAC业务服务"),
	CALL_APC_REST("调用APC支撑服务"),
	CALL_APC_BUSI_SERVICE("调用APC业务服务"),
	CALL_UIF_REST("调用UIF支撑服务"),
	CALL_UIF_BUSI_SERVICE("调用UIF业务服务"),
	CALL_UGC_REST("调用UGC支撑服务"),
	CALL_UGC_BUSI_SERVICE("调用UGC业务服务"),
	CALL_ABILITY_PLAT("调用能力平台"),
	GET_ERROR("获取描述错误");
	
	private String description;
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	EnSystemAuditLog(String description) {
		this.description = description;
	}
}
