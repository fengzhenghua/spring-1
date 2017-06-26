package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderPersionHisVo;

public interface InfoServiceOrderPersionHisServDu {

	
	public List<InfoServiceOrderPersionHisVo> queryInfoServiceOrderPersionHisByOrderNo(InfoServiceOrderPersionHisVo vo)throws Exception;
}
