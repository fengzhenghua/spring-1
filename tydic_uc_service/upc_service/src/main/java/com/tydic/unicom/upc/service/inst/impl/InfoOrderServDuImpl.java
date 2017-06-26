package com.tydic.unicom.upc.service.inst.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.service.sequence.service.interfaces.SequenceServ;
import com.tydic.unicom.upc.base.database.inst.interfaces.InfoOrderServ;
import com.tydic.unicom.upc.base.database.po.inst.InfoOrderPo;
import com.tydic.unicom.upc.service.inst.interfaces.InfoOrderServDu;
import com.tydic.unicom.upc.vo.inst.InfoOrderVo;

public class InfoOrderServDuImpl implements InfoOrderServDu {

	@Autowired
	private InfoOrderServ infoOrderServ;
	
	@Autowired
	private SequenceServ sequenceServ;
	
	
	@Override
	public String addInfoOrder(InfoOrderVo infoOrderVo) {
		if(infoOrderVo.getOrder_id() == null || infoOrderVo.getOrder_id().equals("")){
			infoOrderVo.setOrder_id(genOrderId());
		}
		InfoOrderPo infoOrderPo = new InfoOrderPo();
		BeanUtils.copyProperties(infoOrderVo, infoOrderPo);
		
		infoOrderServ.addInfoOrder(infoOrderPo);
		
		return infoOrderVo.getOrder_id();
	}

	@Override
	public boolean updateInfoOrder(InfoOrderVo infoOrderVo) {
		InfoOrderPo infoOrderPo = new InfoOrderPo();
		BeanUtils.copyProperties(infoOrderVo, infoOrderPo);
		return infoOrderServ.updateInfoOrder(infoOrderPo);
	}

	@Override
	public InfoOrderVo queryInfoOrderByOrderId(InfoOrderVo infoOrderVo) {
		InfoOrderPo infoOrderPo = new InfoOrderPo();
		BeanUtils.copyProperties(infoOrderVo, infoOrderPo);
		InfoOrderPo po = infoOrderServ.queryInfoOrderByOrderId(infoOrderPo);
		if(po != null){
			InfoOrderVo vo = new InfoOrderVo();
			BeanUtils.copyProperties(po, vo);
			return vo;
		}
		else{
			return null;
		}
	}

	@Override
	public InfoOrderVo queryInfoOrderByOutOrderId(InfoOrderVo infoOrderVo) {
		InfoOrderPo infoOrderPo = new InfoOrderPo();
		BeanUtils.copyProperties(infoOrderVo, infoOrderPo);
		InfoOrderPo po = infoOrderServ.queryInfoOrderByOutOrderId(infoOrderPo);
		if(po != null){
			InfoOrderVo vo = new InfoOrderVo();
			BeanUtils.copyProperties(po, vo);
			return vo;
		}
		else{
			return null;
		}
	}
	
	
	/**
	 * 订单ID，时间+序列号八位+六位随机数
	 * @return
	 */
	private synchronized String genOrderId(){
		long seq = sequenceServ.genSequence("info_order");
		
		String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		Random random = new Random();
		int ran = random.nextInt(100000) + 100000;
		
		return datetime + seq + ran;
		
	}

	@Override
	public InfoOrderVo queryInfoOrderByPayOrderId(InfoOrderVo infoOrderVo) {
		InfoOrderPo infoOrderPo = new InfoOrderPo();
		BeanUtils.copyProperties(infoOrderVo, infoOrderPo);
		InfoOrderPo po = infoOrderServ.queryInfoOrderByPayOrderId(infoOrderPo);
		if(po != null){
			InfoOrderVo vo = new InfoOrderVo();
			BeanUtils.copyProperties(po, vo);
			return vo;
		}
		else{
			return null;
		}
	}

}
