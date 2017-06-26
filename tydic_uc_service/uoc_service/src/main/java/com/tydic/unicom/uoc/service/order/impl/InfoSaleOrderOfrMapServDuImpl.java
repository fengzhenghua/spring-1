package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderOfrMapPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderOfrMapServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderOfrMapServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderOfrMapVo;
@Service("InfoSaleOrderOfrMapServDu")
public class InfoSaleOrderOfrMapServDuImpl implements InfoSaleOrderOfrMapServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderOfrMapServDuImpl.class);
	@Autowired
	private  InfoSaleOrderOfrMapServ infoSaleOrderOfrMapServ;
	
	@Override
	public List<InfoSaleOrderOfrMapVo> queryInfoSaleOrderOfrMapBySaleOrderNo(
			InfoSaleOrderOfrMapVo vo) throws Exception {
		logger.info("----------------queryInfoSaleOrderOfrMapBySaleOrderNo-----------------------");

		
		InfoSaleOrderOfrMapPo infoSaleOrderOfrMapPo =new InfoSaleOrderOfrMapPo();
		BeanUtils.copyProperties(vo,infoSaleOrderOfrMapPo);
		List<InfoSaleOrderOfrMapPo> list =infoSaleOrderOfrMapServ.queryInfoSaleOrderOfrMapBySaleOrderNo(infoSaleOrderOfrMapPo);
		List<InfoSaleOrderOfrMapVo> listVo =new ArrayList<InfoSaleOrderOfrMapVo>();
		if(list !=null){
			for(InfoSaleOrderOfrMapPo po:list ){
				InfoSaleOrderOfrMapVo infoSaleOrderOfrMapVo =new InfoSaleOrderOfrMapVo();
				BeanUtils.copyProperties(po,infoSaleOrderOfrMapVo);
				listVo.add(infoSaleOrderOfrMapVo);
			}
		}
		logger.info("----------------queryInfoSaleOrderOfrMapBySaleOrderNo-------------end----------");
		return listVo;
	}

	@Override
	public boolean deleteInfoSaleOrderOfrMapBySaleOrderNo(
			InfoSaleOrderOfrMapVo vo) throws Exception {
		logger.info("----------------deleteInfoSaleOrderOfrMapBySaleOrderNo-----------------------");
		InfoSaleOrderOfrMapPo infoSaleOrderOfrMapPo =new InfoSaleOrderOfrMapPo();
		BeanUtils.copyProperties(vo,infoSaleOrderOfrMapPo);
		
		infoSaleOrderOfrMapServ.deleteInfoSaleOrderOfrMapBySaleOrderNo(infoSaleOrderOfrMapPo);

		logger.info("----------------deleteInfoSaleOrderOfrMapBySaleOrderNo---------end--------------");
		return true;
	}

}
