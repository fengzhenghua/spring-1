package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderFixHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderFixHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderFixHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderFixHisVo;

@Service("InfoServiceOrderFixHisServDu")
public class InfoServiceOrderFixHisServDuImpl implements InfoServiceOrderFixHisServDu{

	@Autowired
	private InfoServiceOrderFixHisServ infoServiceOrderFixHisServ;

	@Override
	public List<InfoServiceOrderFixHisVo> queryInfoServiceOrderFixHisByOrderNo(
			InfoServiceOrderFixHisVo vo) throws Exception {
		InfoServiceOrderFixHisPo InfoServiceOrderFixHisPo = new InfoServiceOrderFixHisPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderFixHisPo);
		List<InfoServiceOrderFixHisVo> listVo = new ArrayList<InfoServiceOrderFixHisVo>();
		List<InfoServiceOrderFixHisPo> listPo = infoServiceOrderFixHisServ.queryInfoServiceOrderFixHisByOrderNo(InfoServiceOrderFixHisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderFixHisPo po : listPo){
				InfoServiceOrderFixHisVo ordVo = new InfoServiceOrderFixHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
