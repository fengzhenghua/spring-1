package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderGoodNbrHisPo;

public interface InfoServiceOrderGoodNbrHisServ {
	/**
	 * 写服务订单靓号信息表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoServiceOrderGoodNbrHisPo(InfoServiceOrderGoodNbrHisPo po)throws Exception;

	public List<InfoServiceOrderGoodNbrHisPo> queryInfoServiceOrderGoodNbrHisByOrderNo(InfoServiceOrderGoodNbrHisPo po)throws Exception;
}
