package com.tydic.unicom.uoc.service.order.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoOrderCancelPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOrderCancelServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOrderCancelServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoOrderCancelVo;

@Service("InfoOrderCancelServDu")
public class InfoOrderCancelServDuImpl implements InfoOrderCancelServDu{
	Logger logger = Logger.getLogger(InfoOrderCancelServDuImpl.class);

	@Autowired
	private InfoOrderCancelServ infoOrderCancelServ;

	@Override
	public boolean createInfoOrderCancel(InfoOrderCancelVo vo) throws Exception {
		logger.info("----------------createInfoOrderCancel-----------------------");
		InfoOrderCancelPo po = new InfoOrderCancelPo();
		BeanUtils.copyProperties(vo, po);
		return infoOrderCancelServ.create(po);
	}

	@Override
	public boolean updateInfoOrderCancel(InfoOrderCancelVo vo) throws Exception {
		logger.info("----------------updateInfoOrderCancel-----------------------");
		InfoOrderCancelPo po = new InfoOrderCancelPo();
		BeanUtils.copyProperties(vo, po);
		return infoOrderCancelServ.updateInfoOrderCancel(po);
	}

	@Override
	public InfoOrderCancelVo queryInfoOrderCancel(InfoOrderCancelVo vo)
			throws Exception {
		logger.info("----------------queryInfoOrderCancel-----------------------");
		InfoOrderCancelPo po = new InfoOrderCancelPo();
		BeanUtils.copyProperties(vo, po);
		InfoOrderCancelPo infoOrderCancelPo = infoOrderCancelServ.queryInfoOrderCancel(po);
		if(infoOrderCancelPo != null){
			InfoOrderCancelVo infoOrderCancelVo = new InfoOrderCancelVo();
			BeanUtils.copyProperties(infoOrderCancelPo, infoOrderCancelVo);
			return infoOrderCancelVo;
		}else{
			return null;
		}
	}

}
