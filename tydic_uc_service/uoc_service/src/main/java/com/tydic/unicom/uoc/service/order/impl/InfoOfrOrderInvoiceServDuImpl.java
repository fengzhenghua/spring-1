package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderInvoicePo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderInvoiceServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOfrOrderInvoiceServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderInvoiceVo;
@Service("InfoOfrOrderInvoiceServDu")
public class InfoOfrOrderInvoiceServDuImpl implements InfoOfrOrderInvoiceServDu{

	@Autowired
	private InfoOfrOrderInvoiceServ infoOfrOrderInvoiceServ;

	@Override
	public List<InfoOfrOrderInvoiceVo> queryInfoOfrOrderInvoiceByOrderNo(
			InfoOfrOrderInvoiceVo vo) throws Exception {
		InfoOfrOrderInvoicePo infoOfrOrderInvoice = new InfoOfrOrderInvoicePo();
		BeanUtils.copyProperties(vo, infoOfrOrderInvoice);
		List<InfoOfrOrderInvoicePo> listPo = infoOfrOrderInvoiceServ.queryInfoOfrOrderInvoiceByOrderNo(infoOfrOrderInvoice);
		List<InfoOfrOrderInvoiceVo> listVo = new ArrayList<InfoOfrOrderInvoiceVo>();
		if(listPo != null && listPo.size()>0){
			for(InfoOfrOrderInvoicePo po : listPo){
				InfoOfrOrderInvoiceVo ordVo = new InfoOfrOrderInvoiceVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
	

}
