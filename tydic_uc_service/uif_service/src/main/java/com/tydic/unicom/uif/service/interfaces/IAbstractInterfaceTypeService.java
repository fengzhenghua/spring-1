package com.tydic.unicom.uif.service.interfaces;

import com.tydic.unicom.uif.service.vo.AbilitReqestVo;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年5月17日 下午6:29:45
 * @ClassName IAbstractInterfaceTypeService
 * @Description 抽象接口类型服务
 * @version V1.0
 */
public interface IAbstractInterfaceTypeService {
	
	/**
	 * 
	 * @author heguoyong 2017年5月17日 下午6:54:37
	 * @Method: getAblitReturn 
	 * @Description: 获取能力调用返回值
	 */
	UocMessage getAblitReturn(AbilitReqestVo requestVo);
}
