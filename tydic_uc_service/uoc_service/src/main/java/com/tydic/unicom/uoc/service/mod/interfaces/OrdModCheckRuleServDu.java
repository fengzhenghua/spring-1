package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.OrdModCheckRuleVo;

public interface OrdModCheckRuleServDu {

	public boolean create(OrdModCheckRuleVo ordModCheckRuleVo)throws Exception;
	
	public boolean update(OrdModCheckRuleVo ordModCheckRuleVo)throws Exception;
	
	public boolean delete(OrdModCheckRuleVo ordModCheckRuleVo)throws Exception;
	
	public List<OrdModCheckRuleVo> queryOrdModCheckRuleList(OrdModCheckRuleVo ordModCheckRuleVo,int pageNo,int pageSize)throws Exception;
	
	
	public int queryOrdModCheckRuleListCount(OrdModCheckRuleVo ordModCheckRuleVo)throws Exception;
	
	public List<OrdModCheckRuleVo> queryOrdModCheckRuleAll() throws Exception;
}
