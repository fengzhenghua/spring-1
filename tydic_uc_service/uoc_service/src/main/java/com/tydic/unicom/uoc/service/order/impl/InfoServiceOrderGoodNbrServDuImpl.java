package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderGoodNbrPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderGoodNbrServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderGoodNbrServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderGoodNbrVo;
@Service("InfoServiceOrderGoodNbrServDu")
public class InfoServiceOrderGoodNbrServDuImpl implements InfoServiceOrderGoodNbrServDu{

	@Autowired
	private InfoServiceOrderGoodNbrServ infoServiceOrderGoodNbrServ;

//	@Override
//	public List<InfoServiceOrderGoodNbrVo> queryInfoServiceOrderGoodNbrByOfrOrderNo(
//			InfoServiceOrderGoodNbrVo vo) throws Exception {
//		InfoServiceOrderGoodNbrPo infoServiceOrderGoodNbrPo = new InfoServiceOrderGoodNbrPo();
//		BeanUtils.copyProperties(vo, infoServiceOrderGoodNbrPo);
//		List<InfoServiceOrderGoodNbrVo> listVo = new ArrayList<InfoServiceOrderGoodNbrVo>();
//		List<InfoServiceOrderGoodNbrPo> listPo = infoServiceOrderGoodNbrServ.queryInfoServiceOrderGoodNbrByOfrOrderNo(infoServiceOrderGoodNbrPo);
//		if(listPo != null && listPo.size()>0){
//			for(InfoServiceOrderGoodNbrPo po : listPo){
//				InfoServiceOrderGoodNbrVo ordVo = new InfoServiceOrderGoodNbrVo();
//				BeanUtils.copyProperties(po, ordVo);
//				listVo.add(ordVo);
//			}
//			return listVo;
//		}else{
//			return null;
//		}
//	}

	@Override
	public List<InfoServiceOrderGoodNbrVo> queryInfoServiceOrderGoodNbrByOrderNo(
			InfoServiceOrderGoodNbrVo vo) throws Exception {
		InfoServiceOrderGoodNbrPo infoServiceOrderGoodNbrPo = new InfoServiceOrderGoodNbrPo();
		BeanUtils.copyProperties(vo, infoServiceOrderGoodNbrPo);
		List<InfoServiceOrderGoodNbrVo> listVo = new ArrayList<InfoServiceOrderGoodNbrVo>();
		List<InfoServiceOrderGoodNbrPo> listPo = infoServiceOrderGoodNbrServ.queryInfoServiceOrderGoodNbrByOrderNo(infoServiceOrderGoodNbrPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderGoodNbrPo po : listPo){
				InfoServiceOrderGoodNbrVo ordVo = new InfoServiceOrderGoodNbrVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
