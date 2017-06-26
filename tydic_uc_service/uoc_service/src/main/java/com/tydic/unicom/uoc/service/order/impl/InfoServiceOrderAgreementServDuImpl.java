package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderAgreementPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderAgreementServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderAgreementServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderAgreementVo;

@Service("InfoServiceOrderAgreementServDu")
public class InfoServiceOrderAgreementServDuImpl implements InfoServiceOrderAgreementServDu{

	@Autowired
	private InfoServiceOrderAgreementServ infoServiceOrderAgreementServ;

	@Override
	public List<InfoServiceOrderAgreementVo> queryInfoServiceOrderAgreementByOrderNo(
			InfoServiceOrderAgreementVo vo) throws Exception {
		InfoServiceOrderAgreementPo InfoServiceOrderAgreementPo = new InfoServiceOrderAgreementPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderAgreementPo);
		
		List<InfoServiceOrderAgreementVo> listVo = new ArrayList<InfoServiceOrderAgreementVo>();
		List<InfoServiceOrderAgreementPo> listPo = infoServiceOrderAgreementServ.queryInfoServiceOrderAgreementByOrderNo(InfoServiceOrderAgreementPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderAgreementPo po : listPo){
				InfoServiceOrderAgreementVo ordVo = new InfoServiceOrderAgreementVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
		
	}
}
