package com.tydic.unicom.aspect.log.interfaces;

import com.tydic.unicom.aspect.log.LogAuditPo;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月28日 下午2:27:38
 * @ClassName IAbstractLogAuditService
 * @Description 日志服务接口
 * @version V1.0
 */
public interface IAbstractLogAuditService {
	/**
	 * 
	 * @author heguoyong 2017年4月28日 下午3:57:06
	 * @Method: doLog 
	 * @Description: 写日志
	 */
	void doLog(LogAuditPo LogAudit);
}
