package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderPayPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderPayServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOfrOrderPayServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderPayVo;
@Service("InfoOfrOrderPayServDu")
public class InfoOfrOrderPayServDuImpl implements InfoOfrOrderPayServDu{

	@Autowired
	private InfoOfrOrderPayServ infoOfrOrderPayServ;
	@Override
	public List<InfoOfrOrderPayVo> queryInfoOfrOrderPayByOrderNo(
			InfoOfrOrderPayVo vo) throws Exception {
		InfoOfrOrderPayPo InfoOfrOrderPay = new InfoOfrOrderPayPo();
		BeanUtils.copyProperties(vo, InfoOfrOrderPay);
		List<InfoOfrOrderPayVo> listVo = new ArrayList<InfoOfrOrderPayVo>();
		List<InfoOfrOrderPayPo> listPo = infoOfrOrderPayServ.queryInfoOfrOrderPayByOrderNo(InfoOfrOrderPay);
		if(listPo != null && listPo.size()>0){
			for(InfoOfrOrderPayPo po : listPo){
				InfoOfrOrderPayVo ordVo = new InfoOfrOrderPayVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
		
	}

}
