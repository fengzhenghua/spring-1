package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderCollectionPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderCollectionServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderCollectionServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderCollectionVo;
@Service("InfoServiceOrderCollectionServDu")
public class InfoServiceOrderCollectionServDuImpl implements InfoServiceOrderCollectionServDu{

	@Autowired
	private InfoServiceOrderCollectionServ infoServiceOrderCollectionServ;

	@Override
	public List<InfoServiceOrderCollectionVo> queryInfoServiceOrderCollectionByOrderNo(
			InfoServiceOrderCollectionVo vo) throws Exception {
		InfoServiceOrderCollectionPo infoServiceOrderCollectionPo = new InfoServiceOrderCollectionPo();
		BeanUtils.copyProperties(vo, infoServiceOrderCollectionPo);
		List<InfoServiceOrderCollectionVo> listVo = new ArrayList<InfoServiceOrderCollectionVo>();
		List<InfoServiceOrderCollectionPo> listPo = infoServiceOrderCollectionServ.queryInfoServiceOrderCollectionByOrderNo(infoServiceOrderCollectionPo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderCollectionPo po : listPo){
				InfoServiceOrderCollectionVo ordVo = new InfoServiceOrderCollectionVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
