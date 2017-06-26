package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderPayHisVo;

public interface InfoServiceOrderPayHisServDu {

	
	public List<InfoServiceOrderPayHisVo> queryInfoServiceOrderPayHisByOrderNo(InfoServiceOrderPayHisVo vo)throws Exception;
}
