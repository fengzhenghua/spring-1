package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderCollectionHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderCollectionHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderCollectionHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderCollectionHisVo;

@Service("InfoServiceOrderCollectionHisServDu")
public class InfoServiceOrderCollectionHisServDuImpl implements InfoServiceOrderCollectionHisServDu{

	@Autowired
	private InfoServiceOrderCollectionHisServ infoServiceOrderCollectionHisServ;

	@Override
	public List<InfoServiceOrderCollectionHisVo> queryInfoServiceOrderCollectionHisByOrderNo(
			InfoServiceOrderCollectionHisVo vo) throws Exception {
		InfoServiceOrderCollectionHisPo infoServiceOrderCollectionHisPo = new InfoServiceOrderCollectionHisPo();
		BeanUtils.copyProperties(vo, infoServiceOrderCollectionHisPo);
		List<InfoServiceOrderCollectionHisVo> listVo = new ArrayList<InfoServiceOrderCollectionHisVo>();
		List<InfoServiceOrderCollectionHisPo> listPo = infoServiceOrderCollectionHisServ.queryInfoServiceOrderCollectionHisByOrderNo(infoServiceOrderCollectionHisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderCollectionHisPo po : listPo){
				InfoServiceOrderCollectionHisVo ordVo = new InfoServiceOrderCollectionHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
