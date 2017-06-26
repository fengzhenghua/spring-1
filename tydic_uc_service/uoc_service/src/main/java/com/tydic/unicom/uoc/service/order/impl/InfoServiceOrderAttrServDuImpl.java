package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderAttrPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderAttrServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderAttrServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderAttrVo;

@Service("InfoServiceOrderAttrServDu")
public class InfoServiceOrderAttrServDuImpl implements InfoServiceOrderAttrServDu{
	
Logger logger = Logger.getLogger(InfoServiceOrderAttrServDuImpl.class);
	
	@Autowired
	private InfoServiceOrderAttrServ infoServiceOrderAttrServ;
	
	@Override
	public boolean addInfoServiceOrderAttrList(List<InfoServiceOrderAttrVo> infoServiceOrderAttrVoList) throws Exception{
		
		if (null == infoServiceOrderAttrVoList) {
			return false;
		}

		for (int i = 0; i < infoServiceOrderAttrVoList.size(); i++) {
			InfoServiceOrderAttrPo res = new InfoServiceOrderAttrPo();
			InfoServiceOrderAttrVo infoServiceOrderAttrVo = new InfoServiceOrderAttrVo();
			/* 循环得到infoOrderAttr对象 */
			infoServiceOrderAttrVo = infoServiceOrderAttrVoList.get(i);
			BeanUtils.copyProperties(infoServiceOrderAttrVo, res);
			/* 判断订单属性表里面是否有该条数据 */
			List<InfoServiceOrderAttrPo> infoServiceOrderAttrVoTemp = infoServiceOrderAttrServ.getInfoServiceOrderAttrPo(res.getServ_order_no());
			/* 如果没有则插入 */
			if (null == infoServiceOrderAttrVoTemp) {
				infoServiceOrderAttrServ.addInfoServiceOrderAttr(res);
			}

		}
		return true;
	}

	@Override
	public List<InfoServiceOrderAttrVo> queryInfoServiceOrderAttrByOrderNo(
			InfoServiceOrderAttrVo infoServiceOrderAttrVo) throws Exception {
		InfoServiceOrderAttrPo po = new InfoServiceOrderAttrPo();
		List<InfoServiceOrderAttrVo> listVo = new ArrayList<InfoServiceOrderAttrVo>();
		BeanUtils.copyProperties(infoServiceOrderAttrVo, po);
		List<InfoServiceOrderAttrPo> listPo = infoServiceOrderAttrServ.queryInfoServiceOrderAttrByOrderNo(po);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderAttrPo poTemp : listPo){
				InfoServiceOrderAttrVo ordVo = new InfoServiceOrderAttrVo();
				BeanUtils.copyProperties(poTemp, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
		
	}
	

}
