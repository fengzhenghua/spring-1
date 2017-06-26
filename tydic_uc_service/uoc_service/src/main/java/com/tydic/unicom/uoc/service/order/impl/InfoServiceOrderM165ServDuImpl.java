package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderM165Po;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderM165Serv;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderM165ServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderM165Vo;
@Service("InfoServiceOrderM165ServDu")
public class InfoServiceOrderM165ServDuImpl implements InfoServiceOrderM165ServDu{

	@Autowired
	private InfoServiceOrderM165Serv infoServiceOrderM165Serv;

//	@Override
//	public List<InfoServiceOrderM165Vo> queryInfoServiceOrderM165ByOfrOrderNo(
//			InfoServiceOrderM165Vo vo) throws Exception {
//		InfoServiceOrderM165Po infoServiceOrderM165Po = new InfoServiceOrderM165Po();
//		BeanUtils.copyProperties(vo, infoServiceOrderM165Po);
//		List<InfoServiceOrderM165Vo> listVo = new ArrayList<InfoServiceOrderM165Vo>();
//		List<InfoServiceOrderM165Po> listPo = infoServiceOrderM165Serv.queryInfoServiceOrderM165ByOfrOrderNo(infoServiceOrderM165Po);
//		if(listPo != null && listPo.size()>0){
//			for(InfoServiceOrderM165Po po : listPo){
//				InfoServiceOrderM165Vo ordVo = new InfoServiceOrderM165Vo();
//				BeanUtils.copyProperties(po, ordVo);
//				listVo.add(ordVo);
//			}
//			return listVo;
//		}else{
//			return null;
//		}
//	}

	@Override
	public List<InfoServiceOrderM165Vo> queryInfoServiceOrderM165ByOrderNo(
			InfoServiceOrderM165Vo vo) throws Exception {
		InfoServiceOrderM165Po infoServiceOrderM165Po = new InfoServiceOrderM165Po();
		BeanUtils.copyProperties(vo, infoServiceOrderM165Po);
		List<InfoServiceOrderM165Vo> listVo = new ArrayList<InfoServiceOrderM165Vo>();
		List<InfoServiceOrderM165Po> listPo = infoServiceOrderM165Serv.queryInfoServiceOrderM165ByOrderNo(infoServiceOrderM165Po);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderM165Po po : listPo){
				InfoServiceOrderM165Vo ordVo = new InfoServiceOrderM165Vo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
