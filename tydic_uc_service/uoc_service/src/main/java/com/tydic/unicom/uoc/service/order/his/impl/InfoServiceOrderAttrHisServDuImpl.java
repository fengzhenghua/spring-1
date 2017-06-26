package com.tydic.unicom.uoc.service.order.his.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderAttrHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderAttrHisServ;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderAttrHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderAttrHisVo;

@Service("InfoServiceOrderAttrHisServDu")
public class  InfoServiceOrderAttrHisServDuImpl implements  InfoServiceOrderAttrHisServDu{
	
Logger logger = Logger.getLogger( InfoServiceOrderAttrHisServDuImpl.class);
	
	@Autowired
	private  InfoServiceOrderAttrHisServ  infoServiceOrderAttrHisServ;
	
	@Override
	public List<InfoServiceOrderAttrHisVo> queryInfoServiceOrderAttrHisByOrderNo(
			InfoServiceOrderAttrHisVo vo)
					throws Exception {
		InfoServiceOrderAttrHisPo po = new  InfoServiceOrderAttrHisPo();
		List< InfoServiceOrderAttrHisVo> listVo = new ArrayList< InfoServiceOrderAttrHisVo>();
		BeanUtils.copyProperties(vo, po);
		List< InfoServiceOrderAttrHisPo> listPo = infoServiceOrderAttrHisServ.queryInfoServiceOrderAttrHisByOrderNo(po);
		if(listPo != null && listPo.size()>0){
			for( InfoServiceOrderAttrHisPo poTemp : listPo){
				InfoServiceOrderAttrHisVo ordVo = new  InfoServiceOrderAttrHisVo();
				BeanUtils.copyProperties(poTemp, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}


}
