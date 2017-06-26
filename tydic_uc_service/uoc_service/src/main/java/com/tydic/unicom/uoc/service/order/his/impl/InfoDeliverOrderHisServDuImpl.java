package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoDeliverOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoDeliverOrderHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoDeliverOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoDeliverOrderHisVo;

@Service("InfoDeliverOrderHisServDu")
public class InfoDeliverOrderHisServDuImpl implements InfoDeliverOrderHisServDu {

	Logger logger = Logger.getLogger(InfoDeliverOrderHisServDuImpl.class);
	@Autowired
	private InfoDeliverOrderHisServ infoDeliverOrderHisServ;	

	@Override
	public List<InfoDeliverOrderHisVo> queryInfoDeliverOrderHisBySaleOrderNo(
			InfoDeliverOrderHisVo vo) throws Exception {
		InfoDeliverOrderHisPo infoDeliverOrderHisPo =new InfoDeliverOrderHisPo();
		BeanUtils.copyProperties(vo,infoDeliverOrderHisPo);
		List<InfoDeliverOrderHisPo> listPo = infoDeliverOrderHisServ.queryInfoDeliverOrderHisByOrderNo(infoDeliverOrderHisPo);
		if(listPo == null || listPo.size() == 0){
			return null;
		}
		List<InfoDeliverOrderHisVo> listVo = new ArrayList<InfoDeliverOrderHisVo>();
		for(InfoDeliverOrderHisPo po : listPo){
			InfoDeliverOrderHisVo voTemp  = new InfoDeliverOrderHisVo();
			BeanUtils.copyProperties(po, voTemp);
			listVo.add(voTemp);
		}
		if(listVo.size() == 0){
			return null;
		}
		return listVo;
	}

}
