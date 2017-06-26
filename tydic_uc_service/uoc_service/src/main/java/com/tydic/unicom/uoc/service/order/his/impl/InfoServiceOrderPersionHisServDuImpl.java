package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderPersionHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderPersionHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderPersionHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderPersionHisVo;

@Service("InfoServiceOrderPersionHisServDu")
public class InfoServiceOrderPersionHisServDuImpl implements InfoServiceOrderPersionHisServDu{

	@Autowired
	private InfoServiceOrderPersionHisServ infoServiceOrderPersionHisServ;

	@Override
	public List<InfoServiceOrderPersionHisVo> queryInfoServiceOrderPersionHisByOrderNo(
			InfoServiceOrderPersionHisVo vo) throws Exception {
		InfoServiceOrderPersionHisPo InfoServiceOrderPersionHisPo = new InfoServiceOrderPersionHisPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderPersionHisPo);
		List<InfoServiceOrderPersionHisVo> listVo = new ArrayList<InfoServiceOrderPersionHisVo>();
		InfoServiceOrderPersionHisPo po = infoServiceOrderPersionHisServ.queryInfoServiceOrderPersionHisByOrderNo(InfoServiceOrderPersionHisPo);
		if(po != null ){
			InfoServiceOrderPersionHisVo ordVo = new InfoServiceOrderPersionHisVo();
			BeanUtils.copyProperties(po, ordVo);
			listVo.add(ordVo);
			return listVo;
		}else{
			return null;
		}
	}
}
