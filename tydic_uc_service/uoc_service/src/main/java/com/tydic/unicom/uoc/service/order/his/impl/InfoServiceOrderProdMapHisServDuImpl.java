package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderProdMapHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderProdMapHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderProdMapHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderProdMapHisVo;

@Service("InfoServiceOrderProdMapHisServDu")
public class InfoServiceOrderProdMapHisServDuImpl implements InfoServiceOrderProdMapHisServDu{
	Logger logger=Logger.getLogger(InfoServiceOrderProdMapHisServDuImpl.class);

	@Autowired
	private InfoServiceOrderProdMapHisServ infoServiceOrderProdMapHisServ;

	@Override
	public List<InfoServiceOrderProdMapHisVo> queryInfoServiceOrderProdMapHisByOrderNo(
			InfoServiceOrderProdMapHisVo vo) throws Exception {
		logger.info("----------------查询info_service_order_prod_map_his-----------------");
		InfoServiceOrderProdMapHisPo InfoServiceOrderProdMapHisPo = new InfoServiceOrderProdMapHisPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderProdMapHisPo);
		List<InfoServiceOrderProdMapHisVo> listVo = new ArrayList<InfoServiceOrderProdMapHisVo>();
		List<InfoServiceOrderProdMapHisPo> listPo = infoServiceOrderProdMapHisServ.queryInfoServiceOrderProdMapHisByOrderNo(InfoServiceOrderProdMapHisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderProdMapHisPo po : listPo){
				InfoServiceOrderProdMapHisVo ordVo = new InfoServiceOrderProdMapHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

	@Override
	public List<InfoServiceOrderProdMapHisVo> queryInfoServiceOrderProdHisMapByVo(
			InfoServiceOrderProdMapHisVo vo) throws Exception {
		logger.info("----------------查询info_service_order_prod_map_his-----------------");
		InfoServiceOrderProdMapHisPo InfoServiceOrderProdMapHisPo = new InfoServiceOrderProdMapHisPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderProdMapHisPo);
		List<InfoServiceOrderProdMapHisVo> listVo = new ArrayList<InfoServiceOrderProdMapHisVo>();
		List<InfoServiceOrderProdMapHisPo> listPo = infoServiceOrderProdMapHisServ.queryInfoServiceOrderProdMapHisByProdCode(InfoServiceOrderProdMapHisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderProdMapHisPo po : listPo){
				InfoServiceOrderProdMapHisVo ordVo = new InfoServiceOrderProdMapHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
