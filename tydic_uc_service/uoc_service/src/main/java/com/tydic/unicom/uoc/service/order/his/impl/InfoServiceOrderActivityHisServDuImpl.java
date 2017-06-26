package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderActivityHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderActivityHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderActivityHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderActivityHisVo;

@Service("InfoServiceOrderActivityHisServDu")
public class InfoServiceOrderActivityHisServDuImpl implements InfoServiceOrderActivityHisServDu{

	@Autowired
	private InfoServiceOrderActivityHisServ infoServiceOrderActivityHisServ;
	
	@Override
	public List<InfoServiceOrderActivityHisVo> queryInfoServiceOrderActivityHisByOrderNo(
			InfoServiceOrderActivityHisVo vo) throws Exception {
		InfoServiceOrderActivityHisPo InfoServiceOrderActivityHisPo = new InfoServiceOrderActivityHisPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderActivityHisPo);
		List<InfoServiceOrderActivityHisVo> listVo = new ArrayList<InfoServiceOrderActivityHisVo>();
		List<InfoServiceOrderActivityHisPo> listPo = infoServiceOrderActivityHisServ.queryInfoServiceOrderActivityHisByOrderNo(InfoServiceOrderActivityHisPo);  
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderActivityHisPo po : listPo){
				InfoServiceOrderActivityHisVo voTemp = new InfoServiceOrderActivityHisVo();
				BeanUtils.copyProperties(po, voTemp);
				listVo.add(voTemp);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
