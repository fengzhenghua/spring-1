package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOfrOrderHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoOfrOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderHisVo;

@Service("InfoOfrOrderHisServDu")
public class InfoOfrOrderHisServDuImpl implements InfoOfrOrderHisServDu {

	Logger logger = Logger.getLogger(InfoOfrOrderHisServDuImpl.class);
	@Autowired
	private InfoOfrOrderHisServ infoOfrOrderHisServ;
	@Override
	public List<InfoOfrOrderHisVo> queryInfoOfrOrderHisBySaleOrderNo(
			InfoOfrOrderHisVo vo) throws Exception {
		logger.info("----------------queryInfoOfrOrderBySaleOrderNo-----------------------");

		
		InfoOfrOrderHisPo infoOfrOrderHisPo =new InfoOfrOrderHisPo();
		BeanUtils.copyProperties(vo,infoOfrOrderHisPo);
		List<InfoOfrOrderHisPo> list =infoOfrOrderHisServ.queryInfoOfrOrderHisByOrderNo(infoOfrOrderHisPo);
		List<InfoOfrOrderHisVo> listVo =new ArrayList<InfoOfrOrderHisVo>();
		if(list!=null && list.size()>0){
			for(InfoOfrOrderHisPo po:list ){
				InfoOfrOrderHisVo infoOfrOrderHisVo =new InfoOfrOrderHisVo();
				BeanUtils.copyProperties(po,infoOfrOrderHisVo);
				listVo.add(infoOfrOrderHisVo);
			}
			logger.info("----------------queryInfoOfrOrderBySaleOrderNo-------------end----------");
			return listVo;
		}else{
			return null;
		}
	}
	@Override
	public List<InfoOfrOrderHisVo> queryInfoOfrOrderHisByOrderNo(
			InfoOfrOrderHisVo vo) throws Exception {
		logger.info("----------------queryInfoOfrOrderBySaleOrderNo-----------------------");

		
		InfoOfrOrderHisPo infoOfrOrderHisPo =new InfoOfrOrderHisPo();
		BeanUtils.copyProperties(vo,infoOfrOrderHisPo);
		List<InfoOfrOrderHisPo> list =infoOfrOrderHisServ.queryInfoOfrOrderHisByOrderNo(infoOfrOrderHisPo);
		List<InfoOfrOrderHisVo> listVo =new ArrayList<InfoOfrOrderHisVo>();
		if(list!=null && list.size()>0){
			for(InfoOfrOrderHisPo po:list ){
				InfoOfrOrderHisVo infoOfrOrderHisVo =new InfoOfrOrderHisVo();
				BeanUtils.copyProperties(po,infoOfrOrderHisVo);
				listVo.add(infoOfrOrderHisVo);
			}
			logger.info("----------------queryInfoOfrOrderBySaleOrderNo-------------end----------");
			return listVo;
		}else{
			return null;
		}
	}

}
