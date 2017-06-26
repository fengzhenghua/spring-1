package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderGuarantorHisPo;

public interface InfoServiceOrderGuarantorHisServ {
	/**
	 * 写服务订单担保人表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoServiceOrderGuarantorHisPo(InfoServiceOrderGuarantorHisPo po)throws Exception;

	public List<InfoServiceOrderGuarantorHisPo> queryInfoServiceOrderGuarantorHisByOrderNo(InfoServiceOrderGuarantorHisPo po)throws Exception;
}
