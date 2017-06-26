package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoOrderCancelHisPo;

public interface InfoOrderCancelHisServ {

	public boolean create(InfoOrderCancelHisPo po) throws Exception;
	
	public List<InfoOrderCancelHisPo> queryInfoOrderCancelList(InfoOrderCancelHisPo po)
			throws Exception;
}
