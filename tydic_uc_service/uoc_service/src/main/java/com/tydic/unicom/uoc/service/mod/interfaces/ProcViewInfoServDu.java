package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.ProcViewInfoVo;

public interface ProcViewInfoServDu {

	public List<ProcViewInfoVo> queryProcViewInfoByProc(ProcViewInfoVo vo)throws Exception;
}
