package com.tydic.unicom.apc.service.pub.interfaces;

import java.util.List;
import com.tydic.unicom.apc.business.pub.vo.ApDefineVo;


public interface ApDefineServDu {

	public ApDefineVo getApDefine(ApDefineVo vo) throws Exception;

	public boolean addApDefine(ApDefineVo vo) throws Exception;

	public boolean deleteApDefine(ApDefineVo vo) throws Exception;

	public boolean updateApDefine(ApDefineVo vo) throws Exception;

	public List<ApDefineVo> queryApDefinePageByVo(ApDefineVo vo, int pageNo, int pageSize) throws Exception;

	public int queryApDefineCount(ApDefineVo vo) throws Exception;
	
	public List<ApDefineVo> queryApDefinePageByPo(ApDefineVo vo) throws Exception;
	
	/**
	 * 获取有效触点（支持分页查询）
	 * */
	public List<ApDefineVo> queryEffectiveApDefinePage(ApDefineVo vo, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 获取无效触点（支持分页查询）
	 * */
	public List<ApDefineVo> queryInvalidApDefinePage(ApDefineVo vo, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 获取有效触点总数
	 * */
	public int queryEffectiveApDefineCount(ApDefineVo vo) throws Exception;
	
	/**
	 * 获取无效触点总数
	 * */
	public int queryInvalidApDefineCount(ApDefineVo vo) throws Exception;
}
