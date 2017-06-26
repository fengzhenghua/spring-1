package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSmsWarnPo;

public interface InfoSmsWarnServ {

	public boolean createInfoSmsWarn(InfoSmsWarnPo po)throws Exception;

	public boolean deleteInfoSmsWarn(InfoSmsWarnPo po) throws Exception;

}
