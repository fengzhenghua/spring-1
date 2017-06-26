package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderDistributionPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderDistributionServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderDistributionServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderDistributionVo;
@Service("InfoSaleOrderDistributionServDu")
public class InfoSaleOrderDistributionServDuImpl implements
		InfoSaleOrderDistributionServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderDistributionServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderDistributionServ infoSaleOrderDistributionServ;
	
	@Override
	public boolean deleteInfoSaleOrderDistributionBySaleOrderNo(
			InfoSaleOrderDistributionVo vo) throws Exception {
		logger.info("----------------deleteInfoSaleOrderDistributionBySaleOrderNo-----------------------");
		InfoSaleOrderDistributionPo infoSaleOrderDistribution =new InfoSaleOrderDistributionPo();
		BeanUtils.copyProperties(vo,infoSaleOrderDistribution);
		
		infoSaleOrderDistributionServ.deleteInfoSaleOrderDistributionBySaleOrderNo(infoSaleOrderDistribution);

		logger.info("----------------deleteInfoSaleOrderDistributionBySaleOrderNo---------end--------------");
		return true;
	}

	@Override
	public InfoSaleOrderDistributionVo getInfoSaleOrderDistributionBySaleOrderNo(
			InfoSaleOrderDistributionVo vo) throws Exception {
		InfoSaleOrderDistributionPo infoSaleOrderDistribution =new InfoSaleOrderDistributionPo();
		BeanUtils.copyProperties(vo,infoSaleOrderDistribution);
		InfoSaleOrderDistributionPo infoSaleOrderDistributionPoRes = infoSaleOrderDistributionServ.getInfoSaleOrderDistributionBySaleOrderNo(infoSaleOrderDistribution);
		if(infoSaleOrderDistributionPoRes != null){
			InfoSaleOrderDistributionVo InfoSaleOrderDistributionVoRes = new InfoSaleOrderDistributionVo();
			BeanUtils.copyProperties(infoSaleOrderDistributionPoRes, InfoSaleOrderDistributionVoRes);
			return InfoSaleOrderDistributionVoRes;
		}else{
			return null;
		}
		
	}

	@Override
	public List<InfoSaleOrderDistributionVo> queryInfoSaleOrderDistributionByPayOrderNo(
			InfoPayOrderVo vo) throws Exception {
		InfoPayOrderPo InfoPayOrder = new InfoPayOrderPo();
		BeanUtils.copyProperties(vo, InfoPayOrder);
		List<InfoSaleOrderDistributionVo> listVo= new ArrayList<InfoSaleOrderDistributionVo>();
		List<InfoSaleOrderDistributionPo> listPo = infoSaleOrderDistributionServ.queryInfoSaleOrderDistributionByPayOrderNo(InfoPayOrder);
		if(listPo != null && listPo.size()>0){
			for(InfoSaleOrderDistributionPo po : listPo){
				InfoSaleOrderDistributionVo ordVo = new InfoSaleOrderDistributionVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

}
