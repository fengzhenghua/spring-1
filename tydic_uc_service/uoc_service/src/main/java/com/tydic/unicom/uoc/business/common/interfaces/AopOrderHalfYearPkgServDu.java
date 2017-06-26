package com.tydic.unicom.uoc.business.common.interfaces;


import com.tydic.unicom.uoc.business.common.vo.AopHalfYearPkgVo;

import com.tydic.unicom.webUtil.UocMessage;

public interface AopOrderHalfYearPkgServDu{
	
	public UocMessage getAopHalfYearPkg(AopHalfYearPkgVo vo) throws Exception;
}
