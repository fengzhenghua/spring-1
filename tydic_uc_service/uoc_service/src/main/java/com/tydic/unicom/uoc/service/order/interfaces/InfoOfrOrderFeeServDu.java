package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderFeeVo;

public interface InfoOfrOrderFeeServDu {

	public List<InfoOfrOrderFeeVo> queryInfoOfrOrderFeeByOrderNo(InfoOfrOrderFeeVo vo)throws Exception;
}
