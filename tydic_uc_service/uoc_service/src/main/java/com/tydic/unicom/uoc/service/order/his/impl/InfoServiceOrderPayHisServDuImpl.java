package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderPayHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderPayHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderPayHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderPayHisVo;

@Service("InfoServiceOrderPayHisServDu")
public class InfoServiceOrderPayHisServDuImpl implements InfoServiceOrderPayHisServDu{

	@Autowired
	private InfoServiceOrderPayHisServ infoServiceOrderPayHisServ;

	@Override
	public List<InfoServiceOrderPayHisVo> queryInfoServiceOrderPayHisByOrderNo(
			InfoServiceOrderPayHisVo vo) throws Exception {
		InfoServiceOrderPayHisPo InfoServiceOrderPayHisPo = new InfoServiceOrderPayHisPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderPayHisPo);
		List<InfoServiceOrderPayHisVo> listVo = new ArrayList<InfoServiceOrderPayHisVo>();
		List<InfoServiceOrderPayHisPo> listPo = infoServiceOrderPayHisServ.queryInfoServiceOrderPayHisByOrderNo(InfoServiceOrderPayHisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderPayHisPo po : listPo){
				InfoServiceOrderPayHisVo ordVo = new InfoServiceOrderPayHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
