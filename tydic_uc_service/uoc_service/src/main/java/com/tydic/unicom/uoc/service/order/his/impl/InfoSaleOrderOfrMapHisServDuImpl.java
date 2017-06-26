package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderOfrMapHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderOfrMapHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderOfrMapHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderOfrMapHisVo;

@Service("InfoSaleOrderOfrMapHisServDu")
public class InfoSaleOrderOfrMapHisServDuImpl implements InfoSaleOrderOfrMapHisServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderOfrMapHisServDuImpl.class);
	@Autowired
	private  InfoSaleOrderOfrMapHisServ infoSaleOrderOfrMapHisServ;
	
	@Override
	public List<InfoSaleOrderOfrMapHisVo> queryInfoSaleOrderOfrMapHisBySaleOrderNo(
			InfoSaleOrderOfrMapHisVo vo) throws Exception {
		logger.info("----------------queryInfoSaleOrderOfrMapHisBySaleOrderNo-----------------------");

		
		InfoSaleOrderOfrMapHisPo InfoSaleOrderOfrMapHisPo =new InfoSaleOrderOfrMapHisPo();
		BeanUtils.copyProperties(vo,InfoSaleOrderOfrMapHisPo);
		List<InfoSaleOrderOfrMapHisPo> list =infoSaleOrderOfrMapHisServ.queryInfoSaleOrderOfrMapHisBySaleOrderNo(InfoSaleOrderOfrMapHisPo);
		List<InfoSaleOrderOfrMapHisVo> listVo =new ArrayList<InfoSaleOrderOfrMapHisVo>();
		if(list !=null){
			for(InfoSaleOrderOfrMapHisPo po:list ){
				InfoSaleOrderOfrMapHisVo InfoSaleOrderOfrMapHisVo =new InfoSaleOrderOfrMapHisVo();
				BeanUtils.copyProperties(po,InfoSaleOrderOfrMapHisVo);
				listVo.add(InfoSaleOrderOfrMapHisVo);
			}
		}
		logger.info("----------------queryInfoSaleOrderOfrMapHisBySaleOrderNo-------------end----------");
		return listVo;
	}

}
