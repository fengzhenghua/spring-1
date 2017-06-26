package com.tydic.unicom.uoc.service.order.his.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderHisVo;

@Service("InfoSaleOrderHisServDu")
public class InfoSaleOrderHisServDuImpl implements InfoSaleOrderHisServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderHisServDuImpl.class);
	@Autowired
	private InfoSaleOrderHisServ infoSaleOrderHisServ;
	@Override
	public InfoSaleOrderHisVo getInfoSaleOrderHisBySaleOrderNo(
			InfoSaleOrderHisVo vo) throws Exception {
		logger.info("----------------getInfoSaleOrderHisPoBySaleOrderNo-----------------------");
		InfoSaleOrderHisPo InfoSaleOrderHisPo =new InfoSaleOrderHisPo();
		BeanUtils.copyProperties(vo,InfoSaleOrderHisPo);
		InfoSaleOrderHisPo =infoSaleOrderHisServ.queryInfoSaleOrderHisBySaleOrderNo(InfoSaleOrderHisPo);
		if(InfoSaleOrderHisPo ==null){
			return null;
		}
		BeanUtils.copyProperties(InfoSaleOrderHisPo,vo);
		
		logger.info("----------------getInfoSaleOrderHisPoBySaleOrderNo--------------end---------");
		return vo;
	}


}
