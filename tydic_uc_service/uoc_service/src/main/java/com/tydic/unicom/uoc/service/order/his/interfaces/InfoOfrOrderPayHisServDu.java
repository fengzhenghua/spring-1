package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderPayHisVo;

public interface InfoOfrOrderPayHisServDu {

	public List<InfoOfrOrderPayHisVo> queryInfoOfrOrderPayHisByOrderNo(InfoOfrOrderPayHisVo vo)throws Exception;
}
