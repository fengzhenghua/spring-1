package com.tydic.unicom.upc.service.inst.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.upc.base.database.inst.interfaces.InfoOrderGoodsDetailServ;
import com.tydic.unicom.upc.base.database.po.inst.InfoOrderGoodsDetailPo;
import com.tydic.unicom.upc.service.inst.interfaces.InfoOrderGoodsDetailServDu;
import com.tydic.unicom.upc.vo.inst.InfoOrderGoodsDetailVo;

public class InfoOrderGoodsDetailServDuImpl implements InfoOrderGoodsDetailServDu {

	@Autowired
	private InfoOrderGoodsDetailServ infoOrderGoodsDetailServ;
	
	@Override
	public void addInfoOrderGoodsDetail(List<InfoOrderGoodsDetailVo> infoOrderGoodsDetailVoList) throws Exception{
		if(infoOrderGoodsDetailVoList != null && infoOrderGoodsDetailVoList.size() > 0){
			for(InfoOrderGoodsDetailVo vo : infoOrderGoodsDetailVoList){
				InfoOrderGoodsDetailPo po = new InfoOrderGoodsDetailPo();
				BeanUtils.copyProperties(vo, po);
				
				infoOrderGoodsDetailServ.addInfoOrderGoodsDetail(po);
			}
		}

	}

	@Override
	public List<InfoOrderGoodsDetailVo> queryGoodsDetailByOrderId(InfoOrderGoodsDetailVo infoOrderGoodsDetailVo) throws Exception {
		
		InfoOrderGoodsDetailPo infoOrderGoodsDetailPo = new InfoOrderGoodsDetailPo();
		BeanUtils.copyProperties(infoOrderGoodsDetailVo, infoOrderGoodsDetailPo);
		List<InfoOrderGoodsDetailPo> poList = infoOrderGoodsDetailServ.queryGoodsDetailByOrderId(infoOrderGoodsDetailPo);
		
		if(poList != null && poList.size() > 0){
			
			List<InfoOrderGoodsDetailVo> voList = new ArrayList<>();
			
			for(InfoOrderGoodsDetailPo po : poList){
				InfoOrderGoodsDetailVo vo = new InfoOrderGoodsDetailVo();
				BeanUtils.copyProperties(po, vo);
				
				voList.add(vo);
			}
			
			return voList;
		}
		
		return null;
	}

}
