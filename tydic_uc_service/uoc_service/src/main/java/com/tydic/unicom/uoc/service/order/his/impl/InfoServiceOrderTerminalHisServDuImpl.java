package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderTerminalHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderTerminalHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderTerminalHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderTerminalHisVo;

@Service("InfoServiceOrderTerminalHisServDu")
public class InfoServiceOrderTerminalHisServDuImpl implements InfoServiceOrderTerminalHisServDu{

	@Autowired
	private InfoServiceOrderTerminalHisServ infoServiceOrderTerminalHisServ;

	@Override
	public List<InfoServiceOrderTerminalHisVo> queryInfoServiceOrderTerminalHisByOrderNo(
			InfoServiceOrderTerminalHisVo vo) throws Exception {
		InfoServiceOrderTerminalHisPo InfoServiceOrderTerminalHisPo = new InfoServiceOrderTerminalHisPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderTerminalHisPo);
		List<InfoServiceOrderTerminalHisVo> listVo = new ArrayList<InfoServiceOrderTerminalHisVo>();
		List<InfoServiceOrderTerminalHisPo> listPo = infoServiceOrderTerminalHisServ.queryInfoServiceOrderTerminalHisByOrderNo(InfoServiceOrderTerminalHisPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderTerminalHisPo po : listPo){
				InfoServiceOrderTerminalHisVo ordVo = new InfoServiceOrderTerminalHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
