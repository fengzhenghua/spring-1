package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderFeeHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderFeeHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoPayOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderFeeHisVo;

@Service("InfoSaleOrderFeeHisServDu")
public class InfoSaleOrderFeeHisServDuImpl implements InfoSaleOrderFeeHisServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderFeeHisServDuImpl.class);
	@Autowired
	private InfoSaleOrderFeeHisServ infoSaleOrderFeeHisServ;
	
	@Override
	public List<InfoSaleOrderFeeHisVo> queryInfoSaleOrderFeeHisByPayOrder(
			InfoPayOrderHisVo vo) throws Exception {
//		InfoPayOrderHisPo infoPayOrderHisPo =new InfoPayOrderHisPo();
//		BeanUtils.copyProperties(vo, infoPayOrderHisPo);
//		List<InfoSaleOrderFeeHisVo> listVo = new ArrayList<InfoSaleOrderFeeHisVo>();
//		List<InfoSaleOrderFeeHisPo> listPo = infoSaleOrderFeeHisServ.queryInfoSaleOrderFeeHisBySaleOrderNo(null);
//		if(listPo != null && listPo.size() > 0){
//			for(InfoSaleOrderFeeHisPo po : listPo){
//				InfoSaleOrderFeeHisVo ordVo = new InfoSaleOrderFeeHisVo();
//				BeanUtils.copyProperties(po, ordVo);
//				listVo.add(ordVo);
//			}
//			return listVo;
//		}
		return null;
	}

	@Override
	public InfoSaleOrderFeeHisVo queryInfoSaleOrderFeeHisBySaleOrderNo(
			InfoSaleOrderFeeHisVo vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
