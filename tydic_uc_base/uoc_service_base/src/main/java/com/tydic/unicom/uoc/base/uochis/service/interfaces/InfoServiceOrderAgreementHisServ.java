package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderAgreementHisPo;

public interface InfoServiceOrderAgreementHisServ {
	/**
	 * 写服务订单协议表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoServiceOrderAgreementHisPo(InfoServiceOrderAgreementHisPo po)throws Exception;

	public List<InfoServiceOrderAgreementHisPo> queryInfoServiceOrderAgreementHisByOrderNo(InfoServiceOrderAgreementHisPo po)throws Exception;
}
