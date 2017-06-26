package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.ProcTacheRetPo;

public interface ProcTacheRetServ {

	public List<ProcTacheRetPo> queryProcTacheRetByPo(ProcTacheRetPo po) throws Exception;

}
