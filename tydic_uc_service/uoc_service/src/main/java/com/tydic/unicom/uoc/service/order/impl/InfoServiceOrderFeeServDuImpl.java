package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderFeePo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderFeeServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderFeeServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderFeeVo;
@Service("InfoServiceOrderFeeServDu")
public class InfoServiceOrderFeeServDuImpl implements InfoServiceOrderFeeServDu {

	Logger logger = Logger.getLogger(InfoServiceOrderFeeServDuImpl.class);
	@Autowired
	private InfoServiceOrderFeeServ infoServiceOrderFeeServ;
	@Override
	public List<InfoServiceOrderFeeVo> queryInfoServiceOrderFeeBySaleOrderNo(
			InfoServiceOrderFeeVo vo) throws Exception {

		logger.info("----------------queryInfoServiceOrderFeeBySaleOrderNo-----------------------");

		InfoServiceOrderFeePo infoServiceOrderFeePo =new InfoServiceOrderFeePo();
		BeanUtils.copyProperties(vo,infoServiceOrderFeePo);
		List<InfoServiceOrderFeePo> list =infoServiceOrderFeeServ.queryInfoServiceOrderFeeBySaleOrderNo(infoServiceOrderFeePo);
		List<InfoServiceOrderFeeVo> listVo =new ArrayList<InfoServiceOrderFeeVo>();
		if(list !=null){
			for(InfoServiceOrderFeePo po:list ){
				InfoServiceOrderFeeVo infoServiceOrderFeeVo =new InfoServiceOrderFeeVo();
				BeanUtils.copyProperties(po,infoServiceOrderFeeVo);
				listVo.add(infoServiceOrderFeeVo);
			}
		}
		logger.info("----------------queryInfoServiceOrderFeeBySaleOrderNo-------------end----------");
		return listVo;
	}
	@Override
	public List<InfoServiceOrderFeeVo> queryInfoServiceOrderFeeByOrderNo(
			InfoServiceOrderFeeVo vo) throws Exception {
		InfoServiceOrderFeePo infoServiceOrderFeePo =new InfoServiceOrderFeePo();
		BeanUtils.copyProperties(vo,infoServiceOrderFeePo);
		List<InfoServiceOrderFeeVo> listVo =new ArrayList<InfoServiceOrderFeeVo>();
		List<InfoServiceOrderFeePo> listPo = infoServiceOrderFeeServ.queryInfoServiceOrderFeeByOrderNo(infoServiceOrderFeePo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderFeePo po : listPo){
				InfoServiceOrderFeeVo ordVo = new InfoServiceOrderFeeVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
		
	}
	@Override
	public List<InfoServiceOrderFeeVo> queryInfoServiceOrderFeeByServOrderNo(
			InfoServiceOrderFeeVo vo) throws Exception {
		InfoServiceOrderFeePo infoServiceOrderFeePo =new InfoServiceOrderFeePo();
		BeanUtils.copyProperties(vo,infoServiceOrderFeePo);
		List<InfoServiceOrderFeeVo> listVo =new ArrayList<InfoServiceOrderFeeVo>();
		List<InfoServiceOrderFeePo> listPo = infoServiceOrderFeeServ.queryInfoServiceOrderFeeByServOrderNo(infoServiceOrderFeePo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderFeePo po : listPo){
				InfoServiceOrderFeeVo ordVo = new InfoServiceOrderFeeVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

}
