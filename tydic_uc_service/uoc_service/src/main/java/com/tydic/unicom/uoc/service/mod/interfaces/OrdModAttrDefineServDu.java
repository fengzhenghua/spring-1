package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.OrdModAttrDefineVo;

public interface OrdModAttrDefineServDu {

	/*
	 * 根据订单模版编码模版参数定义表
	 */
	public List<OrdModAttrDefineVo> queryOrdModAttrDefineByModCode(OrdModAttrDefineVo vo) throws Exception;
	
	public boolean create(OrdModAttrDefineVo vo)throws Exception;
	
	public boolean update(OrdModAttrDefineVo vo)throws Exception;
	
	public boolean delete(OrdModAttrDefineVo vo)throws Exception;
	
	public List<OrdModAttrDefineVo> queryOrdModAttrDefineList(OrdModAttrDefineVo vo,int pageNo,int pageSize) throws Exception;
	
	public int queryOrdModAttrDefineListCount(OrdModAttrDefineVo vo) throws Exception;
	
	public List<OrdModAttrDefineVo> queryOrdModAttrDefineAll() throws Exception;
	
}
