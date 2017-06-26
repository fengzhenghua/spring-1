package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderFeeHisPo;

public interface InfoServiceOrderFeeHisServ {
	/**
	 * 写服务订单费用详情表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoServiceOrderFeeHisPo(InfoServiceOrderFeeHisPo po)throws Exception;

	public List<InfoServiceOrderFeeHisPo> queryInfoServiceOrderFeeHisByOrderNo(InfoServiceOrderFeeHisPo po)throws Exception;
}
