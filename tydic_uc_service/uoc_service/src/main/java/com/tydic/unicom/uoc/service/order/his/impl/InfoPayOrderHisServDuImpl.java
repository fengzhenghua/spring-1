package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoPayOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoPayOrderHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoPayOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoPayOrderHisVo;

@Service("InfoPayOrderHisServDu")
public class InfoPayOrderHisServDuImpl implements InfoPayOrderHisServDu {

	Logger logger = Logger.getLogger(InfoPayOrderHisServDuImpl.class);
	@Autowired
	private InfoPayOrderHisServ infoPayOrderHisServ;

	@Override
	public InfoPayOrderHisVo getInfoPayOrderHisByPayOrderNo(InfoPayOrderHisVo vo)
			throws Exception {
		logger.info("----------------queryInfoPayOrderHisByRelaOrderNo-----------------------");
		InfoPayOrderHisPo infoPayOrderHisPo =new InfoPayOrderHisPo();
		BeanUtils.copyProperties(vo,infoPayOrderHisPo);
		List<InfoPayOrderHisPo> po=infoPayOrderHisServ.queryInfoPayOrderHisByOrderNo(infoPayOrderHisPo);
		if(po ==null){
			return null;
		}
		BeanUtils.copyProperties(po.get(0),vo);
		
		logger.info("----------------queryInfoPayOrderHisByRelaOrderNo--------------end---------");
		return vo;
	}

	
}
