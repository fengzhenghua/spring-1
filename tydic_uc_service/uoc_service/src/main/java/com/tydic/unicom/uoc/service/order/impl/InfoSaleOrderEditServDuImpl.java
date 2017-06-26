package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderEditPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderEditServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderEditServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderEditVo;
@Service("InfoSaleOrderEditServDu")
public class InfoSaleOrderEditServDuImpl implements InfoSaleOrderEditServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderEditServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderEditServ infoSaleOrderEditServ;
	
	@Override
	public InfoSaleOrderEditVo getInfoSaleOrderEditBySaleOrderNo(
			InfoSaleOrderEditVo vo) throws Exception {
		logger.info("----------------getInfoSaleOrderEditBySaleOrderNo-----------------------");
		InfoSaleOrderEditPo infoSaleOrderEditPo =new InfoSaleOrderEditPo();
		BeanUtils.copyProperties(vo,infoSaleOrderEditPo);
		infoSaleOrderEditPo =infoSaleOrderEditServ.getInfoSaleOrderEditPoBySaleOrderNo(infoSaleOrderEditPo);
		if(infoSaleOrderEditPo ==null){
			return null;
		}
		BeanUtils.copyProperties(infoSaleOrderEditPo,vo);
		
		logger.info("----------------getInfoSaleOrderEditBySaleOrderNo--------------end---------");
		return vo;
	}

	@Override
	public boolean createInfoSaleOrderEdit(InfoSaleOrderEditVo vo)
			throws Exception {
		logger.info("----------------createInfoSaleOrderEdit-----------------------");
		InfoSaleOrderEditPo infoSaleOrderEditPo =new InfoSaleOrderEditPo();
		BeanUtils.copyProperties(vo,infoSaleOrderEditPo);
	
		infoSaleOrderEditServ.createInfoSaleOrderEditPo(infoSaleOrderEditPo);

		logger.info("----------------createInfoSaleOrderEdit------------end -----------");

		return true;
	}

	@Override
	public List<InfoSaleOrderEditVo> queryInfoSaleOrderEditBySaleOrderNo(
			InfoSaleOrderEditVo vo) throws Exception {
		InfoSaleOrderEditPo infoSaleOrderEditPo =new InfoSaleOrderEditPo();
		BeanUtils.copyProperties(vo,infoSaleOrderEditPo);
		List<InfoSaleOrderEditVo> listVo = new ArrayList<InfoSaleOrderEditVo>();
		List<InfoSaleOrderEditPo> listPo = infoSaleOrderEditServ.queryInfoSaleOrderEditPoBySaleOrderNo(infoSaleOrderEditPo);
		if(listPo != null && listPo.size()>0){
			for(InfoSaleOrderEditPo po : listPo){
				InfoSaleOrderEditVo ordVo = new InfoSaleOrderEditVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
		
	}

}
