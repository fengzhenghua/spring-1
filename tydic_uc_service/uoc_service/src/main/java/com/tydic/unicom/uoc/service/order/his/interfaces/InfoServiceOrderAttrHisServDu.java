package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderAttrHisVo;

public interface InfoServiceOrderAttrHisServDu {
	
	public List<InfoServiceOrderAttrHisVo> queryInfoServiceOrderAttrHisByOrderNo(InfoServiceOrderAttrHisVo vo) throws Exception;
	
}
