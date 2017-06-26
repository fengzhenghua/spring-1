package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderActivityPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderActivityServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderActivityServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderActivityVo;

@Service("InfoServiceOrderActivityServDu")
public class InfoServiceOrderActivityServDuImpl implements InfoServiceOrderActivityServDu{

	@Autowired
	private InfoServiceOrderActivityServ infoServiceOrderActivityServ;
	@Override
	public List<InfoServiceOrderActivityVo> queryInfoServiceOrderActivityByOrderNo(InfoServiceOrderActivityVo vo)throws Exception {
		InfoServiceOrderActivityPo InfoServiceOrderActivityPo = new InfoServiceOrderActivityPo();
		BeanUtils.copyProperties(vo, InfoServiceOrderActivityPo);
		List<InfoServiceOrderActivityVo> listVo = new ArrayList<InfoServiceOrderActivityVo>();
		List<InfoServiceOrderActivityPo> listPo = infoServiceOrderActivityServ.queryInfoServiceOrderActivityByOrderNo(InfoServiceOrderActivityPo);  
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderActivityPo po : listPo){
				InfoServiceOrderActivityVo voTemp = new InfoServiceOrderActivityVo();
				BeanUtils.copyProperties(po, voTemp);
				listVo.add(voTemp);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
