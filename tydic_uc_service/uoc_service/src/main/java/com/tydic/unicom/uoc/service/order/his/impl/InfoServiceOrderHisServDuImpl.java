package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderHisVo;

@Service("InfoServiceOrderHisServDu")
public class InfoServiceOrderHisServDuImpl implements InfoServiceOrderHisServDu {

	Logger logger = Logger.getLogger(InfoServiceOrderHisServDuImpl.class);
	@Autowired
	private InfoServiceOrderHisServ infoServiceOrderHisServ;

	@Override
	public List<InfoServiceOrderHisVo> queryInfoServiceOrderHisByOrderNo(
			InfoServiceOrderHisVo vo) throws Exception {
		logger.info("----------------queryInfoServiceOrderHisByOfrOrderNo-----------------------");


		InfoServiceOrderHisPo infoServiceOrderHisPo =new InfoServiceOrderHisPo();
		BeanUtils.copyProperties(vo,infoServiceOrderHisPo);
		List<InfoServiceOrderHisPo> list =infoServiceOrderHisServ.queryInfoServiceOrderHisByOrderNo(infoServiceOrderHisPo);
		List<InfoServiceOrderHisVo> listVo =new ArrayList<InfoServiceOrderHisVo>();
		if(list != null && list.size()>0){
			for(InfoServiceOrderHisPo po:list ){
				InfoServiceOrderHisVo InfoServiceOrderHisVo =new InfoServiceOrderHisVo();
				BeanUtils.copyProperties(po,InfoServiceOrderHisVo);
				listVo.add(InfoServiceOrderHisVo);
			}

			logger.info("----------------queryInfoServiceOrderHisByOfrOrderNo-------------end----------");
			return listVo;
		}else{
			return null;
		}
	}

	@Override
	public List<InfoServiceOrderHisVo> queryInfoServiceOrderHisByVo(InfoServiceOrderHisVo vo)
			throws Exception {
		logger.info("----------------queryInfoServiceOrderHis-----------------------");

		InfoServiceOrderHisPo infoServiceOrderHisPo =new InfoServiceOrderHisPo();
		BeanUtils.copyProperties(vo,infoServiceOrderHisPo);
		List<InfoServiceOrderHisPo> list =infoServiceOrderHisServ.queryInfoServiceOrderHisByPo(infoServiceOrderHisPo);
		List<InfoServiceOrderHisVo> listVo =new ArrayList<InfoServiceOrderHisVo>();
		if(list!=null){
			for(InfoServiceOrderHisPo po:list ){
				InfoServiceOrderHisVo infoServiceOrderHisVo =new InfoServiceOrderHisVo();
				BeanUtils.copyProperties(po,infoServiceOrderHisVo);
				listVo.add(infoServiceOrderHisVo);
			}
		}
		logger.info("----------------queryInfoServiceOrderHis-------------end----------");
		return listVo;
	}
}
