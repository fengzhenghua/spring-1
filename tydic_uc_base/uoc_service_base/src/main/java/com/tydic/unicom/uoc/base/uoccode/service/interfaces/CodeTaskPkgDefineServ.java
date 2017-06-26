package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgDefinePo;

public interface CodeTaskPkgDefineServ {

	public List<CodeTaskPkgDefinePo> queryCodeTaskPkgDefineByPo(CodeTaskPkgDefinePo po) throws Exception;
	
	public List<CodeTaskPkgDefinePo> queryCodeTaskPkgDefine(CodeTaskPkgDefinePo po,int pageNo,int pageSize) throws Exception;
	
	public int queryCodeTaskPkgDefineCount(CodeTaskPkgDefinePo po) throws Exception;
	
    public boolean create(CodeTaskPkgDefinePo po)throws Exception;
	
	public boolean update(CodeTaskPkgDefinePo po)throws Exception;
	
	public boolean delete(CodeTaskPkgDefinePo po)throws Exception;
}
