package com.tydic.unicom.upc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.upc.base.database.code.interfaces.InfoPayParaAttrServ;
import com.tydic.unicom.upc.base.database.po.code.InfoPayParaAttrPo;
import com.tydic.unicom.upc.service.code.interfaces.InfoPayParaAttrServDu;
import com.tydic.unicom.upc.vo.code.InfoPayParaAttrValueVo;
import com.tydic.unicom.upc.vo.code.InfoPayParaAttrVo;

public class InfoPayParaAttrServDuImpl implements InfoPayParaAttrServDu {

	@Autowired
	private InfoPayParaAttrServ infoPayParaAttrServ;
	
	
	@Override
	public List<InfoPayParaAttrVo> getPayParaByPayType(String busi_id, String pay_type) {
		List<InfoPayParaAttrPo> list = infoPayParaAttrServ.getPayParaByPayType(busi_id, pay_type);
		
		if(list != null && list.size() > 0){
			List<InfoPayParaAttrVo> voList = new ArrayList<>();
			
			for(InfoPayParaAttrPo po : list){
				InfoPayParaAttrVo vo = new InfoPayParaAttrVo();
				BeanUtils.copyProperties(po, vo);
				
				voList.add(vo);
			}
			
			return voList;
		}
		
		return null;
	}


	@Override
	public InfoPayParaAttrValueVo getPayParaValueByPayType(String busi_id, String pay_type) {
		List<InfoPayParaAttrVo> voList = getPayParaByPayType(busi_id, pay_type);
		
		if(voList != null){
			InfoPayParaAttrValueVo valueVo = new InfoPayParaAttrValueVo();
			for(InfoPayParaAttrVo vo : voList){
				if("appid".equals(vo.getAttr_code())){
					valueVo.setAppid(vo.getAttr_value());
				}
				else if("privateKey".equals(vo.getAttr_code())){
					valueVo.setPrivateKey(vo.getAttr_value());
				}
				else if("publicKey".equals(vo.getAttr_code())){
					valueVo.setPublicKey(vo.getAttr_value());
				}
				else if("appsecret".equals(vo.getAttr_code())){
					valueVo.setAppsecret(vo.getAttr_value());
				}
				else if("mchid".equals(vo.getAttr_code())){
					valueVo.setMchid(vo.getAttr_value());
				}
				else if("signkey".equals(vo.getAttr_code())){
					valueVo.setSignkey(vo.getAttr_value());
				}
				else if("certname".equals(vo.getAttr_code())){
					valueVo.setCertname(vo.getAttr_value());
				}
				else if("certpassword".equals(vo.getAttr_code())){
					valueVo.setCertpassword(vo.getAttr_value());
				}
			}
			
			return valueVo;
		}
		return null;
	}

}
