package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderGuarantorHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderGuarantorHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderGuarantorHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderGuarantorHisVo;

@Service("InfoServiceOrderGuarantorHisServDu")
public class InfoServiceOrderGuarantorHisServDuImpl implements InfoServiceOrderGuarantorHisServDu{

	@Autowired
	private InfoServiceOrderGuarantorHisServ infoServiceOrderGuarantorHisServ;

	@Override
	public List<InfoServiceOrderGuarantorHisVo> queryInfoServiceOrderGuarantorHisByOrderNo(
			InfoServiceOrderGuarantorHisVo vo) throws Exception {
		InfoServiceOrderGuarantorHisPo InfoServiceOrderGuarantorHisPo = new InfoServiceOrderGuarantorHisPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderGuarantorHisPo);
		List<InfoServiceOrderGuarantorHisVo> listVo = new ArrayList<InfoServiceOrderGuarantorHisVo>();
		List<InfoServiceOrderGuarantorHisPo> listPo = infoServiceOrderGuarantorHisServ.queryInfoServiceOrderGuarantorHisByOrderNo(InfoServiceOrderGuarantorHisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderGuarantorHisPo po : listPo){
				InfoServiceOrderGuarantorHisVo ordVo = new InfoServiceOrderGuarantorHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
