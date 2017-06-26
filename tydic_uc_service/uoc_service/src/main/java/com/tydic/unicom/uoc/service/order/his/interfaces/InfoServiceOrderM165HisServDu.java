package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderM165HisVo;

public interface InfoServiceOrderM165HisServDu{

	
	public List<InfoServiceOrderM165HisVo> queryInfoServiceOrderM165HisByOrderNo(InfoServiceOrderM165HisVo vo)throws Exception;
}
