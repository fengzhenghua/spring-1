package com.tydic.unicom.aspect.log.interfaces;

import com.tydic.unicom.aspect.log.LogAuditDetailsPo;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月28日 下午3:56:43
 * @ClassName IAbstractLogAuditDetailsService
 * @Description 记录日志抽象接口
 * @version V1.0
 */
public interface IAbstractLogAuditDetailsService {
	/**
	 * 
	 * @author heguoyong 2017年4月28日 下午3:56:29
	 * @Method: doLog 
	 * @Description: 记录日志
	 */
	 void doLog(LogAuditDetailsPo LogAuditDetails);
}
