package com.tydic.unicom.apc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tydic.unicom.apc.business.common.interfaces.ApcServiceDispatchServDu;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月6日 下午5:34:17
 * @ClassName ApcServiceRest
 * @Description 提供给APCRest服务
 * @version V1.0
 */
@RestController
@RequestMapping(value = ControllerMappings.APC)
public class ApcServiceRest {
	/**
	 * 应用中不可直接使用日志系统（ Log4j、 Logback）中的 API，而应依赖使用日志框架
       SLF4J 中的 API，使用门面模式的日志框架，有利于维护和各个类的日志处理方式统一
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	//private final Logger restLogger = LoggerFactory.getLogger("apcServiceLogger");
	
	@Autowired
	private ApcServiceDispatchServDu apcServiceDispatchServDu;
	
	@RequestMapping(value = UrlsMappings.APC_SERVICE,method=RequestMethod.POST)
	public UocMessage getOptionalAtouchPoint(String json_info){
		UocMessage uocMessage = new UocMessage();
		try {
			/**
			 * 对 trace/debug/info 级别的日志输出，必须使用条件输出形式或者使用占位符的方式。
			 */
			if(logger.isDebugEnabled()){
				logger.info("===========================获取可选触点信息（apc）========================json_info:"+json_info);
			}
			uocMessage = apcServiceDispatchServDu.createHandler(json_info);
		} catch (Exception e) {
			/**
			 * 异常处理时候，不要捕获 Java 类库中定义的继承自 RuntimeException 的运行时异常类，
			 * 打印异常信息应该包括两类信息：案发现场信息和异常堆栈信息。如果不处理，那么往上抛。
			 * 禁止打印堆栈
			 */
			logger.error(String.format("json_info:%s", json_info)+e.getMessage(),e);
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("apc服务异常");
			return uocMessage;
		}
		return uocMessage;
	}
}
