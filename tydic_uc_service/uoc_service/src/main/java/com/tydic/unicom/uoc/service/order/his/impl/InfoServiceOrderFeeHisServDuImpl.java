package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderFeeHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderFeeHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderFeeHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderFeeHisVo;

@Service("InfoServiceOrderFeeHisServDu")
public class InfoServiceOrderFeeHisServDuImpl implements InfoServiceOrderFeeHisServDu {

	Logger logger = Logger.getLogger(InfoServiceOrderFeeHisServDuImpl.class);
	@Autowired
	private InfoServiceOrderFeeHisServ infoServiceOrderFeeHisServ;
	
	@Override
	public List<InfoServiceOrderFeeHisVo> queryInfoServiceOrderFeeHisByOrderNo(
			InfoServiceOrderFeeHisVo vo) throws Exception {
		InfoServiceOrderFeeHisPo infoServiceOrderFeeHisPo =new InfoServiceOrderFeeHisPo();
		BeanUtils.copyProperties(vo,infoServiceOrderFeeHisPo);
		List<InfoServiceOrderFeeHisVo> listVo =new ArrayList<InfoServiceOrderFeeHisVo>();
		List<InfoServiceOrderFeeHisPo> listPo = infoServiceOrderFeeHisServ.queryInfoServiceOrderFeeHisByOrderNo(infoServiceOrderFeeHisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderFeeHisPo po : listPo){
				InfoServiceOrderFeeHisVo ordVo = new InfoServiceOrderFeeHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
		
	}

}
