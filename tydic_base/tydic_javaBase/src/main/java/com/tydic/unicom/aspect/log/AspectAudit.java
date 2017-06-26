package com.tydic.unicom.aspect.log;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.tydic.unicom.aspect.annotation.SystemAuditLog;
import com.tydic.unicom.aspect.log.interfaces.IAbstractLogAuditDetailsService;
import com.tydic.unicom.aspect.log.interfaces.IAbstractLogAuditService;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.util.Http;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月28日 下午5:41:49
 * @ClassName AspectAudit
 * @Description 审计日志切面
 * @version V1.0
 */
@Component
@Aspect
public class AspectAudit {
	
	private static Logger logger = LoggerFactory.getLogger(AspectAudit.class);
	
	private IAbstractLogAuditService abstractAuditService;
	
	private IAbstractLogAuditDetailsService abstractAuditDetailService;
	
	public void setAbstractAuditService(IAbstractLogAuditService abstractAuditService) {
		this.abstractAuditService = abstractAuditService;
	}
	
	public void setAbstractAuditDetailService(IAbstractLogAuditDetailsService abstractAuditDetailService) {
		this.abstractAuditDetailService = abstractAuditDetailService;
	}
	
	/**
	 * 审计日志切点
	 */
	@Pointcut("@annotation(com.tydic.unicom.aspect.annotation.SystemAuditLog)")
	public void systemAuditLogAspect() {
	}
	
	/**
	 * 后置拦截 用于拦截Controller层记录用户的操作返回，含操作参数记录
	 *
	 * @param joinPoint 切点
	 */
	@AfterReturning(value = "systemAuditLogAspect()", returning = "returnValue")
	public void doSystemAuditLog(JoinPoint joinPoint, Object returnValue) {
		// 日志
		LogAuditPo logAuditPo = setLogAudit(joinPoint);
		logger.debug("获取切面描述");
		logAuditPo.setFunctionDescription(getSystemAuditLogAnnotationDescription(joinPoint));
		logger.debug("设置日志详情");
		// 日志详情
		LogAuditDetailsPo logAuditDetailsPo = setLogAuditDetails(joinPoint, returnValue, logAuditPo);
		// 保存数据库
		logger.debug("获取日志存储类型");
		String saveLogType = getSystemAuditLogType(joinPoint);
		logger.info("日志存储类型为：{}", saveLogType);
		// 记录到数据库
		if (StringUtils.pathEquals(saveLogType, EnSystemAuditLogType.DATABASE.getCode())) {
			abstractAuditService.doLog(logAuditPo);
			logger.info("日志存入数据库完成");
			abstractAuditDetailService.doLog(logAuditDetailsPo);
			logger.info("日志详情存入数据库完成");
			// 记录到文件
		} else
			if (StringUtils.pathEquals(saveLogType, EnSystemAuditLogType.LOGFILE.getCode())) {
				doLog(logAuditPo, getAnnotationLogname(joinPoint));
				doLogDetail(logAuditDetailsPo, getAnnotationDetailLogname(joinPoint));
				// 记录到文件和数据库
			} else {
				abstractAuditService.doLog(logAuditPo);
				logger.info("日志存入数据库完成");
				abstractAuditDetailService.doLog(logAuditDetailsPo);
				logger.info("日志详情存入数据库完成");
				doLog(logAuditPo, getAnnotationLogname(joinPoint));
				doLogDetail(logAuditDetailsPo, getAnnotationDetailLogname(joinPoint));
			}
	}
	
	/**
	 * 日志文件记录方法调用
	 *
	 * @param logAudit
	 */
	public void doLog(LogAuditPo logAudit, String logname) {
		if (null != logname) {
			logger = LoggerFactory.getLogger(logname);
		}
		logger.debug("开始记录日志");
		logger.info("审计日志: ".concat(JSON.toJSONString(logAudit)));
		logger.debug("日志存入日志完成");
	}
	
	/**
	 * 日志文件记录详情
	 *
	 * @param logAuditDetails
	 */
	public void doLogDetail(LogAuditDetailsPo logAuditDetails, String logname) {
		if (null != logname) {
			logger = LoggerFactory.getLogger(logname);
		}
		logger.debug("开始记录日志详情");
		logger.info("审计日志详情: ".concat(JSON.toJSONString(logAuditDetails)));
		logger.debug("日志详情存入日志完成");
	}
	
