package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
@Service("InfoServiceOrderServDu")
public class InfoServiceOrderServDuImpl implements InfoServiceOrderServDu {

	Logger logger = Logger.getLogger(InfoServiceOrderServDuImpl.class);
	@Autowired
	private InfoServiceOrderServ infoServiceOrderServ;

	@Override
	public InfoServiceOrderVo getInfoServiceOrderByServOrderNo(
			InfoServiceOrderVo vo) throws Exception {
		logger.info("----------------getInfoServiceOrderByServOrderNo-----------------------");

		InfoServiceOrderPo infoServiceOrderPo =new InfoServiceOrderPo();
		BeanUtils.copyProperties(vo,infoServiceOrderPo);
		infoServiceOrderPo =infoServiceOrderServ.getInfoServiceOrderByServOrderNo(infoServiceOrderPo);
		if(infoServiceOrderPo ==null){
			return null;
		}

		BeanUtils.copyProperties(infoServiceOrderPo,vo);

		logger.info("----------------getInfoServiceOrderByServOrderNo--------------end---------");
		return vo;
	}

	@Override
	public List<InfoServiceOrderVo> queryInfoServiceOrderByOrderNo(
			InfoServiceOrderVo vo) throws Exception {
		logger.info("----------------queryInfoServiceOrderByOfrOrderNo-----------------------");


		InfoServiceOrderPo infoServiceOrderPo =new InfoServiceOrderPo();
		BeanUtils.copyProperties(vo,infoServiceOrderPo);
		List<InfoServiceOrderPo> list =infoServiceOrderServ.queryInfoServiceOrderByOrderNo(infoServiceOrderPo);
		List<InfoServiceOrderVo> listVo =new ArrayList<InfoServiceOrderVo>();
		if(list != null && list.size()>0){
			for(InfoServiceOrderPo po:list ){
				InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
				BeanUtils.copyProperties(po,infoServiceOrderVo);
				listVo.add(infoServiceOrderVo);
			}

			logger.info("----------------queryInfoServiceOrderByOfrOrderNo-------------end----------");
			return listVo;
		}else{
			return null;
		}


	}

	@Override
	public boolean createInfoServiceOrder(InfoServiceOrderVo vo)
			throws Exception {
		logger.info("----------------createInfoServiceOrder-----------------------");
		InfoServiceOrderPo infoServiceOrderPo =new InfoServiceOrderPo();
		BeanUtils.copyProperties(vo,infoServiceOrderPo);

		infoServiceOrderServ.create(infoServiceOrderPo);

		logger.info("----------------createInfoServiceOrder---------end--------------");
		return true;
	}

	@Override
	public boolean updateInfoServiceOrder(InfoServiceOrderVo vo)
			throws Exception {
		logger.info("----------------updateInfoServiceOrder-----------------------");
		InfoServiceOrderPo infoServiceOrderPo =new InfoServiceOrderPo();
		BeanUtils.copyProperties(vo,infoServiceOrderPo);

		infoServiceOrderServ.update(infoServiceOrderPo);

		logger.info("----------------updateInfoServiceOrder---------end--------------");
		return true;
	}

	@Override
	public boolean deleteInfoServiceOrder(InfoServiceOrderVo vo)
			throws Exception {
		logger.info("----------------deleteInfoServiceOrder-----------------------");
		InfoServiceOrderPo infoServiceOrderPo =new InfoServiceOrderPo();
		BeanUtils.copyProperties(vo,infoServiceOrderPo);

		infoServiceOrderServ.delete(infoServiceOrderPo);

		logger.info("----------------deleteInfoServiceOrder---------end--------------");
		return true;
	}

	@Override
	public List<InfoServiceOrderVo> queryInfoServiceOrderBySaleOrderNo(
			InfoServiceOrderVo vo) throws Exception {

		logger.info("----------------queryInfoServiceOrderByOfrOrderNo-----------------------");

		InfoServiceOrderPo infoServiceOrderPo =new InfoServiceOrderPo();
		BeanUtils.copyProperties(vo,infoServiceOrderPo);
		List<InfoServiceOrderPo> list =infoServiceOrderServ.queryInfoServiceOrderBySaleOrderNo(infoServiceOrderPo);
		List<InfoServiceOrderVo> listVo =new ArrayList<InfoServiceOrderVo>();
		if(list !=null){
			for(InfoServiceOrderPo po:list ){
				InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
				BeanUtils.copyProperties(po,infoServiceOrderVo);
				listVo.add(infoServiceOrderVo);
			}
		}
		logger.info("----------------queryInfoServiceOrderByOfrOrderNo-------------end----------");
		return listVo;
	}

	@Override
	public List<InfoServiceOrderVo> queryInfoServiceOrderByOfrOrderNo(
			InfoServiceOrderVo vo) throws Exception {
		logger.info("----------------queryInfoServiceOrderByOfrOrderNo-----------------------");


		InfoServiceOrderPo infoServiceOrderPo =new InfoServiceOrderPo();
		BeanUtils.copyProperties(vo,infoServiceOrderPo);
		List<InfoServiceOrderPo> list =infoServiceOrderServ.queryInfoServiceOrderByOfrOrderNo(infoServiceOrderPo);
		List<InfoServiceOrderVo> listVo =new ArrayList<InfoServiceOrderVo>();
		for(InfoServiceOrderPo po:list ){
			InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
			BeanUtils.copyProperties(po,infoServiceOrderVo);
			listVo.add(infoServiceOrderVo);
		}

		logger.info("----------------queryInfoServiceOrderByOfrOrderNo-------------end----------");
		return listVo;
	}

	@Override
	public List<InfoServiceOrderVo> queryInfoServiceOrderByVo(InfoServiceOrderVo vo) throws Exception {
		logger.info("----------------queryInfoServiceOrder-----------------------");

		InfoServiceOrderPo infoServiceOrderPo =new InfoServiceOrderPo();
		BeanUtils.copyProperties(vo,infoServiceOrderPo);
		List<InfoServiceOrderPo> list =infoServiceOrderServ.queryInfoServiceOrderByPo(infoServiceOrderPo);
		List<InfoServiceOrderVo> listVo =new ArrayList<InfoServiceOrderVo>();
		if(list!=null){
			for(InfoServiceOrderPo po:list ){
				InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
				BeanUtils.copyProperties(po,infoServiceOrderVo);
				listVo.add(infoServiceOrderVo);
			}
		}
		logger.info("----------------queryInfoServiceOrder-------------end----------");
		return listVo;
	}
}
