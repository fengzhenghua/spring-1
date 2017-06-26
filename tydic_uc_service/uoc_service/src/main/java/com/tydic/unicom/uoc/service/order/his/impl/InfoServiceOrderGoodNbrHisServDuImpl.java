package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderGoodNbrHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderGoodNbrHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderGoodNbrHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderGoodNbrHisVo;

@Service("InfoServiceOrderGoodNbrHisServDu")
public class  InfoServiceOrderGoodNbrHisServDuImpl implements  InfoServiceOrderGoodNbrHisServDu{

	@Autowired
	private  InfoServiceOrderGoodNbrHisServ  infoServiceOrderGoodNbrHisServ;

	@Override
	public List<InfoServiceOrderGoodNbrHisVo> queryInfoServiceOrderGoodNbrHisByOrderNo(
			InfoServiceOrderGoodNbrHisVo vo) throws Exception {
		InfoServiceOrderGoodNbrHisPo  InfoServiceOrderGoodNbrHisPo = new  InfoServiceOrderGoodNbrHisPo();
		BeanUtils.copyProperties(vo,  InfoServiceOrderGoodNbrHisPo);
		List< InfoServiceOrderGoodNbrHisVo> listVo = new ArrayList< InfoServiceOrderGoodNbrHisVo>();
		List< InfoServiceOrderGoodNbrHisPo> listPo =  infoServiceOrderGoodNbrHisServ.queryInfoServiceOrderGoodNbrHisByOrderNo( InfoServiceOrderGoodNbrHisPo);
		if(listPo != null && listPo.size()>0){
			for( InfoServiceOrderGoodNbrHisPo po : listPo){
				InfoServiceOrderGoodNbrHisVo ordVo = new  InfoServiceOrderGoodNbrHisVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
