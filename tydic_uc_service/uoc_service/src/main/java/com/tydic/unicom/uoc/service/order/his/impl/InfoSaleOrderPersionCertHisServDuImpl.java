package com.tydic.unicom.uoc.service.order.his.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderPersionCertHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderPersionHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderPersionCertHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderPersionCertHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderPersionCertHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderPersionHisVo;
@Service("InfoSaleOrderPersionCertHisServDu")
public class InfoSaleOrderPersionCertHisServDuImpl implements
		InfoSaleOrderPersionCertHisServDu {

	
	Logger logger = Logger.getLogger(InfoSaleOrderPersionCertHisServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderPersionCertHisServ infoSaleOrderPersionCertHisServ;
	

	@Override
	public boolean createInfoSaleOrderPersionCertHis(
			InfoSaleOrderPersionCertHisVo vo) throws Exception {
		
		return false;
	}

	@Override
	public InfoSaleOrderPersionCertHisVo getInfoSaleOrderPersionCertHis(
			InfoSaleOrderPersionCertHisVo vo) throws Exception {
		InfoSaleOrderPersionCertHisPo infoSaleOrderPersionCertHisPo =new InfoSaleOrderPersionCertHisPo();
		BeanUtils.copyProperties(vo,infoSaleOrderPersionCertHisPo);
		InfoSaleOrderPersionCertHisPo po = infoSaleOrderPersionCertHisServ.getInfoSaleOrderPersionCertHis(infoSaleOrderPersionCertHisPo);
		InfoSaleOrderPersionCertHisVo ordVo = new InfoSaleOrderPersionCertHisVo();
		if(po != null){
			BeanUtils.copyProperties(po, ordVo);
			return ordVo;
		}else{
			return null;
		}
	}

}
