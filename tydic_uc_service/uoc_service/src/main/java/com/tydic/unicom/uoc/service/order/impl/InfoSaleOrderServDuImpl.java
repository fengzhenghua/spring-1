package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
@Service("InfoSaleOrderServDu")
public class InfoSaleOrderServDuImpl implements InfoSaleOrderServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderServDuImpl.class);
	@Autowired
	private InfoSaleOrderServ infoSaleOrderServ;

	@Override
	public InfoSaleOrderVo getInfoSaleOrderPoBySaleOrderNo(InfoSaleOrderVo vo)
			throws Exception {
		logger.info("----------------getInfoSaleOrderPoBySaleOrderNo-----------------------");
		InfoSaleOrderPo infoSaleOrderPo =new InfoSaleOrderPo();
		BeanUtils.copyProperties(vo,infoSaleOrderPo);
		infoSaleOrderPo =infoSaleOrderServ.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderPo);
		if(infoSaleOrderPo ==null){
			return null;
		}
		BeanUtils.copyProperties(infoSaleOrderPo,vo);

		logger.info("----------------getInfoSaleOrderPoBySaleOrderNo--------------end---------");
		return vo;
	}

	@Override
	public boolean createInfoSaleOrder(InfoSaleOrderVo vo) throws Exception {
		logger.info("----------------createInfoSaleOrder-----------------------");
		InfoSaleOrderPo infoSaleOrderPo =new InfoSaleOrderPo();
		BeanUtils.copyProperties(vo,infoSaleOrderPo);

		infoSaleOrderServ.createInfoSaleOrderPo(infoSaleOrderPo);

		logger.info("----------------createInfoSaleOrder---------end--------------");
		return true;
	}

	@Override
	public boolean updateInfoSaleOrder(InfoSaleOrderVo vo) throws Exception {
		logger.info("----------------updateInfoSaleOrder-----------------------");
		InfoSaleOrderPo infoSaleOrderPo =new InfoSaleOrderPo();
		BeanUtils.copyProperties(vo,infoSaleOrderPo);

		infoSaleOrderServ.updateInfoSaleOrderPo(infoSaleOrderPo);

		logger.info("----------------updateInfoSaleOrder---------end--------------");
		return true;
	}

	@Override
	public List<InfoSaleOrderVo> queryInfoSaleOrderByOrderState(
			InfoSaleOrderVo vo) throws Exception {
		logger.info("----------------queryInfoSaleOrderByOrderState-----------------------");
		InfoSaleOrderPo infoSaleOrderPo =new InfoSaleOrderPo();
		List<InfoSaleOrderVo> list =new ArrayList<InfoSaleOrderVo>();
		BeanUtils.copyProperties(vo,infoSaleOrderPo);

		List<InfoSaleOrderPo> listPo =infoSaleOrderServ.queryInfoSaleOrderByOrderState(infoSaleOrderPo);

		if(listPo !=null && listPo.size()>0){
			for(InfoSaleOrderPo po :listPo){
				InfoSaleOrderVo infoSaleOrderVo =new InfoSaleOrderVo();
				BeanUtils.copyProperties(po,infoSaleOrderVo);
				list.add(infoSaleOrderVo);
			}

		}

		logger.info("----------------queryInfoSaleOrderByOrderState---------end--------------");
		return list;
	}

	@Override
	public InfoSaleOrderVo queryInfoSaleOrder(InfoSaleOrderVo vo) throws Exception {
		logger.info("----------------getInfoSaleOrder-----------------------");
		InfoSaleOrderPo infoSaleOrderPo = new InfoSaleOrderPo();
		BeanUtils.copyProperties(vo, infoSaleOrderPo);
		infoSaleOrderPo = infoSaleOrderServ.queryInfoSaleOrderPo(infoSaleOrderPo);
		if (infoSaleOrderPo == null) {
			return null;
		}
		BeanUtils.copyProperties(infoSaleOrderPo, vo);

		logger.info("----------------getInfoSaleOrder--------------end---------");
		return vo;
	}

}