	/**
	 * 获取注解的日志详情文件名称
	 *
	 * @param joinPoint
	 * @return
	 */
	public static String getAnnotationDetailLogname(JoinPoint joinPoint) {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		String logname = null;
		SystemAuditLog systemAuditLog;
		try {
			@SuppressWarnings("rawtypes")
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName) && method.isAnnotationPresent(SystemAuditLog.class)) {
					systemAuditLog = method.getAnnotation(SystemAuditLog.class);
					if (null != systemAuditLog) {
						logname = systemAuditLog.detailLogname();
					}
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("日志切面获取注解日志文件错误", e);
		}
		return logname;
	}
	
	/**
	 * 获取注解的日志文件名称
	 *
	 * @param joinPoint
	 * @return
	 */
	public static String getAnnotationLogname(JoinPoint joinPoint) {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		String logname = null;
		SystemAuditLog systemAuditLog;
		try {
			@SuppressWarnings("rawtypes")
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName) && method.isAnnotationPresent(SystemAuditLog.class)) {
					systemAuditLog = method.getAnnotation(SystemAuditLog.class);
					if (null != systemAuditLog) {
						logname = systemAuditLog.logname();
					}
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("日志切面获取注解日志文件错误", e);
		}
		return logname;
	}
	
	/**
	 * 获取注解的日志类型
	 *
	 * @param joinPoint
	 * @return
	 */
	public static String getSystemAuditLogType(JoinPoint joinPoint) {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		EnSystemAuditLogType type = EnSystemAuditLogType.LOGFILE;
		SystemAuditLog systemAuditLog;
		try {
			@SuppressWarnings("rawtypes")
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName) && method.isAnnotationPresent(SystemAuditLog.class)) {
					systemAuditLog = method.getAnnotation(SystemAuditLog.class);
					if (null != systemAuditLog) {
						type = method.getAnnotation(SystemAuditLog.class).type();
					}
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("日志切面获取注解描述错误", e);
		}
		
		return type.getCode();
	}
	
	/**
	 * 获取注解的方法名称描述
	 *
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	public static String getSystemAuditLogAnnotationDescription(JoinPoint joinPoint) {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		EnSystemAuditLog description = EnSystemAuditLog.DEFUALT_SYS_LOG;
		SystemAuditLog systemAuditLog;
		try {
			@SuppressWarnings("rawtypes")
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName) && method.isAnnotationPresent(SystemAuditLog.class)) {
					systemAuditLog = method.getAnnotation(SystemAuditLog.class);
					if (null != systemAuditLog) {
						description = systemAuditLog.description();
					}
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("日志切面获取注解描述错误", e);
		}
		
		return description.getDescription();
	}
	
	/**
	 * 设置日志详情内容
	 *
	 * @param joinPoint
	 * @param returnValue
	 * @param logAudit
	 * @return
	 */
	private LogAuditDetailsPo setLogAuditDetails(JoinPoint joinPoint, Object returnValue, LogAuditPo logAudit) {
		
		LogAuditDetailsPo logAuditDetailsPo = new LogAuditDetailsPo();
		logAuditDetailsPo.setLogAuditId(logAudit.getLogId());
		// 记录方法全路径
		logAuditDetailsPo.setFunctionName(logAudit.getFunctionName());
		StringBuilder sb = new StringBuilder();
		logger.debug("获取参数名称");
		String[] paramsName = ((CodeSignature)joinPoint.getStaticPart().getSignature()).getParameterNames();
		Object[] paramsArgs = joinPoint.getArgs();
		sb.append("{");
		int paramNum = paramsName.length;
		
		String classname;
		logger.info("获取返回值 {} 个", paramNum);
		for (int i = 0; i < paramNum; i++) {
			if (null != paramsArgs[i]) {
				// 若参数不可序列化，则忽略
				if (!(paramsArgs[i] instanceof Serializable)) {
					continue;
				}
				classname = paramsArgs[i].getClass().getSimpleName();
				
				sb.append(paramsName[i]);
				sb.append(":");
				
				if (classname.equals("CommMultipartFile") || classname.equals("CommonsMultipartFile")) {
					sb.append(((CommonsMultipartFile)paramsArgs[i]).getOriginalFilename()); // 文件名称
				} else {
					logger.debug("参数 执行Json转换");
					sb.append(JSON.toJSONString(paramsArgs[i]));
					logger.debug("参数 执行Json转换完成");
				}
			} else {
				sb.append(paramsName[i]);
				sb.append(":");
			}
			if (paramNum > i - 1) {
				sb.append(",");
			}
		}
		sb.append("}");
		logAuditDetailsPo.setFunctionParam(sb.toString());
		if (null != returnValue) {
			String returnValueClass = returnValue.getClass().getSimpleName();
			String returnValueString = "";
			if (!returnValueClass.equals("ModelAndView")) {
				logger.debug("参数详情 执行Json转换");
				returnValueString = JSON.toJSONString(returnValue);
				logger.debug("参数详情  执行Json转换完成");
				if (returnValueString.indexOf("'success':false") != -1) {
					logAudit.setActionStatus(EnActionStatus.FAILED);
				}
			}
			
			logAuditDetailsPo.setFunctionReturn(returnValueString);
		}
		
		return logAuditDetailsPo;
	}
	
	/**
	 * 设置日志内容
	 *
	 * @param joinPoint
	 * @return
	 */
	private LogAuditPo setLogAudit(JoinPoint joinPoint) {
		/* 数据库日志 */
		LogAuditPo logAuditPo = new LogAuditPo();
		logAuditPo.setActorId("admin");
		logAuditPo.setActorName("系统管理员");
		// 读取session中的信息
		if(RequestContextHolder.getRequestAttributes()!=null){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String deviceId = "";
			// 请求的IP
			String ip = Http.getIpAddr(request);
			logAuditPo.setActorIp(ip);
			deviceId = request.getParameter("deviceId");
			if (StringUtils.isEmpty(deviceId)) {
				logAuditPo.setActorDeviceNumber(request.getHeader("User-Agent"));
			}
		}
		logAuditPo.setLogId(UUID.randomUUID().toString());
		logAuditPo.setFunctionName(getFunctionName(joinPoint));
		
		String now = DateUtil.getCurrentDate();
		logAuditPo.setActionTime(now);
		logAuditPo.setActionTimeMillisecond(now.substring(now.indexOf(".") + 1));
		
		logAuditPo.setActionStatus(EnActionStatus.SUCCESS);
		
		return logAuditPo;
	}
	
	/**
	 * 获取被切面的方法名称
	 *
	 * @param joinPoint
	 * @return
	 */
	private String getFunctionName(JoinPoint joinPoint) {
		logger.debug("获取切面函数");
		StringBuilder sb = new StringBuilder();
		sb.append(joinPoint.getTarget().getClass().getName());
		sb.append(".");
		sb.append(joinPoint.getSignature().getName());
		return sb.toString();
	}
}
