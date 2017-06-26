package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderGuarantorPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderGuarantorServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderGuarantorServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderGuarantorVo;
@Service("InfoServiceOrderGuarantorServDu")
public class InfoServiceOrderGuarantorServDuImpl implements InfoServiceOrderGuarantorServDu{

	@Autowired
	private InfoServiceOrderGuarantorServ infoServiceOrderGuarantorServ;

//	@Override
//	public List<InfoServiceOrderGuarantorVo> queryInfoServiceOrderGuarantorByOfrOrderNo(
//			InfoServiceOrderGuarantorVo vo) throws Exception {
//		InfoServiceOrderGuarantorPo infoServiceOrderGuarantorPo = new InfoServiceOrderGuarantorPo();
//		BeanUtils.copyProperties(vo, infoServiceOrderGuarantorPo);
//		List<InfoServiceOrderGuarantorVo> listVo = new ArrayList<InfoServiceOrderGuarantorVo>();
//		List<InfoServiceOrderGuarantorPo> listPo = infoServiceOrderGuarantorServ.queryInfoServiceOrderGuarantorByOfrOrderNo(infoServiceOrderGuarantorPo);
//		if(listPo != null && listPo.size()>0){
//			for(InfoServiceOrderGuarantorPo po : listPo){
//				InfoServiceOrderGuarantorVo ordVo = new InfoServiceOrderGuarantorVo();
//				BeanUtils.copyProperties(po, ordVo);
//				listVo.add(ordVo);
//			}
//			return listVo;
//		}else{
//			return null;
//		}
//	}

	@Override
	public List<InfoServiceOrderGuarantorVo> queryInfoServiceOrderGuarantorByOrderNo(
			InfoServiceOrderGuarantorVo vo) throws Exception {
		InfoServiceOrderGuarantorPo infoServiceOrderGuarantorPo = new InfoServiceOrderGuarantorPo();
		BeanUtils.copyProperties(vo, infoServiceOrderGuarantorPo);
		List<InfoServiceOrderGuarantorVo> listVo = new ArrayList<InfoServiceOrderGuarantorVo>();
		List<InfoServiceOrderGuarantorPo> listPo = infoServiceOrderGuarantorServ.queryInfoServiceOrderGuarantorByOrderNo(infoServiceOrderGuarantorPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderGuarantorPo po : listPo){
				InfoServiceOrderGuarantorVo ordVo = new InfoServiceOrderGuarantorVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
