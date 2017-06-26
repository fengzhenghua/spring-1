package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderAttrPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderAttrServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderAttrServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderAttrVo;

@Service("InfoSaleOrderAttrServDu")
public class InfoSaleOrderAttrServDuImpl implements InfoSaleOrderAttrServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderAttrServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderAttrServ infoSaleOrderAttrServ;	
	
	@Override
	public boolean deleteInfoSaleOrderAttrBySaleOrderNo(InfoSaleOrderAttrVo vo)
			throws Exception {
		logger.info("----------------deleteInfoSaleOrderAttrBySaleOrderNo-----------------------");
		InfoSaleOrderAttrPo infoSaleOrderAttr =new InfoSaleOrderAttrPo();
		BeanUtils.copyProperties(vo,infoSaleOrderAttr);
		
		infoSaleOrderAttrServ.deleteInfoSaleOrderAttrBySaleOrderNo(infoSaleOrderAttr);

		logger.info("----------------deleteInfoSaleOrderAttrBySaleOrderNo---------end--------------");
		return true;
	}

	@Override
	public List<InfoSaleOrderAttrVo> queryInfoSaleOrderAttrList(
			InfoSaleOrderAttrVo vo) throws Exception {
		InfoSaleOrderAttrPo infoSaleOrderAttr =new InfoSaleOrderAttrPo();
		BeanUtils.copyProperties(vo,infoSaleOrderAttr);
		List<InfoSaleOrderAttrPo> listPo = infoSaleOrderAttrServ.queryInfoSaleOrderAttrBySaleOrderNo(infoSaleOrderAttr);
		List<InfoSaleOrderAttrVo> listVo = new ArrayList<InfoSaleOrderAttrVo>();
		if(listPo != null && listPo.size()>0){
			for(InfoSaleOrderAttrPo po : listPo){
				InfoSaleOrderAttrVo ordVo = new InfoSaleOrderAttrVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

}
