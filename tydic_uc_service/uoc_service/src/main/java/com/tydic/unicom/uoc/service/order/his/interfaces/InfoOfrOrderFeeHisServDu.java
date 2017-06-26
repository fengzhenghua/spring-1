package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderFeeHisVo;

public interface InfoOfrOrderFeeHisServDu {

	public List<InfoOfrOrderFeeHisVo> queryInfoOfrOrderFeeHisByOrderNo(InfoOfrOrderFeeHisVo vo)throws Exception;
}
