package com.tydic.unicom.uoc.service.order.his.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderPersionHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderPersionHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderPersionHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderPersionHisVo;

@Service("InfoSaleOrderPersionHisServDu")
public class InfoSaleOrderPersionHisServDuImpl implements
		InfoSaleOrderPersionHisServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderPersionHisServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderPersionHisServ infoSaleOrderPersionHisServ;
	
	@Override
	public InfoSaleOrderPersionHisVo getInfoSaleOrderPersionHisBySaleOrderNo(
			InfoSaleOrderPersionHisVo vo) throws Exception {
		InfoSaleOrderPersionHisPo InfoSaleOrderPersionHis =new InfoSaleOrderPersionHisPo();
		BeanUtils.copyProperties(vo,InfoSaleOrderPersionHis);
		InfoSaleOrderPersionHisPo po = infoSaleOrderPersionHisServ.queryInfoSaleOrderPersionHisBySaleOrderNo(InfoSaleOrderPersionHis);
		InfoSaleOrderPersionHisVo ordVo = new InfoSaleOrderPersionHisVo();
		if(po != null){
			BeanUtils.copyProperties(po, ordVo);
			return ordVo;
		}else{
			return null;
		}
	}

}
