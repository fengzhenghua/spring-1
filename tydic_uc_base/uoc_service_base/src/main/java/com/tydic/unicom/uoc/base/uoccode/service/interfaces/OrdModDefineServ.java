package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModDefinePo;

public interface OrdModDefineServ {

	/**
	 * 操作订单模板定义表
	 * @param ordModDefinePo
	 * @return
	 */
	public boolean create(OrdModDefinePo ordModDefinePo);
	
	public boolean update(OrdModDefinePo ordModDefinePo);
	
	public boolean delete(OrdModDefinePo ordModDefinePo);
	
	public OrdModDefinePo queryOrdModDefineByModCode(OrdModDefinePo ordModDefinePo);
	
	public List<OrdModDefinePo> queryOrdModDefineList(OrdModDefinePo ordModDefinePo,int pageNo,int pageSize)throws Exception;

	public int queryOrdModDefineListCount(OrdModDefinePo ordModDefinePo)throws Exception;
	
	/**
	 * 查询OrdModDefine全部数据
	 * */
	public List<OrdModDefinePo> queryOrdModDefineAll() throws Exception;
	
}
