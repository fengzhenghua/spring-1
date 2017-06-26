package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderAttrHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderAttrHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderAttrHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderAttrHisVo;


@Service("InfoSaleOrderAttrHisServDu")
public class InfoSaleOrderAttrHisServDuImpl implements InfoSaleOrderAttrHisServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderAttrHisServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderAttrHisServ infoSaleOrderAttrHisServ;	
	
	@Override
	public List<InfoSaleOrderAttrHisVo> queryInfoSaleOrderAttrHisList(
			InfoSaleOrderAttrHisVo vo) throws Exception {
		InfoSaleOrderAttrHisPo InfoSaleOrderAttrHis =new InfoSaleOrderAttrHisPo();
		BeanUtils.copyProperties(vo,InfoSaleOrderAttrHis);
		List<InfoSaleOrderAttrHisPo> listPo = infoSaleOrderAttrHisServ.queryInfoSaleOrderAttrHisByOrderNo(InfoSaleOrderAttrHis);
		List<InfoSaleOrderAttrHisVo> listVo = new ArrayList<InfoSaleOrderAttrHisVo>();
		if(listPo != null && listPo.size()>0){
			for(InfoSaleOrderAttrHisPo po : listPo){
				InfoSaleOrderAttrHisVo ordVo = new InfoSaleOrderAttrHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}


}
