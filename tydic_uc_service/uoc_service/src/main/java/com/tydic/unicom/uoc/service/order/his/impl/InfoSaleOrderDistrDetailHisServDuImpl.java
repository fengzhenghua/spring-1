package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderDistrDetailHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderDistrDetailHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderDistrDetailHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoPayOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderDistrDetailHisVo;

@Service("InfoSaleOrderDistrDetailHisServDu")
public class InfoSaleOrderDistrDetailHisServDuImpl implements
		InfoSaleOrderDistrDetailHisServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderDistrDetailHisServDuImpl.class);
	
	@Autowired
	private InfoSaleOrderDistrDetailHisServ infoSaleOrderDistrDetailHisServ;		

	@Override
	public List<InfoSaleOrderDistrDetailHisVo> queryInfoSaleOrderDistrDetailHisBySaleOrderNo(
			InfoSaleOrderDistrDetailHisVo vo) throws Exception {
		InfoSaleOrderDistrDetailHisPo InfoSaleOrderDistrDetailHis =new InfoSaleOrderDistrDetailHisPo();
		BeanUtils.copyProperties(vo,InfoSaleOrderDistrDetailHis);
		List<InfoSaleOrderDistrDetailHisPo> listPo = infoSaleOrderDistrDetailHisServ.queryInfoSaleOrderDistrDetailHisByOrderNo(InfoSaleOrderDistrDetailHis);
		List<InfoSaleOrderDistrDetailHisVo> listVo = new ArrayList<InfoSaleOrderDistrDetailHisVo>();
		if(listPo != null && listPo.size() >0){
			for(InfoSaleOrderDistrDetailHisPo po : listPo){
				InfoSaleOrderDistrDetailHisVo ordVo =new InfoSaleOrderDistrDetailHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
		
	}

	@Override
	public List<InfoSaleOrderDistrDetailHisVo> queryInfoSaleOrderDistrDetailHisByPayOrderNo(
			InfoPayOrderHisVo vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
