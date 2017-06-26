package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgAppPo;

public interface CodeTaskPkgAppServ {

	public List<CodeTaskPkgAppPo> queryCodeTaskPkgAppById(CodeTaskPkgAppPo po) throws Exception;
	
	public List<CodeTaskPkgAppPo> queryCodeTaskPkgApp(CodeTaskPkgAppPo po,int pageNo,int pageSize) throws Exception;
	
	public int queryCodeTaskPkgAppCount(CodeTaskPkgAppPo po) throws Exception;
	
	public boolean create(CodeTaskPkgAppPo po)throws Exception;
	
	public boolean update(CodeTaskPkgAppPo po)throws Exception;
	
	public boolean delete(CodeTaskPkgAppPo po)throws Exception;
	
}
