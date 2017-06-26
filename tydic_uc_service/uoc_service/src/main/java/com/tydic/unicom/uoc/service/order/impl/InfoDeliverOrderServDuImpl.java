package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoDeliverOrderPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoDeliverOrderServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoDeliverOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;
@Service("InfoDeliverOrderServDu")
public class InfoDeliverOrderServDuImpl implements InfoDeliverOrderServDu {

	Logger logger = Logger.getLogger(InfoDeliverOrderServDuImpl.class);
	@Autowired
	private InfoDeliverOrderServ infoDeliverOrderServ;
	
	@Override
	public InfoDeliverOrderVo getInfoDeliverOrderByPayDeliverOrderNo(
			InfoDeliverOrderVo vo) throws Exception {
		logger.info("----------------getInfoDeliverOrderByPayDeliverOrderNo-----------------------");
		InfoDeliverOrderPo infoDeliverOrderPo =new InfoDeliverOrderPo();
		BeanUtils.copyProperties(vo,infoDeliverOrderPo);
		infoDeliverOrderPo =infoDeliverOrderServ.getInfoDeliverOrderByPayDeliverOrderNo(infoDeliverOrderPo);
		if(infoDeliverOrderPo ==null){
			return null;
		}
		BeanUtils.copyProperties(infoDeliverOrderPo,vo);
		
		logger.info("----------------getInfoDeliverOrderByPayDeliverOrderNo--------------end---------");
		return vo;
	}

	@Override
	public boolean updateInfoDeliverOrder(InfoDeliverOrderVo vo)
			throws Exception {
		logger.info("----------------updateInfoDeliverOrder-----------------------");
		InfoDeliverOrderPo infoDeliverOrderPo =new InfoDeliverOrderPo();
		BeanUtils.copyProperties(vo,infoDeliverOrderPo);
		
		infoDeliverOrderServ.updateInfoDeliverOrder(infoDeliverOrderPo);

		logger.info("----------------updateInfoDeliverOrder---------end--------------");
		return true;
	}

	@Override
	public List<InfoDeliverOrderVo> queryInfoDeliverOrderBySaleOrderNo(
			InfoDeliverOrderVo vo) throws Exception {
		InfoDeliverOrderPo infoDeliverOrderPo =new InfoDeliverOrderPo();
		BeanUtils.copyProperties(vo,infoDeliverOrderPo);
		List<InfoDeliverOrderPo> listPo = infoDeliverOrderServ.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderPo);
		if(listPo==null || listPo.size() <= 0){
			return null;
		}
		List<InfoDeliverOrderVo> listVo = new ArrayList<InfoDeliverOrderVo>();
		for(InfoDeliverOrderPo po : listPo){
			InfoDeliverOrderVo voTemp  = new InfoDeliverOrderVo();
			BeanUtils.copyProperties(po, voTemp);
			listVo.add(voTemp);
		}
		if(listPo==null || listVo.size() <= 0){
			return null;
		}
		return listVo;
	}

	@Override
	public boolean create(InfoDeliverOrderVo infoDeliverOrderVo) throws Exception {
		InfoDeliverOrderPo infoDeliverOrderPo =new InfoDeliverOrderPo();
		BeanUtils.copyProperties(infoDeliverOrderVo, infoDeliverOrderPo);
		return infoDeliverOrderServ.create(infoDeliverOrderPo);
	}

}
