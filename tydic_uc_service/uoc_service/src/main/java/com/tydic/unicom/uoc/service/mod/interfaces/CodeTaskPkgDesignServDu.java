package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgDesignVo;


public interface CodeTaskPkgDesignServDu {

	public boolean create(CodeTaskPkgDesignVo vo)throws Exception;

	public boolean update(CodeTaskPkgDesignVo vo)throws Exception;

	public boolean delete(CodeTaskPkgDesignVo vo)throws Exception;

	public List<CodeTaskPkgDesignVo> queryCodeTaskPkgDesign(CodeTaskPkgDesignVo vo,int pageNo,int pageSize)throws Exception;

	public int queryCodeTaskPkgDesignCount(CodeTaskPkgDesignVo vo)throws Exception;

	public List<CodeTaskPkgDesignVo> queryCodeTaskPkgDesignByVo(CodeTaskPkgDesignVo vo) throws Exception;
}
