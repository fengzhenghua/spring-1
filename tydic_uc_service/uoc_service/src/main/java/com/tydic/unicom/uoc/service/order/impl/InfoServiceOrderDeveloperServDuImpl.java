package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderDeveloperPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderDeveloperServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderDeveloperServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderDeveloperVo;
@Service("InfoServiceOrderDeveloperServDu")
public class InfoServiceOrderDeveloperServDuImpl implements InfoServiceOrderDeveloperServDu{

	@Autowired
	private InfoServiceOrderDeveloperServ infoServiceOrderDeveloperServ;

	@Override
	public List<InfoServiceOrderDeveloperVo> queryInfoServiceOrderDeveloperByOrderNo(
			InfoServiceOrderDeveloperVo vo) throws Exception {
		InfoServiceOrderDeveloperPo infoServiceOrderDeveloperPo = new InfoServiceOrderDeveloperPo();
		BeanUtils.copyProperties(vo, infoServiceOrderDeveloperPo);
		List<InfoServiceOrderDeveloperVo> listVo = new ArrayList<InfoServiceOrderDeveloperVo>();
		List<InfoServiceOrderDeveloperPo> listPo = infoServiceOrderDeveloperServ.queryInfoServiceOrderDeveloperByOrderNo(infoServiceOrderDeveloperPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderDeveloperPo po : listPo){
				InfoServiceOrderDeveloperVo ordVo = new InfoServiceOrderDeveloperVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
