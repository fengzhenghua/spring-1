package com.tydic.unicom.apc.service.common.interfaces;

import java.util.List;

import com.tydic.unicom.apc.business.pub.vo.ApAttrVo;
import com.tydic.unicom.apc.service.common.vo.ApcRedisData;
import com.tydic.unicom.webUtil.UocMessage;

public interface ApcRedisServiceServDu {

	/**
	 * 添加或更新redis数据（触点属性使用）
	 * */
	public UocMessage addOrUpdateForApAttr(List<ApAttrVo> list) throws Exception;
	
	/**
	 * 获取redis数据（触点属性使用）
	 * */
	public UocMessage queryForApAttr(String key) throws Exception;
	
	/**
	 * 删除redis数据（触点属性使用）
	 * */
	public UocMessage deleteForApAttr(String key) throws Exception;
	
	/**
	 * 添加redis缓存数据（通用方法）
	 * */
	public UocMessage create(ApcRedisData apcRedisData) throws Exception;
	
	/**
	 * 更新redis缓存数据（通用方法）
	 * */
	public UocMessage update(ApcRedisData apcRedisData) throws Exception;
	
	/**
	 * 删除redis缓存数据（通用方法）
	 * */
	public UocMessage delete(String key) throws Exception;
	
	/**
	 * 获取redis缓存数据（通用方法）
	 * */
	public UocMessage query(String key) throws Exception;
	
}
