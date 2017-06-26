package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderPayHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOfrOrderPayHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoOfrOrderPayHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderPayHisVo;

@Service("InfoOfrOrderPayHisServDu")
public class InfoOfrOrderPayHisServDuImpl implements InfoOfrOrderPayHisServDu{

	@Autowired
	private InfoOfrOrderPayHisServ infoOfrOrderPayHisServ;
	@Override
	public List<InfoOfrOrderPayHisVo> queryInfoOfrOrderPayHisByOrderNo(
			InfoOfrOrderPayHisVo vo) throws Exception {
		InfoOfrOrderPayHisPo infoOfrOrderPayHis = new InfoOfrOrderPayHisPo();
		BeanUtils.copyProperties(vo, infoOfrOrderPayHis);
		List<InfoOfrOrderPayHisVo> listVo = new ArrayList<InfoOfrOrderPayHisVo>();
		List<InfoOfrOrderPayHisPo> listPo = infoOfrOrderPayHisServ.queryInfoOfrOrderPayHisByOrderNo(infoOfrOrderPayHis);
		if(listPo != null && listPo.size()>0){
			for(InfoOfrOrderPayHisPo po : listPo){
				InfoOfrOrderPayHisVo ordVo = new InfoOfrOrderPayHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
		
	}

}
