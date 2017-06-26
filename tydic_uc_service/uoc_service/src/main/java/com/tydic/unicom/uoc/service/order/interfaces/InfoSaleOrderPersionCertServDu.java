package com.tydic.unicom.uoc.service.order.interfaces;

import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderPersionCertVo;

public interface InfoSaleOrderPersionCertServDu {

	public InfoSaleOrderPersionCertVo getInfoSaleOrderPersionCertBySaleOrderNo(InfoSaleOrderPersionCertVo vo) throws Exception;
	public boolean deleteInfoSaleOrderPersionCertBySaleOrderNo(InfoSaleOrderPersionCertVo vo) throws Exception;
	public boolean createInfoSaleOrderPersionCert(InfoSaleOrderPersionCertVo vo)throws Exception;
	public boolean updateInfoSaleOrderPersionCert(InfoSaleOrderPersionCertVo vo)throws Exception;

}
