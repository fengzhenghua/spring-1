package com.tydic.unicom.uoc.service.order.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoPayOrderServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoPayOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
@Service("InfoPayOrderServDu")
public class InfoPayOrderServDuImpl implements InfoPayOrderServDu {

	Logger logger = Logger.getLogger(InfoPayOrderServDuImpl.class);
	@Autowired
	private InfoPayOrderServ infoPayOrderServ;
	
	@Override
	public InfoPayOrderVo getInfoPayOrderByPayOrderNo(InfoPayOrderVo vo)
			throws Exception {
		logger.info("----------------getInfoPayOrderByPayOrderNo-----------------------");
		InfoPayOrderPo infoPayOrderPo =new InfoPayOrderPo();
		BeanUtils.copyProperties(vo,infoPayOrderPo);
		infoPayOrderPo =infoPayOrderServ.getInfoPayOrderByPayOrderNo(infoPayOrderPo);
		if(infoPayOrderPo ==null){
			return null;
		}
		BeanUtils.copyProperties(infoPayOrderPo,vo);
		
		logger.info("----------------getInfoPayOrderByPayOrderNo--------------end---------");
		return vo;
	}

	@Override
	public boolean updateInfoPayOrder(InfoPayOrderVo vo) throws Exception {
		logger.info("----------------updateInfoPayOrder-----------------------");
		InfoPayOrderPo infoPayOrderPo =new InfoPayOrderPo();
		BeanUtils.copyProperties(vo,infoPayOrderPo);
		
		infoPayOrderServ.updateInfoPayOrder(infoPayOrderPo);

		logger.info("----------------updateInfoPayOrder---------end--------------");
		return true;
	}

	@Override
	public boolean createInfoPayOrder(InfoPayOrderVo vo) throws Exception {
		logger.info("----------------createInfoPayOrder-----------------------");
		InfoPayOrderPo infoPayOrderPo =new InfoPayOrderPo();
		BeanUtils.copyProperties(vo,infoPayOrderPo);
		
		infoPayOrderServ.createInfoPayOrder(infoPayOrderPo);

		logger.info("----------------createInfoPayOrder---------end--------------");
		return true;
	}

	@Override
	public InfoPayOrderVo queryInfoPayOrderByRelaOrderNo(InfoPayOrderVo vo)
			throws Exception {
		logger.info("----------------queryInfoPayOrderByRelaOrderNo-----------------------");
		InfoPayOrderPo infoPayOrderPo =new InfoPayOrderPo();
		BeanUtils.copyProperties(vo,infoPayOrderPo);
		infoPayOrderPo =infoPayOrderServ.queryInfoPayOrderByRelaOrderNo(infoPayOrderPo);
		if(infoPayOrderPo ==null){
			return null;
		}
		BeanUtils.copyProperties(infoPayOrderPo,vo);
		
		logger.info("----------------queryInfoPayOrderByRelaOrderNo--------------end---------");
		return vo;
	}

//	@Override
//	public List<InfoPayOrderVo> queryInfoPayOrderByRelaOrderNo(
//			InfoPayOrderVo vo) throws Exception {
//		InfoPayOrderPo infoPayOrderPo =new InfoPayOrderPo();
//		BeanUtils.copyProperties(vo,infoPayOrderPo);
//		List<InfoPayOrderPo> listPo = infoPayOrderServ.queryInfoPayOrderByRelaOrderNo(infoPayOrderPo);
//		if(listPo.size() == 0){
//			return null;
//		}
//		List<InfoPayOrderVo> listVo = new ArrayList<InfoPayOrderVo>();
//		for(InfoPayOrderPo po : listPo){
//			InfoPayOrderVo voTemp = new InfoPayOrderVo();
//			BeanUtils.copyProperties(po, voTemp);
//			listVo.add(voTemp);
//		}
//		if(listVo.size() == 0){
//			return null;
//		}
//		return listVo;
//	}

}
