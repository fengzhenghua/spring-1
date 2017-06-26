package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.OrdModAttrCheckRuleVo;

public interface OrdModAttrCheckRuleServDu {

	public boolean create(OrdModAttrCheckRuleVo ordModAttrCheckRuleVo) throws Exception;
	
	public boolean update(OrdModAttrCheckRuleVo ordModAttrCheckRuleVo)throws Exception;
	
	public boolean delete(OrdModAttrCheckRuleVo ordModAttrCheckRuleVo)throws Exception;
	
	public List<OrdModAttrCheckRuleVo> queryOrdModAttrCheckRuleList(OrdModAttrCheckRuleVo ordModAttrCheckRuleVo,int pageNo,int pageSize)throws Exception;
	
	public int queryOrdModAttrCheckRuleListCount(OrdModAttrCheckRuleVo ordModAttrCheckRuleVo)throws Exception;

	public List<OrdModAttrCheckRuleVo> queryOrdModAttrCheckRuleAll() throws Exception;
}
