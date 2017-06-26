package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPayPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPayServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderPayServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderPayVo;
@Service("InfoServiceOrderPayServDu")
public class InfoServiceOrderPayServDuImpl implements InfoServiceOrderPayServDu{

	@Autowired
	private InfoServiceOrderPayServ infoServiceOrderPayServ;

//	@Override
//	public List<InfoServiceOrderPayVo> queryInfoServiceOrderPayByOfrOrderNo(
//			InfoServiceOrderPayVo vo) throws Exception {
//		InfoServiceOrderPayPo infoServiceOrderPayPo = new InfoServiceOrderPayPo();
//		BeanUtils.copyProperties(vo, infoServiceOrderPayPo);
//		List<InfoServiceOrderPayVo> listVo = new ArrayList<InfoServiceOrderPayVo>();
//		List<InfoServiceOrderPayPo> listPo = infoServiceOrderPayServ.queryInfoServiceOrderPayByOfrOrderNo(infoServiceOrderPayPo);
//		if(listPo != null && listPo.size()>0){
//			for(InfoServiceOrderPayPo po : listPo){
//				InfoServiceOrderPayVo ordVo = new InfoServiceOrderPayVo();
//				BeanUtils.copyProperties(po, ordVo);
//				listVo.add(ordVo);
//			}
//			return listVo;
//		}else{
//			return null;
//		}
//	}

	@Override
	public List<InfoServiceOrderPayVo> queryInfoServiceOrderPayByOrderNo(
			InfoServiceOrderPayVo vo) throws Exception {
		InfoServiceOrderPayPo infoServiceOrderPayPo = new InfoServiceOrderPayPo();
		BeanUtils.copyProperties(vo, infoServiceOrderPayPo);
		List<InfoServiceOrderPayVo> listVo = new ArrayList<InfoServiceOrderPayVo>();
		List<InfoServiceOrderPayPo> listPo = infoServiceOrderPayServ.queryInfoServiceOrderPayByOrderNo(infoServiceOrderPayPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderPayPo po : listPo){
				InfoServiceOrderPayVo ordVo = new InfoServiceOrderPayVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
