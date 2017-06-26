package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderPayVo;

public interface InfoOfrOrderPayServDu {

	public List<InfoOfrOrderPayVo> queryInfoOfrOrderPayByOrderNo(InfoOfrOrderPayVo vo)throws Exception;
}
