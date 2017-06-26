package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderProdMapPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderProdMapServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderProdMapServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderProdMapVo;
@Service("InfoServiceOrderProdMapServDu")
public class InfoServiceOrderProdMapServDuImpl implements InfoServiceOrderProdMapServDu{
	Logger logger=Logger.getLogger(InfoServiceOrderProdMapServDuImpl.class);

	@Autowired
	private InfoServiceOrderProdMapServ infoServiceOrderProdMapServ;

//	@Override
//	public List<InfoServiceOrderProdMapVo> queryInfoServiceOrderProdMapByOfrOrderNo(
//			InfoServiceOrderProdMapVo vo) throws Exception {
//		InfoServiceOrderProdMapPo InfoServiceOrderProdMapPo = new InfoServiceOrderProdMapPo();
//		BeanUtils.copyProperties(vo, InfoServiceOrderProdMapPo);
//		List<InfoServiceOrderProdMapVo> listVo = new ArrayList<InfoServiceOrderProdMapVo>();
//		List<InfoServiceOrderProdMapPo> listPo = infoServiceOrderProdMapServ.queryInfoServiceOrderProdMapByOfrOrderNo(InfoServiceOrderProdMapPo);
//		if(listPo != null && listPo.size()>0){
//			for(InfoServiceOrderProdMapPo po : listPo){
//				InfoServiceOrderProdMapVo ordVo = new InfoServiceOrderProdMapVo();
//				BeanUtils.copyProperties(po, ordVo);
//				listVo.add(ordVo);
//			}
//			return listVo;
//		}else{
//			return null;
//		}
//	}

	@Override
	public List<InfoServiceOrderProdMapVo> queryInfoServiceOrderProdMapByOrderNo(
			InfoServiceOrderProdMapVo vo) throws Exception {
		logger.info("----------------查询info_service_order_prod_map-----------------");
		InfoServiceOrderProdMapPo InfoServiceOrderProdMapPo = new InfoServiceOrderProdMapPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderProdMapPo);
		List<InfoServiceOrderProdMapVo> listVo = new ArrayList<InfoServiceOrderProdMapVo>();
		List<InfoServiceOrderProdMapPo> listPo = infoServiceOrderProdMapServ.queryInfoServiceOrderProdMapByOrderNo(InfoServiceOrderProdMapPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderProdMapPo po : listPo){
				InfoServiceOrderProdMapVo ordVo = new InfoServiceOrderProdMapVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

	@Override
	public List<InfoServiceOrderProdMapVo> queryInfoServiceOrderProdMapByVo(
			InfoServiceOrderProdMapVo vo) throws Exception {
		logger.info("----------------查询info_service_order_prod_map-----------------");
		InfoServiceOrderProdMapPo InfoServiceOrderProdMapPo = new InfoServiceOrderProdMapPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderProdMapPo);
		List<InfoServiceOrderProdMapVo> listVo = new ArrayList<InfoServiceOrderProdMapVo>();
		List<InfoServiceOrderProdMapPo> listPo = infoServiceOrderProdMapServ.queryInfoServiceOrderProdMapByProdCode(InfoServiceOrderProdMapPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderProdMapPo po : listPo){
				InfoServiceOrderProdMapVo ordVo = new InfoServiceOrderProdMapVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
