package com.tydic.unicom.upc.service.code.interfaces;

import java.util.List;

import com.tydic.unicom.upc.vo.code.SystemBusiParaVo;

public interface SystemBusiParaServDu {

	/**
	 * 获取参数分组信息
	 * @param paraGroup
	 * @return
	 */
	public List<SystemBusiParaVo> queryByParaGroup(String paraGroup) throws Exception;
	
	/**
	 * 获取参数信息
	 * @param paraGroup
	 * @param paraCode
	 * @return
	 */
	public SystemBusiParaVo queryByParaGroupAndCode(String paraGroup, String paraCode) throws Exception;
	
	/**
	 * 获取参数信息
	 * @param paraCode
	 * @return
	 */
	public SystemBusiParaVo queryByParaCode(String paraCode) throws Exception;
}
