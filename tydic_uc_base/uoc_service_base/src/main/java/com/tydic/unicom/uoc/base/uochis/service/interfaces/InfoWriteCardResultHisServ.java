package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.common.po.WriteCardResultInPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoWriteCardResultHisPo;

public interface InfoWriteCardResultHisServ {
	
	
	
	public boolean createInfoWriteCardResultHisPo(InfoWriteCardResultHisPo po)throws Exception;
	
	public List<InfoWriteCardResultHisPo> queryInfoWriteCardResultHis(WriteCardResultInPo po,int pageNo,int pageSize)throws Exception;
	
	public int queryInfoWriteCardResultCount(WriteCardResultInPo po)throws Exception;

}
