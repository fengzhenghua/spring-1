package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderFeeHisVo;

public interface InfoServiceOrderFeeHisServDu {

	
	public List<InfoServiceOrderFeeHisVo> queryInfoServiceOrderFeeHisByOrderNo(InfoServiceOrderFeeHisVo vo) throws Exception;
	
	
	
}
