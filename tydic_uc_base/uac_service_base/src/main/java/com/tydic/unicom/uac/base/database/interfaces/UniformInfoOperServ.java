package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.UniformInfoOperPo;

public interface UniformInfoOperServ {

	/**统一工号登陆*/
	public UniformInfoOperPo uniformLoginIn(UniformInfoOperPo uniformInfoOperPo) throws Exception;
	
	public List<UniformInfoOperPo> queryUniformInfoOperByUniformOperOrDeviceNumber(UniformInfoOperPo uniformInfoOper);
}
