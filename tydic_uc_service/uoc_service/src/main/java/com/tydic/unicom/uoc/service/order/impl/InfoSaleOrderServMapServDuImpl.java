package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderServMapPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderServMapServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServMapServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderServMapVo;
@Service("InfoSaleOrderServMapServDu")
public class InfoSaleOrderServMapServDuImpl implements
		InfoSaleOrderServMapServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderServMapServDuImpl.class);
	@Autowired
	private InfoSaleOrderServMapServ infoSaleOrderServMapServ;
	
	@Override
	public List<InfoSaleOrderServMapVo> queryInfoSaleOrderServMapBySaleOrderNo(
			InfoSaleOrderServMapVo vo) throws Exception {
		logger.info("----------------queryInfoSaleOrderServMapBySaleOrderNo-----------------------");

		
		InfoSaleOrderServMapPo infoSaleOrderServMapPo =new InfoSaleOrderServMapPo();
		BeanUtils.copyProperties(vo,infoSaleOrderServMapPo);
		List<InfoSaleOrderServMapPo> list =infoSaleOrderServMapServ.queryInfoSaleOrderServMapBySaleOrderNo(infoSaleOrderServMapPo);
		List<InfoSaleOrderServMapVo> listVo =new ArrayList<InfoSaleOrderServMapVo>();
		if(list !=null){
			for(InfoSaleOrderServMapPo po:list ){
				InfoSaleOrderServMapVo infoSaleOrderServMapVo =new InfoSaleOrderServMapVo();
				BeanUtils.copyProperties(po,infoSaleOrderServMapVo);
				listVo.add(infoSaleOrderServMapVo);
			}
		}
		logger.info("----------------queryInfoSaleOrderServMapBySaleOrderNo-------------end----------");
		return listVo;
	}

	@Override
	public boolean deleteInfoSaleOrderServMapBySaleOrderNo(
			InfoSaleOrderServMapVo vo) throws Exception {
		logger.info("----------------deleteInfoSaleOrderServMapBySaleOrderNo-----------------------");
		InfoSaleOrderServMapPo infoSaleOrderServMapPo =new InfoSaleOrderServMapPo();
		BeanUtils.copyProperties(vo,infoSaleOrderServMapPo);
		
		infoSaleOrderServMapServ.deleteInfoSaleOrderServMapBySaleOrderNo(infoSaleOrderServMapPo);

		logger.info("----------------deleteInfoSaleOrderServMapBySaleOrderNo---------end--------------");
		return true;
	}

	@Override
	public boolean createInfoSaleOrderServMap(InfoSaleOrderServMapVo vo)
			throws Exception {
		logger.info("----------------createInfoSaleOrderServMap-----------------------");
		InfoSaleOrderServMapPo infoSaleOrderServMapPo =new InfoSaleOrderServMapPo();
		BeanUtils.copyProperties(vo,infoSaleOrderServMapPo);
		
		infoSaleOrderServMapServ.createInfoSaleOrderServMap(infoSaleOrderServMapPo);

		logger.info("----------------createInfoSaleOrderServMap---------end--------------");
		return true;
	}


}
