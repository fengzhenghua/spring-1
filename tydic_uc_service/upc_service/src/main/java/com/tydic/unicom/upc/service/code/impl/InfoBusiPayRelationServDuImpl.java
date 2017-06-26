package com.tydic.unicom.upc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.upc.base.database.code.interfaces.InfoBusiPayRelationServ;
import com.tydic.unicom.upc.base.database.po.code.InfoBusiPayRelationPo;
import com.tydic.unicom.upc.service.code.interfaces.InfoBusiPayRelationServDu;
import com.tydic.unicom.upc.vo.code.InfoBusiPayRelationVo;

public class InfoBusiPayRelationServDuImpl implements InfoBusiPayRelationServDu {

	@Autowired
	private InfoBusiPayRelationServ infoBusiPayRelationServ;
	
	@Override
	public List<InfoBusiPayRelationVo> queryByBusiId(String busi_id) {
		List<InfoBusiPayRelationPo> list = infoBusiPayRelationServ.queryByBusiId(busi_id);
		
		if(list != null && list.size() > 0){
			List<InfoBusiPayRelationVo> voList = new ArrayList<>();
			
			for(InfoBusiPayRelationPo po : list){
				InfoBusiPayRelationVo vo = new InfoBusiPayRelationVo();
				BeanUtils.copyProperties(po, vo);
				
				voList.add(vo);
			}
			
			return voList;
		}
		
		return null;
	}

	@Override
	public InfoBusiPayRelationVo queryByBusIdPayType(String busi_id, String pay_type) {
		InfoBusiPayRelationPo po = infoBusiPayRelationServ.queryByBusIdPayType(busi_id, pay_type);
		if(po != null){
			InfoBusiPayRelationVo vo = new InfoBusiPayRelationVo();
			BeanUtils.copyProperties(po, vo);
			return vo;
		}
		
		return null;
	}

}
