package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderInvoiceHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOfrOrderInvoiceHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoOfrOrderInvoiceHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderInvoiceHisVo;

@Service("InfoOfrOrderInvoiceHisServDu")
public class InfoOfrOrderInvoiceHisServDuImpl implements InfoOfrOrderInvoiceHisServDu{

	@Autowired
	private InfoOfrOrderInvoiceHisServ infoOfrOrderInvoiceHisServ;
	@Override
	public List<InfoOfrOrderInvoiceHisVo> queryInfoOfrOrderInvoiceByOrderNo(
			InfoOfrOrderInvoiceHisVo vo) throws Exception {
		InfoOfrOrderInvoiceHisPo infoOfrOrderInvoiceHis = new InfoOfrOrderInvoiceHisPo();
		BeanUtils.copyProperties(vo, infoOfrOrderInvoiceHis);
		List<InfoOfrOrderInvoiceHisPo> listPo = infoOfrOrderInvoiceHisServ.queryInfoOfrOrderInvoiceHisByOrderNo(infoOfrOrderInvoiceHis);
		List<InfoOfrOrderInvoiceHisVo> listVo = new ArrayList<InfoOfrOrderInvoiceHisVo>();
		if(listPo != null && listPo.size()>0){
			for(InfoOfrOrderInvoiceHisPo po : listPo){
				InfoOfrOrderInvoiceHisVo ordVo = new InfoOfrOrderInvoiceHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
	

}
