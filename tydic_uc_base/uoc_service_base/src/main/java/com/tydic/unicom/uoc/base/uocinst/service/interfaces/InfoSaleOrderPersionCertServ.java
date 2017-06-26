package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionCertPo;

public interface InfoSaleOrderPersionCertServ {
	
	public InfoSaleOrderPersionCertPo getInfoSaleOrderPersionCertBySaleOrderNo(InfoSaleOrderPersionCertPo po) throws Exception;
	public boolean deleteInfoSaleOrderPersionCertBySaleOrderNo(InfoSaleOrderPersionCertPo po) throws Exception;
	public boolean createInfoSaleOrderPersionCert(InfoSaleOrderPersionCertPo po)throws Exception;
	public boolean updateInfoSaleOrderPersionCert(InfoSaleOrderPersionCertPo po)throws Exception;
}
