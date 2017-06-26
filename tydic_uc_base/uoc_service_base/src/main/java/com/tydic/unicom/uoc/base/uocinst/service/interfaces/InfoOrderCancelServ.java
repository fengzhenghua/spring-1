package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoOrderCancelPo;

public interface InfoOrderCancelServ {

	public boolean updateInfoOrderCancel(InfoOrderCancelPo po) throws Exception;

	public InfoOrderCancelPo queryInfoOrderCancel(InfoOrderCancelPo po)throws Exception;
	
	public boolean create(InfoOrderCancelPo po) throws Exception;
	
	public List<InfoOrderCancelPo> queryInfoOrderCancelList(InfoOrderCancelPo po)throws Exception;
	
	public boolean deleteByOrderNo(InfoOrderCancelPo po) throws Exception;
}
