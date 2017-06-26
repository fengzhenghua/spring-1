package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderEditHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderEditHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderEditHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderEditHisVo;

@Service("InfoSaleOrderEditHisServDu")
public class InfoSaleOrderEditHisServDuImpl implements InfoSaleOrderEditHisServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderEditHisServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderEditHisServ infoSaleOrderEditHisServ;
	

	@Override
	public InfoSaleOrderEditHisVo getInfoSaleOrderEditBySaleOrderNo(
			InfoSaleOrderEditHisVo vo) throws Exception {
		logger.info("----------------getInfoSaleOrderEditHisBySaleOrderNo-----------------------");
		InfoSaleOrderEditHisPo InfoSaleOrderEditHisPo =new InfoSaleOrderEditHisPo();
		BeanUtils.copyProperties(vo,InfoSaleOrderEditHisPo);
		List<InfoSaleOrderEditHisPo> po =infoSaleOrderEditHisServ.queryInfoSaleOrderEditHisByOrderNo(InfoSaleOrderEditHisPo);
		if(po ==null){
			return null;
		}
		BeanUtils.copyProperties(po.get(0),vo);
		
		logger.info("----------------getInfoSaleOrderEditHisBySaleOrderNo--------------end---------");
		return vo;
	}

}
