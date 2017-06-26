package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderFixPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderFixServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderFixServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderFixVo;
@Service("InfoServiceOrderFixServDu")
public class InfoServiceOrderFixServDuImpl implements InfoServiceOrderFixServDu{

	@Autowired
	private InfoServiceOrderFixServ infoServiceOrderFixServ;

//	@Override
//	public List<InfoServiceOrderFixVo> queryInfoServiceOrderFixByOfrOrderNo(
//			InfoServiceOrderFixVo vo) throws Exception {
//		InfoServiceOrderFixPo infoServiceOrderFixPo = new InfoServiceOrderFixPo();
//		BeanUtils.copyProperties(vo, infoServiceOrderFixPo);
//		List<InfoServiceOrderFixVo> listVo = new ArrayList<InfoServiceOrderFixVo>();
//		List<InfoServiceOrderFixPo> listPo = infoServiceOrderFixServ.queryInfoServiceOrderFixByOfrOrderNo(infoServiceOrderFixPo);
//		if(listPo != null && listPo.size()>0){
//			for(InfoServiceOrderFixPo po : listPo){
//				InfoServiceOrderFixVo ordVo = new InfoServiceOrderFixVo();
//				BeanUtils.copyProperties(po, ordVo);
//				listVo.add(ordVo);
//			}
//			return listVo;
//		}else{
//			return null;
//		}
//	}

	@Override
	public List<InfoServiceOrderFixVo> queryInfoServiceOrderFixByOrderNo(
			InfoServiceOrderFixVo vo) throws Exception {
		InfoServiceOrderFixPo infoServiceOrderFixPo = new InfoServiceOrderFixPo();
		BeanUtils.copyProperties(vo, infoServiceOrderFixPo);
		List<InfoServiceOrderFixVo> listVo = new ArrayList<InfoServiceOrderFixVo>();
		List<InfoServiceOrderFixPo> listPo = infoServiceOrderFixServ.queryInfoServiceOrderFixByOrderNo(infoServiceOrderFixPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderFixPo po : listPo){
				InfoServiceOrderFixVo ordVo = new InfoServiceOrderFixVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
