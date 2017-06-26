package com.tydic.unicom.apc.base.pub.interfaces;

import java.util.List;

import com.tydic.unicom.apc.base.pub.po.ApDefinePo;

public interface ApDefineServ {

	public ApDefinePo getApDefinePo(ApDefinePo po) throws Exception;

	public boolean addApDefinePo(ApDefinePo po) throws Exception;

	public boolean deleteApDefinePo(ApDefinePo po) throws Exception;

	public boolean updateApDefinePo(ApDefinePo po) throws Exception;

	public List<ApDefinePo> queryApDefinePageByPo(ApDefinePo po, int pageNo, int pageSize) throws Exception;

	public int queryApDefineCount(ApDefinePo po) throws Exception;
	
	public List<ApDefinePo> queryApDefineByPo(ApDefinePo po) throws Exception;
	
	public List<ApDefinePo> queryApDefineByApIdOrApName(ApDefinePo po) throws Exception;
	
	public List<ApDefinePo> queryApDefineAll(ApDefinePo po) throws Exception;
	
	/**
	 * 获取有效触点（支持分页查询）
	 * */
	public List<ApDefinePo> queryEffectiveApDefinePage(ApDefinePo po, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 获取无效触点（支持分页查询）
	 * */
	public List<ApDefinePo> queryInvalidApDefinePage(ApDefinePo po, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 获取有效触点总数
	 * */
	public int queryEffectiveApDefineCount(ApDefinePo po) throws Exception;
	
	/**
	 * 获取无效触点总数
	 * */
	public int queryInvalidApDefineCount(ApDefinePo po) throws Exception;
}
