package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.OrdModStateRuleVo;



public interface OrdModStateRuleServDu {

	
	public boolean create(OrdModStateRuleVo ordModStateRuleVo) throws Exception;
	
	public boolean delete(OrdModStateRuleVo ordModStateRuleVo)throws Exception;
	
	public boolean update(OrdModStateRuleVo ordModStateRuleVo)throws Exception;
	
	public OrdModStateRuleVo getOrdModStateRuleById(OrdModStateRuleVo ordModStateRuleVo)throws Exception;
	
	/*
	 * 分页
	 */
	public List<OrdModStateRuleVo> getOrdModStateRuleList(OrdModStateRuleVo ordModStateRuleVo,int pageNo,int pageSize)throws Exception;
		
	/*
	 * 根据oper_code+stat_code查询服务订单状态关系表
	 */
	public OrdModStateRuleVo queryOrdModStateRuleByOperCodeAndStateCode(OrdModStateRuleVo vo) throws Exception;
	
	/*
	 * 分页，统计条数
	 */
	public int queryOrdModStateRuleCount(OrdModStateRuleVo ordVo)throws Exception;
	
	public List<OrdModStateRuleVo> queryOrdModStateRuleAll() throws Exception;
	
}
