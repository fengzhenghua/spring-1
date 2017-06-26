package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderTerminalPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderTerminalServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderTerminalServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderTerminalVo;
@Service("InfoServiceOrderTerminalServDu")
public class InfoServiceOrderTerminalServDuImpl implements InfoServiceOrderTerminalServDu{

	@Autowired
	private InfoServiceOrderTerminalServ infoServiceOrderTerminalServ;

//	@Override
//	public List<InfoServiceOrderTerminalVo> queryInfoServiceOrderTerminalByOfrOrderNo(
//			InfoServiceOrderTerminalVo vo) throws Exception {
//		InfoServiceOrderTerminalPo infoServiceOrderTerminalPo = new InfoServiceOrderTerminalPo();
//		BeanUtils.copyProperties(vo, infoServiceOrderTerminalPo);
//		List<InfoServiceOrderTerminalVo> listVo = new ArrayList<InfoServiceOrderTerminalVo>();
//		List<InfoServiceOrderTerminalPo> listPo = infoServiceOrderTerminalServ.queryInfoServiceOrderTerminalByOfrOrderNo(infoServiceOrderTerminalPo);
//		if(listPo != null && listPo.size()>0){
//			for(InfoServiceOrderTerminalPo po : listPo){
//				InfoServiceOrderTerminalVo ordVo = new InfoServiceOrderTerminalVo();
//				BeanUtils.copyProperties(po, ordVo);
//				listVo.add(ordVo);
//			}
//			return listVo;
//		}else{
//			return null;
//		}
//	}

	@Override
	public List<InfoServiceOrderTerminalVo> queryInfoServiceOrderTerminalByOrderNo(
			InfoServiceOrderTerminalVo vo) throws Exception {
		InfoServiceOrderTerminalPo infoServiceOrderTerminalPo = new InfoServiceOrderTerminalPo();
		BeanUtils.copyProperties(vo, infoServiceOrderTerminalPo);
		List<InfoServiceOrderTerminalVo> listVo = new ArrayList<InfoServiceOrderTerminalVo>();
		List<InfoServiceOrderTerminalPo> listPo = infoServiceOrderTerminalServ.queryInfoServiceOrderTerminalByOrderNo(infoServiceOrderTerminalPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderTerminalPo po : listPo){
				InfoServiceOrderTerminalVo ordVo = new InfoServiceOrderTerminalVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
