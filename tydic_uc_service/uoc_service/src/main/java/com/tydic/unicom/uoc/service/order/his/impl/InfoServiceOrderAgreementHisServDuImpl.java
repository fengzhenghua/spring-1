package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderAgreementHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderAgreementHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderAgreementHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderAgreementHisVo;

@Service("InfoServiceOrderAgreementHisServDu")
public class InfoServiceOrderAgreementHisServDuImpl implements InfoServiceOrderAgreementHisServDu{

	@Autowired
	private InfoServiceOrderAgreementHisServ infoServiceOrderAgreementHisServ;

	@Override
	public List<InfoServiceOrderAgreementHisVo> queryInfoServiceOrderAgreementHisByOrderNo(
			InfoServiceOrderAgreementHisVo vo) throws Exception {
		InfoServiceOrderAgreementHisPo InfoServiceOrderAgreementHisPo = new InfoServiceOrderAgreementHisPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderAgreementHisPo);
		
		List<InfoServiceOrderAgreementHisVo> listVo = new ArrayList<InfoServiceOrderAgreementHisVo>();
		List<InfoServiceOrderAgreementHisPo> listPo = infoServiceOrderAgreementHisServ.queryInfoServiceOrderAgreementHisByOrderNo(InfoServiceOrderAgreementHisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderAgreementHisPo po : listPo){
				InfoServiceOrderAgreementHisVo ordVo = new InfoServiceOrderAgreementHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
		
	}

}
