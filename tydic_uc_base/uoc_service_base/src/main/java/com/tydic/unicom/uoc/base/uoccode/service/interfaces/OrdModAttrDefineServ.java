package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModAttrDefinePo;

public interface OrdModAttrDefineServ {
	
	/**
	 * 操作模板参数定义表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	/*
	 * 根据订单模版编码模版参数定义表
	 */
	public List<OrdModAttrDefinePo> queryOrdModAttrDefineByModCode(OrdModAttrDefinePo po) throws Exception;

	public boolean create(OrdModAttrDefinePo po);
	
	public boolean update(OrdModAttrDefinePo po);
	
	public boolean delete(OrdModAttrDefinePo po);
	
	public List<OrdModAttrDefinePo> queryOrdModAttrDefineList(OrdModAttrDefinePo po,int pageNo,int pageSize) throws Exception;
	
	public int queryOrdModAttrDefineListCount(OrdModAttrDefinePo po) throws Exception;
	
	public List<OrdModAttrDefinePo> queryAllOrdModAttrDefineList(OrdModAttrDefinePo po) throws Exception;
	
	/**
	 * 查询OrdModAttrDefine全部数据
	 * */
	public List<OrdModAttrDefinePo> queryOrdModAttrDefineAll() throws Exception;
	
}
