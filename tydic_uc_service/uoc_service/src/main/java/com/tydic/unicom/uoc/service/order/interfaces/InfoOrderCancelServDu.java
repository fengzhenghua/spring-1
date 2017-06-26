package com.tydic.unicom.uoc.service.order.interfaces;

import com.tydic.unicom.uoc.service.order.vo.InfoOrderCancelVo;

public interface InfoOrderCancelServDu {

	public boolean createInfoOrderCancel(InfoOrderCancelVo vo) throws Exception;

	public boolean updateInfoOrderCancel(InfoOrderCancelVo vo) throws Exception;

	public InfoOrderCancelVo queryInfoOrderCancel(InfoOrderCancelVo vo) throws Exception;
}
