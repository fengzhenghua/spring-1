package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgAppVo;

public interface CodeTaskPkgAppServDu {

	public boolean create(CodeTaskPkgAppVo vo)throws Exception;

	public boolean update(CodeTaskPkgAppVo vo)throws Exception;

	public boolean delete(CodeTaskPkgAppVo vo)throws Exception;

	public List<CodeTaskPkgAppVo> queryCodeTaskPkgApp(CodeTaskPkgAppVo vo,int pageNo,int pageSize)throws Exception;

	public int queryCodeTaskPkgAppCount(CodeTaskPkgAppVo vo)throws Exception;

	public List<CodeTaskPkgAppVo> queryCodeTaskPkgAppByVo(CodeTaskPkgAppVo vo) throws Exception;

}
