package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderM165HisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderM165HisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderM165HisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderM165HisVo;

@Service("InfoServiceOrderM165HisServDu")
public class InfoServiceOrderM165HisServDuImpl implements InfoServiceOrderM165HisServDu{

	@Autowired
	private InfoServiceOrderM165HisServ infoServiceOrderM165HisServ;

	@Override
	public List<InfoServiceOrderM165HisVo> queryInfoServiceOrderM165HisByOrderNo(
			InfoServiceOrderM165HisVo vo) throws Exception {
		InfoServiceOrderM165HisPo infoServiceOrderM165HisPo = new InfoServiceOrderM165HisPo();
		BeanUtils.copyProperties(vo, infoServiceOrderM165HisPo);
		List<InfoServiceOrderM165HisVo> listVo = new ArrayList<InfoServiceOrderM165HisVo>();
		List<InfoServiceOrderM165HisPo> listPo = infoServiceOrderM165HisServ.queryInfoServiceOrderM165HisByOrderNo(infoServiceOrderM165HisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderM165HisPo po : listPo){
				InfoServiceOrderM165HisVo ordVo = new InfoServiceOrderM165HisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
