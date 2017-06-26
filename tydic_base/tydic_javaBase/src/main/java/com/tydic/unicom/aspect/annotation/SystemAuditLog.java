package com.tydic.unicom.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tydic.unicom.aspect.log.EnSystemAuditLog;
import com.tydic.unicom.aspect.log.EnSystemAuditLogType;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月28日 上午11:23:10
 * @ClassName SystemAuditLog
 * @Description 系统审计日志接口,记录接口输入日志
 * @version V1.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemAuditLog {
	
	EnSystemAuditLog description() default EnSystemAuditLog.DEFUALT_SYS_LOG ;
	
	EnSystemAuditLogType type() default EnSystemAuditLogType.LOGFILE;
	
	String logname() default "systemAuditLog";
	
	String detailLogname() default "systemAuditLog";
}
