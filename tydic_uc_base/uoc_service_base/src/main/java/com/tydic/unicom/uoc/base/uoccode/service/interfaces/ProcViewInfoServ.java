package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.ProcViewInfoPo;

public interface ProcViewInfoServ {

	public List<ProcViewInfoPo> queryProcViewInfo(ProcViewInfoPo po)throws Exception;
}
