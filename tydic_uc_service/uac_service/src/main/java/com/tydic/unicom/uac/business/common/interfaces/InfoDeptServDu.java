package com.tydic.unicom.uac.business.common.interfaces;

import java.util.List;

import com.tydic.unicom.uac.business.common.vo.InfoDeptVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface InfoDeptServDu {

	public List<InfoDeptVo> queryInfoDeptByDeptNoAndDeptName(String currDeptNo,String departNo, String departName) throws Exception;
	
	public InfoDeptVo queryInfoDeptByOperNo(String operNo) throws Exception;
	
	/**获取可选营业厅*/
	public UocMessage getBusinessHallInfo(String jsessionId,String areaId,String departNo,String departName) throws Exception;

	public List<InfoDeptVo> queryInfoDepts(String jsession_id,String region_id,String depart_no,String depart_name,String query_type)throws Exception;
}
