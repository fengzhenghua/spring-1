package com.tydic.unicom.uoc.service.order.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionCertPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderPersionCertServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderPersionCertServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderPersionCertVo;
@Service("InfoSaleOrderPersionCertServDu")
public class InfoSaleOrderPersionCertServDuImpl implements
		InfoSaleOrderPersionCertServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderPersionCertServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderPersionCertServ infoSaleOrderPersionCertServ;
	
	@Override
	public InfoSaleOrderPersionCertVo getInfoSaleOrderPersionCertBySaleOrderNo(
			InfoSaleOrderPersionCertVo vo) throws Exception {
		InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPo =new InfoSaleOrderPersionCertPo();
		BeanUtils.copyProperties(vo,infoSaleOrderPersionCertPo);
		InfoSaleOrderPersionCertPo po = infoSaleOrderPersionCertServ.getInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCertPo);
		InfoSaleOrderPersionCertVo ordVo = new InfoSaleOrderPersionCertVo();
		if(po != null){
			BeanUtils.copyProperties(po, ordVo);
			return ordVo;
		}else{
			return null;
		}
	}

	@Override
	public boolean deleteInfoSaleOrderPersionCertBySaleOrderNo(
			InfoSaleOrderPersionCertVo vo) throws Exception {
		logger.info("----------------deleteInfoSaleOrderPersionCertBySaleOrderNo-----------------------");
		InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPo =new InfoSaleOrderPersionCertPo();
		BeanUtils.copyProperties(vo,infoSaleOrderPersionCertPo);
		
		infoSaleOrderPersionCertServ.deleteInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCertPo);

		logger.info("----------------deleteInfoSaleOrderPersionCertBySaleOrderNo---------end--------------");
		return true;
	}

	@Override
	public boolean createInfoSaleOrderPersionCert(InfoSaleOrderPersionCertVo vo)
			throws Exception {
		logger.info("----------------createInfoSaleOrderPersionCert-----------------------");
		InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPo =new InfoSaleOrderPersionCertPo();
		BeanUtils.copyProperties(vo,infoSaleOrderPersionCertPo);
		
		infoSaleOrderPersionCertServ.createInfoSaleOrderPersionCert(infoSaleOrderPersionCertPo);

		logger.info("----------------createInfoSaleOrderPersionCert---------end--------------");
		return true;
	}

	@Override
	public boolean updateInfoSaleOrderPersionCert(InfoSaleOrderPersionCertVo vo)
			throws Exception {
		
		logger.info("----------------updateInfoSaleOrderPersionCert-----------------------");
		InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPo =new InfoSaleOrderPersionCertPo();
		BeanUtils.copyProperties(vo,infoSaleOrderPersionCertPo);
		
		infoSaleOrderPersionCertServ.updateInfoSaleOrderPersionCert(infoSaleOrderPersionCertPo);

		logger.info("----------------updateInfoSaleOrderPersionCert---------end--------------");
		return true;
	}
}
