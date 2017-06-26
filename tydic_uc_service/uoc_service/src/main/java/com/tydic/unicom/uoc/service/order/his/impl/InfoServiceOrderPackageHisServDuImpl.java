package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderPackageHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderPackageHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderPackageHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderPackageHisVo;

@Service("InfoServiceOrderPackageHisServDu")
public class InfoServiceOrderPackageHisServDuImpl implements InfoServiceOrderPackageHisServDu{

	@Autowired
	private InfoServiceOrderPackageHisServ infoServiceOrderPackageHisServ;

	@Override
	public List<InfoServiceOrderPackageHisVo> queryInfoServiceOrderPackageHisByOrderNo(
			InfoServiceOrderPackageHisVo vo) throws Exception {
		InfoServiceOrderPackageHisPo InfoServiceOrderPackageHisPo = new InfoServiceOrderPackageHisPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderPackageHisPo);
		List<InfoServiceOrderPackageHisVo> listVo = new ArrayList<InfoServiceOrderPackageHisVo>();
		List<InfoServiceOrderPackageHisPo> listPo = infoServiceOrderPackageHisServ.queryInfoServiceOrderPackageHisByOrderNo(InfoServiceOrderPackageHisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderPackageHisPo po : listPo){
				InfoServiceOrderPackageHisVo ordVo = new InfoServiceOrderPackageHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
