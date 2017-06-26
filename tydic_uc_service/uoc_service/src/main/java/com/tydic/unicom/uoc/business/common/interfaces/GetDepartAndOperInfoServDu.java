package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface GetDepartAndOperInfoServDu {

	/**
	 * 获取可选部门信息（从云销售获取）
	 * */
	public UocMessage getDepartInfo(String jsessionId,String departNo,String departName) throws Exception;
	
	public UocMessage getDepartInfoNew(String jsession_id,String region_id,String depart_no,String depart_name,String query_type) throws Exception;
	/**
	 * 获取可选工号信息（从云销售获取）
	 * */
	public UocMessage getOperInfo(String jsessionId,String operNo,String operName) throws Exception;	
	/**
	 * 根据工号获取对应部门信息（从云销售获取）
	 * */
	public UocMessage getDepartInfoByOperNo(String operNo) throws Exception;
	/**
	 * UOC0078 	获取可选营业厅
	 * @param jsession_id
	 * @param region_id
	 * @param dept_no
	 * @param dept_name
	 * @return
	 * @throws Exception
	 */
	
	public UocMessage getShellingInfo(String jsession_id,String region_id, String dept_no,String dept_name) throws Exception;
}
