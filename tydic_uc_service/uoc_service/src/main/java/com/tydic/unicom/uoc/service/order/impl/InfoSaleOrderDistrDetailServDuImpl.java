package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderDistrDetailPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderDistrDetailServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderDistrDetailServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderDistrDetailVo;
@Service("InfoSaleOrderDistrDetailServDu")
public class InfoSaleOrderDistrDetailServDuImpl implements
		InfoSaleOrderDistrDetailServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderDistrDetailServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderDistrDetailServ infoSaleOrderDistrDetailServ;	
	@Override
	public boolean deleteInfoSaleOrderDistrDetailBySaleOrderNo(
			InfoSaleOrderDistrDetailVo vo) throws Exception {
		logger.info("----------------deleteInfoSaleOrderDistrDetailBySaleOrderNo-----------------------");
		InfoSaleOrderDistrDetailPo infoSaleOrderDistrDetail =new InfoSaleOrderDistrDetailPo();
		BeanUtils.copyProperties(vo,infoSaleOrderDistrDetail);
		
		infoSaleOrderDistrDetailServ.deleteInfoSaleOrderDistrDetailBySaleOrderNo(infoSaleOrderDistrDetail);

		logger.info("----------------deleteInfoSaleOrderDistrDetailBySaleOrderNo---------end--------------");
		return true;
	}

	@Override
	public List<InfoSaleOrderDistrDetailVo> queryInfoSaleOrderDistrDetailBySaleOrderNo(
			InfoSaleOrderDistrDetailVo vo) throws Exception {
		InfoSaleOrderDistrDetailPo infoSaleOrderDistrDetail =new InfoSaleOrderDistrDetailPo();
		BeanUtils.copyProperties(vo,infoSaleOrderDistrDetail);
		List<InfoSaleOrderDistrDetailPo> listPo = infoSaleOrderDistrDetailServ.queryInfoSaleOrderDistrDetailBySaleOrderNo(infoSaleOrderDistrDetail);
		List<InfoSaleOrderDistrDetailVo> listVo = new ArrayList<InfoSaleOrderDistrDetailVo>();
		if(listPo != null && listPo.size() >0){
			for(InfoSaleOrderDistrDetailPo po : listPo){
				InfoSaleOrderDistrDetailVo ordVo =new InfoSaleOrderDistrDetailVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
		
	}

	@Override
	public List<InfoSaleOrderDistrDetailVo> queryInfoSaleOrderDistrDetailByPayOrderNo(
			InfoPayOrderVo vo) throws Exception {
		InfoPayOrderPo InfoPayOrder = new InfoPayOrderPo();
		BeanUtils.copyProperties(vo, InfoPayOrder);
		List<InfoSaleOrderDistrDetailPo> listPo = infoSaleOrderDistrDetailServ.queryInfoSaleOrderDistrDetailByPayOrderNo(InfoPayOrder);
		List<InfoSaleOrderDistrDetailVo> listVo = new ArrayList<InfoSaleOrderDistrDetailVo>();
		if(listPo != null && listPo.size() >0){
			for(InfoSaleOrderDistrDetailPo po : listPo){
				InfoSaleOrderDistrDetailVo ordVo =new InfoSaleOrderDistrDetailVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}


}
