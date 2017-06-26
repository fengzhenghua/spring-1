package com.tydic.unicom.uoc.service.order.his.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderDistributionHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderDistributionHisServ;

import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderDistributionHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoPayOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderDistributionHisVo;

@Service("InfoSaleOrderDistributionHisServDu")
public class InfoSaleOrderDistributionHisServDuImpl implements
		InfoSaleOrderDistributionHisServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderDistributionHisServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderDistributionHisServ infoSaleOrderDistributionHisServ;

	@Override
	public InfoSaleOrderDistributionHisVo getInfoSaleOrderDistributionHisBySaleOrderNo(
			InfoSaleOrderDistributionHisVo vo) throws Exception {
		InfoSaleOrderDistributionHisPo InfoSaleOrderDistributionHis =new InfoSaleOrderDistributionHisPo();
		BeanUtils.copyProperties(vo,InfoSaleOrderDistributionHis);
		List<InfoSaleOrderDistributionHisPo> InfoSaleOrderDistributionHisPoRes = infoSaleOrderDistributionHisServ.queryInfoSaleOrderDistributionHisByOrderNo(InfoSaleOrderDistributionHis);
		if(InfoSaleOrderDistributionHisPoRes != null){
			InfoSaleOrderDistributionHisVo InfoSaleOrderDistributionHisVoRes = new InfoSaleOrderDistributionHisVo();
			BeanUtils.copyProperties(InfoSaleOrderDistributionHisPoRes.get(0), InfoSaleOrderDistributionHisVoRes);
			return InfoSaleOrderDistributionHisVoRes;
		}else{
			return null;
		}
		
	}

	@Override
	public List<InfoSaleOrderDistributionHisVo> queryInfoSaleOrderDistributionHisByPayOrderNo(
			InfoPayOrderHisVo vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
