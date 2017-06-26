package com.tydic.unicom.uoc.base.uoccode.service.interfaces;


import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.ProcModTacheLoginPo;

public interface ProcModTacheLoginServ {

	/**
	 * 操作环节关联工号表
	 * @param procModTacheLoginPo
	 * @return
	 */
	public List<ProcModTacheLoginPo> queryProcModTacheLoginByTacheCode(ProcModTacheLoginPo procModTacheLoginPo);
	
	public boolean create(ProcModTacheLoginPo procModTacheLoginPo)throws Exception;
	
	public boolean update(ProcModTacheLoginPo procModTacheLoginPo)throws Exception;
	
	public boolean delete(ProcModTacheLoginPo procModTacheLoginPo)throws Exception;
	
	public List<ProcModTacheLoginPo> queryProcModTacheLoginList(ProcModTacheLoginPo procModTacheLoginPo,int pageNo,int pageSize) throws Exception;
	
	public int queryProcModTacheLoginListCount(ProcModTacheLoginPo procModTacheLoginPo)throws Exception;
	
	/**
	 * 查询ProcModTacheLogin全部数据
	 * */
	public List<ProcModTacheLoginPo> queryProcModTacheLoginAll() throws Exception;
}
