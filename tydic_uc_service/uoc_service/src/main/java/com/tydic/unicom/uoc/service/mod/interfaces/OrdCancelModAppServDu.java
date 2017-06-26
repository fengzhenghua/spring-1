package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.OrdCancelModAppVo;

public interface OrdCancelModAppServDu {

	public OrdCancelModAppVo queryOrdCancelModApp(OrdCancelModAppVo vo)throws Exception;
	
	public List<OrdCancelModAppVo> queryOrdCancelModAppByVo(OrdCancelModAppVo ordCancelModAppVo)throws Exception;
}
