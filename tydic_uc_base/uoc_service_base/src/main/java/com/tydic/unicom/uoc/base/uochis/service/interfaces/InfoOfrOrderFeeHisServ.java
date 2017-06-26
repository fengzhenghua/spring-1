package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderFeeHisPo;

public interface InfoOfrOrderFeeHisServ {
	/**
	 * 写入商品订单费用详情表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean createInfoOfrOrderFeeHisPo(InfoOfrOrderFeeHisPo po)throws Exception;

	public List<InfoOfrOrderFeeHisPo> queryInfoOfrOrderFeeHisByOrderNo(InfoOfrOrderFeeHisPo po)throws Exception;
}
