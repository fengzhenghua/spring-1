package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderSimCardHisVo;

public interface InfoServiceOrderSimCardHisServDu {

	
	public List<InfoServiceOrderSimCardHisVo> queryInfoServiceOrderSimCardHisByOrderNo(InfoServiceOrderSimCardHisVo vo)throws Exception;
}
