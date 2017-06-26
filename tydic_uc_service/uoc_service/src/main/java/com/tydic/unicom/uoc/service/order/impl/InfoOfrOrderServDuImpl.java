package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOfrOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderVo;
@Service("InfoOfrOrderServDu")
public class InfoOfrOrderServDuImpl implements InfoOfrOrderServDu {

	Logger logger = Logger.getLogger(InfoOfrOrderServDuImpl.class);
	@Autowired
	private InfoOfrOrderServ infoOfrOrderServ;
	
	@Override
	public boolean createInfoOfrOrder(InfoOfrOrderVo vo) throws Exception {
		logger.info("----------------createInfoOfrOrder-----------------------");
		InfoOfrOrderPo infoOfrOrderPo =new InfoOfrOrderPo();
		BeanUtils.copyProperties(vo,infoOfrOrderPo);
		
		infoOfrOrderServ.createInfoOfrOrder(infoOfrOrderPo);

		logger.info("----------------createInfoOfrOrder---------end--------------");
		return true;
	}

	@Override
	public InfoOfrOrderVo getInfoOfrOrderByOfrOrderNo(InfoOfrOrderVo vo)
			throws Exception {
		logger.info("----------------getInfoOfrOrderByOfrOrderNo-----------------------");
		InfoOfrOrderPo infoOfrOrderPo =new InfoOfrOrderPo();
		BeanUtils.copyProperties(vo,infoOfrOrderPo);
		infoOfrOrderPo =infoOfrOrderServ.getInfoOfrOrderByOfrOrderNo(infoOfrOrderPo);
		if(infoOfrOrderPo ==null){
			return null;
		}
		BeanUtils.copyProperties(infoOfrOrderPo,vo);
		
		logger.info("----------------getInfoOfrOrderByOfrOrderNo--------------end---------");
		return vo;
	}

	@Override
	public boolean updateInfoOfrOrder(InfoOfrOrderVo vo) throws Exception {
		logger.info("----------------updateInfoOfrOrder-----------------------");
		InfoOfrOrderPo infoOfrOrderPo =new InfoOfrOrderPo();
		BeanUtils.copyProperties(vo,infoOfrOrderPo);
		
		infoOfrOrderServ.updateInfoOfrOrder(infoOfrOrderPo);

		logger.info("----------------updateInfoOfrOrder---------end--------------");
		return true;
	}

	@Override
	public List<InfoOfrOrderVo> queryInfoOfrOrderBySaleOrderNo(InfoOfrOrderVo vo)
			throws Exception {
		logger.info("----------------queryInfoOfrOrderBySaleOrderNo-----------------------");

		
		InfoOfrOrderPo infoOfrOrderPo =new InfoOfrOrderPo();
		BeanUtils.copyProperties(vo,infoOfrOrderPo);
		List<InfoOfrOrderPo> list =infoOfrOrderServ.queryInfoOfrOrderBySaleOrderNo(infoOfrOrderPo);
		List<InfoOfrOrderVo> listVo =new ArrayList<InfoOfrOrderVo>();
		if(list!=null && list.size()>0){
			for(InfoOfrOrderPo po:list ){
				InfoOfrOrderVo infoOfrOrderVo =new InfoOfrOrderVo();
				BeanUtils.copyProperties(po,infoOfrOrderVo);
				listVo.add(infoOfrOrderVo);
			}
			logger.info("----------------queryInfoOfrOrderBySaleOrderNo-------------end----------");
			return listVo;
		}else{
			return null;
		}
	}

}
