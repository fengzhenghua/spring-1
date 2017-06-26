package com.tydic.unicom.uac.base.database.interfaces;

import com.tydic.unicom.uac.base.database.po.LogUniformInfoOperPo;

public interface LogUniformInfoOperServ {

	/**记录登陆信息日志*/
	public boolean addLogUniformInfoOper(LogUniformInfoOperPo logUniformInfoOperPo) throws Exception;
}
