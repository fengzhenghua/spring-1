package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderSimCardPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderSimCardServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderSimCardServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderSimCardVo;
@Service("InfoServiceOrderSimCardServDu")
public class InfoServiceOrderSimCardServDuImpl implements InfoServiceOrderSimCardServDu{

	@Autowired
	private InfoServiceOrderSimCardServ infoServiceOrderSimCardServ;

//	@Override
//	public List<InfoServiceOrderSimCardVo> queryInfoServiceOrderSimCardByOfrOrderNo(
//			InfoServiceOrderSimCardVo vo) throws Exception {
//		InfoServiceOrderSimCardPo InfoServiceOrderSimCardPo = new InfoServiceOrderSimCardPo();
//		BeanUtils.copyProperties(vo, InfoServiceOrderSimCardPo);
//		List<InfoServiceOrderSimCardVo> listVo = new ArrayList<InfoServiceOrderSimCardVo>();
//		List<InfoServiceOrderSimCardPo> listPo = infoServiceOrderSimCardServ.queryInfoServiceOrderSimCardByOfrOrderNo(InfoServiceOrderSimCardPo);
//		if(listPo != null && listPo.size()>0){
//			for(InfoServiceOrderSimCardPo po : listPo){
//				InfoServiceOrderSimCardVo ordVo = new InfoServiceOrderSimCardVo();
//				BeanUtils.copyProperties(po, ordVo);
//				listVo.add(ordVo);
//			}
//			return listVo;
//		}else{
//			return null;
//		}
//	}

	@Override
	public List<InfoServiceOrderSimCardVo> queryInfoServiceOrderSimCardByOrderNo(
			InfoServiceOrderSimCardVo vo) throws Exception {
		InfoServiceOrderSimCardPo InfoServiceOrderSimCardPo = new InfoServiceOrderSimCardPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderSimCardPo);
		List<InfoServiceOrderSimCardVo> listVo = new ArrayList<InfoServiceOrderSimCardVo>();
		List<InfoServiceOrderSimCardPo> listPo = infoServiceOrderSimCardServ.queryInfoServiceOrderSimCardByOrderNo(InfoServiceOrderSimCardPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderSimCardPo po : listPo){
				InfoServiceOrderSimCardVo ordVo = new InfoServiceOrderSimCardVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
	
}
