package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgDefineVo;

public interface CodeTaskPkgDefineServDu {

	public boolean create(CodeTaskPkgDefineVo vo)throws Exception;
	
	public boolean update(CodeTaskPkgDefineVo vo)throws Exception;
	
	public boolean delete(CodeTaskPkgDefineVo vo)throws Exception;
	
	public List<CodeTaskPkgDefineVo> queryCodeTaskPkgDefine(CodeTaskPkgDefineVo vo,int pageNo, int pageSize) throws Exception;
	
	public int queryCodeTaskPkgDefineCount(CodeTaskPkgDefineVo vo) throws Exception;
	
	public CodeTaskPkgDefineVo queryCodeTaskPkgDefineByPkgId(CodeTaskPkgDefineVo vo) throws Exception;
	
}
