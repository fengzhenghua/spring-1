package com.tydic.unicom.apc.business.common.interfaces;

import com.tydic.unicom.exception.BusinessException;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月6日 下午6:56:20
 * @ClassName IApcServiceHandler
 * @Description APC服务处理接口
 * @version V1.0
 */
public interface IApcServiceHandler {
	
	UocMessage getMessage(String json_in,String method_name) throws BusinessException;
}
