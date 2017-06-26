package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.InfoOperPo;

public interface InfoOperServ {

	public List<InfoOperPo> queryInfoOperOperNo(InfoOperPo infoOperPo) throws Exception;
	
	public InfoOperPo getInfoOperByLoginCode(InfoOperPo infoOperPo) throws Exception;
	
	public List<InfoOperPo> queryInfoOperByOperNoAndOperName(String currDeptNo,String operNo, String operName) throws Exception;
}
