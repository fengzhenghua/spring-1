package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.OrdCancelModAppPo;

public interface OrdCancelModAppServ {

	public OrdCancelModAppPo queryOrdCancelModApp(OrdCancelModAppPo po)throws Exception;
	
	public List<OrdCancelModAppPo> queryOrdCancelModAppByPo(OrdCancelModAppPo ordCancelModAppPo) throws Exception;
}
