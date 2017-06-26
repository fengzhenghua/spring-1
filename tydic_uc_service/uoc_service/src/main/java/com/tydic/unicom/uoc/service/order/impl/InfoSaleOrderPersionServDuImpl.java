package com.tydic.unicom.uoc.service.order.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderPersionServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderPersionServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderPersionVo;
@Service("InfoSaleOrderPersionServDu")
public class InfoSaleOrderPersionServDuImpl implements
		InfoSaleOrderPersionServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderPersionServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderPersionServ infoSaleOrderPersionServ;
	
	@Override
	public boolean deleteInfoSaleOrderPersionBySaleOrderNo(
			InfoSaleOrderPersionVo vo) throws Exception {
		logger.info("----------------deleteInfoSaleOrderPersionBySaleOrderNo-----------------------");
		InfoSaleOrderPersionPo infoSaleOrderPersion =new InfoSaleOrderPersionPo();
		BeanUtils.copyProperties(vo,infoSaleOrderPersion);
		
		infoSaleOrderPersionServ.deleteInfoSaleOrderPersionBySaleOrderNo(infoSaleOrderPersion);

		logger.info("----------------deleteInfoSaleOrderPersionBySaleOrderNo---------end--------------");
		return true;
	}

	@Override
	public InfoSaleOrderPersionVo getInfoSaleOrderPersionBySaleOrderNo(
			InfoSaleOrderPersionVo vo) throws Exception {
		InfoSaleOrderPersionPo infoSaleOrderPersion =new InfoSaleOrderPersionPo();
		BeanUtils.copyProperties(vo,infoSaleOrderPersion);
		InfoSaleOrderPersionPo po = infoSaleOrderPersionServ.getInfoSaleOrderPersionBySaleOrderNo(infoSaleOrderPersion);
		InfoSaleOrderPersionVo ordVo = new InfoSaleOrderPersionVo();
		if(po != null){
			BeanUtils.copyProperties(po, ordVo);
			return ordVo;
		}else{
			return null;
		}
	}

}
