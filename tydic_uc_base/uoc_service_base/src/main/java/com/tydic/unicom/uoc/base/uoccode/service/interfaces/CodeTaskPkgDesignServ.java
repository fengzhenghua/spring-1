package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgDesignPo;

public interface CodeTaskPkgDesignServ {

	public List<CodeTaskPkgDesignPo> queryCodeTaskPkgDesignByPo(CodeTaskPkgDesignPo po) throws Exception;
	
	public List<CodeTaskPkgDesignPo> queryCodeTaskPkgDesign(CodeTaskPkgDesignPo po, int pageNo, int pageSize) throws Exception;
	
	public int queryCodeTaskPkgDesignCount(CodeTaskPkgDesignPo po) throws Exception;
	
	public boolean create(CodeTaskPkgDesignPo po)throws Exception;
	
	public boolean update(CodeTaskPkgDesignPo po)throws Exception;
	
	public boolean delete(CodeTaskPkgDesignPo po)throws Exception;
}
