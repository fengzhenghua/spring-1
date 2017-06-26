package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderServMapHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderServMapHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderServMapHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderServMapHisVo;

@Service("InfoSaleOrderServMapHisServDu")
public class InfoSaleOrderServMapHisServDuImpl implements
		InfoSaleOrderServMapHisServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderServMapHisServDuImpl.class);
	@Autowired
	private InfoSaleOrderServMapHisServ infoSaleOrderServMapHisServ;
	
	@Override
	public List<InfoSaleOrderServMapHisVo> queryInfoSaleOrderServMapHisBySaleOrderNo(
			InfoSaleOrderServMapHisVo vo) throws Exception {
		logger.info("----------------queryInfoSaleOrderServMapHisBySaleOrderNo-----------------------");

		
		InfoSaleOrderServMapHisPo InfoSaleOrderServMapHisPo =new InfoSaleOrderServMapHisPo();
		BeanUtils.copyProperties(vo,InfoSaleOrderServMapHisPo);
		List<InfoSaleOrderServMapHisPo> list =infoSaleOrderServMapHisServ.queryInfoSaleOrderServMapHisBySaleOrderNo(InfoSaleOrderServMapHisPo);
		List<InfoSaleOrderServMapHisVo> listVo =new ArrayList<InfoSaleOrderServMapHisVo>();
		if(list !=null){
			for(InfoSaleOrderServMapHisPo po:list ){
				InfoSaleOrderServMapHisVo InfoSaleOrderServMapHisVo =new InfoSaleOrderServMapHisVo();
				BeanUtils.copyProperties(po,InfoSaleOrderServMapHisVo);
				listVo.add(InfoSaleOrderServMapHisVo);
			}
		}
		logger.info("----------------queryInfoSaleOrderServMapHisBySaleOrderNo-------------end----------");
		return listVo;
	}

	

}
