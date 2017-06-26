package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderSimCardHisPo;

public interface InfoServiceOrderSimCardHisServ {
	
	/**
	 * 写服务订单SIM卡信息表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean createInfoServiceOrderSimCardHisPo(InfoServiceOrderSimCardHisPo po)throws Exception;

	public List<InfoServiceOrderSimCardHisPo> queryInfoServiceOrderSimCardHisByOrderNo(InfoServiceOrderSimCardHisPo po)throws Exception;

}
