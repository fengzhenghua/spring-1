package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderFeeHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOfrOrderFeeHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoOfrOrderFeeHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderFeeHisVo;

@Service("InfoOfrOrderFeeHisServDu")
public class InfoOfrOrderFeeHisServDuImpl implements InfoOfrOrderFeeHisServDu{

	@Autowired
	private InfoOfrOrderFeeHisServ infoOfrOrderFeeHisServ;
	
	@Override
	public List<InfoOfrOrderFeeHisVo> queryInfoOfrOrderFeeHisByOrderNo(
			InfoOfrOrderFeeHisVo vo) throws Exception {
		InfoOfrOrderFeeHisPo infoOfrOrderFeeHisPo = new InfoOfrOrderFeeHisPo();
		BeanUtils.copyProperties(vo, infoOfrOrderFeeHisPo);
		List<InfoOfrOrderFeeHisPo> listPo = infoOfrOrderFeeHisServ.queryInfoOfrOrderFeeHisByOrderNo(infoOfrOrderFeeHisPo);
		List<InfoOfrOrderFeeHisVo> listVo = new ArrayList<InfoOfrOrderFeeHisVo>();
		if(listPo != null && listPo.size()>0){
			for(InfoOfrOrderFeeHisPo po : listPo){
				InfoOfrOrderFeeHisVo ordVo = new InfoOfrOrderFeeHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

}
