package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderDeveloperHisVo;

public interface InfoServiceOrderDeveloperHisServDu {
	
	public List<InfoServiceOrderDeveloperHisVo> queryInfoServiceOrderDeveloperHisByOrderNo(InfoServiceOrderDeveloperHisVo vo)throws Exception;
}
