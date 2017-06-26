package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPersionPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPersionServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderPersionServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderPersionVo;
@Service("InfoServiceOrderPersionServDu")
public class InfoServiceOrderPersionServDuImpl implements InfoServiceOrderPersionServDu{

	@Autowired
	private InfoServiceOrderPersionServ infoServiceOrderPersionServ;

//	@Override
//	public List<InfoServiceOrderPersionVo> queryInfoServiceOrderPersionByOfrOrderNo(
//			InfoServiceOrderPersionVo vo) throws Exception {
//		InfoServiceOrderPersionPo infoServiceOrderPersionPo = new InfoServiceOrderPersionPo();
//		BeanUtils.copyProperties(vo, infoServiceOrderPersionPo);
//		List<InfoServiceOrderPersionVo> listVo = new ArrayList<InfoServiceOrderPersionVo>();
//		List<InfoServiceOrderPersionPo> listPo = infoServiceOrderPersionServ.queryInfoServiceOrderPersionByOfrOrderNo(infoServiceOrderPersionPo);
//		if(listPo != null && listPo.size()>0){
//			for(InfoServiceOrderPersionPo po : listPo){
//				InfoServiceOrderPersionVo ordVo = new InfoServiceOrderPersionVo();
//				BeanUtils.copyProperties(po, ordVo);
//				listVo.add(ordVo);
//			}
//			return listVo;
//		}else{
//			return null;
//		}
//	}

	@Override
	public List<InfoServiceOrderPersionVo> queryInfoServiceOrderPersionByOrderNo(
			InfoServiceOrderPersionVo vo) throws Exception {
		InfoServiceOrderPersionPo infoServiceOrderPersionPo = new InfoServiceOrderPersionPo();
		BeanUtils.copyProperties(vo, infoServiceOrderPersionPo);
		List<InfoServiceOrderPersionVo> listVo = new ArrayList<InfoServiceOrderPersionVo>();
		List<InfoServiceOrderPersionPo> listPo = infoServiceOrderPersionServ.queryInfoServiceOrderPersionByOrderNo(infoServiceOrderPersionPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderPersionPo po : listPo){
				InfoServiceOrderPersionVo ordVo = new InfoServiceOrderPersionVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
