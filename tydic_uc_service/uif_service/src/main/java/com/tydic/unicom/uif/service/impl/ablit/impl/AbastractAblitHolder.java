package com.tydic.unicom.uif.service.impl.ablit.impl;

import com.tydic.unicom.security.BaseObject;
import com.tydic.unicom.uif.service.interfaces.IAbstractInterfaceTypeService;
import com.tydic.unicom.uif.service.vo.AbilitReqestVo;
import com.tydic.unicom.webUtil.UocMessage;

public abstract class AbastractAblitHolder implements IAbstractInterfaceTypeService {
	
	/**
	 * 
	 * @Method: buildReturn
	 * @Description: 获取返回信息
	 */
	public abstract UocMessage buildReturn(String returnMsg);
	
	/**
	 * 
	 * @author heguoyong 2017年5月18日 上午11:50:46
	 * @Method: buildCallMessage
	 * @Description: 构建请求数据
	 */
	public abstract BaseObject buildCallMessage(AbilitReqestVo requestVo);
}
