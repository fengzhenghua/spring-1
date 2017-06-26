package com.tydic.unicom.uoc.service.code.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.code.vo.ProcTacheRetVo;

public interface ProcTacheRetServDu {

	public List<ProcTacheRetVo> queryProcTacheRetByVo(ProcTacheRetVo vo) throws Exception;

}
