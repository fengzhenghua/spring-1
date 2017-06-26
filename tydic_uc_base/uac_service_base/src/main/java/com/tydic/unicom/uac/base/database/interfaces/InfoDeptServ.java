package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.InfoDeptPo;

public interface InfoDeptServ {
	
	public InfoDeptPo queryInfoDeptByDeptNo(InfoDeptPo infoDept);
	
	public List<InfoDeptPo> queryInfoDeptByDeptNoAndDeptName(String currDeptNo,String departNo, String departName);
	
	public InfoDeptPo queryInfoDeptByOperNo(String operNo) throws Exception;
	/**
	 * 根据DeptType查询
	 * */
	public List<InfoDeptPo> queryInfoDeptByDeptType(InfoDeptPo infoDeptPo) throws Exception;
	
	public List<InfoDeptPo> queryInfoDeptByLocalNet(InfoDeptPo infoDeptPo) throws Exception;
}
