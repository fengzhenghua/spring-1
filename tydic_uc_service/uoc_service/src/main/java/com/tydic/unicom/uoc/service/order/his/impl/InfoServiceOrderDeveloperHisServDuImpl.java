package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderDeveloperHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderDeveloperHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderDeveloperHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderDeveloperHisVo;

@Service("InfoServiceOrderDeveloperHisServDu")
public class InfoServiceOrderDeveloperHisServDuImpl implements InfoServiceOrderDeveloperHisServDu{

	@Autowired
	private InfoServiceOrderDeveloperHisServ infoServiceOrderDeveloperHisServ;

	@Override
	public List<InfoServiceOrderDeveloperHisVo> queryInfoServiceOrderDeveloperHisByOrderNo(
			InfoServiceOrderDeveloperHisVo vo) throws Exception {
		InfoServiceOrderDeveloperHisPo InfoServiceOrderDeveloperHisPo = new InfoServiceOrderDeveloperHisPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderDeveloperHisPo);
		List<InfoServiceOrderDeveloperHisVo> listVo = new ArrayList<InfoServiceOrderDeveloperHisVo>();
		List<InfoServiceOrderDeveloperHisPo> listPo = infoServiceOrderDeveloperHisServ.queyInfoServiceOrderDeveloperHisByOrderNo(InfoServiceOrderDeveloperHisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderDeveloperHisPo po : listPo){
				InfoServiceOrderDeveloperHisVo ordVo = new InfoServiceOrderDeveloperHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
