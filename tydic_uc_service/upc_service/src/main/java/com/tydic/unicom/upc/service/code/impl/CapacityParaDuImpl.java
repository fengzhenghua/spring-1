package com.tydic.unicom.upc.service.code.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.upc.base.database.code.interfaces.CapacityParaServ;
import com.tydic.unicom.upc.base.database.po.code.CapacityParaPo;
import com.tydic.unicom.upc.base.database.po.code.SystemBusiParaPo;
import com.tydic.unicom.upc.service.code.interfaces.CapacityParaDu;
import com.tydic.unicom.upc.vo.code.CapacityParaVo;

public class CapacityParaDuImpl implements CapacityParaDu {

	private static final Logger logger = Logger.getLogger(CapacityParaDuImpl.class);
	@Autowired
	private CapacityParaServ capacityParaServ;
	@Override
	public CapacityParaVo getCapacityPara(String busi_id,String aopName) {
		/*if(vo.getBusi_id() == null||"".equals(vo.getBusi_id())){
			throw new IllegalArgumentException("找不到busi_id");
		}
		if(vo.getAopname() == null||"".equals(vo.getAopname())){
			throw new IllegalArgumentException("找不到aopname");
		}*/
		try{
			CapacityParaPo po1 = new CapacityParaPo();
		logger.info("-----------------------------------------");
		logger.info("-----------------------------------------");
		logger.info("DUImplvo = " + busi_id);
		
		//BeanUtils.copyProperties(vo, po);
		
		CapacityParaPo po = capacityParaServ.getCapacityPara(busi_id,aopName);
		if(po != null){
			CapacityParaVo cpVo= new CapacityParaVo(); 
			BeanUtils.copyProperties(po, cpVo);
			return cpVo;
		}
		}catch(Exception e){
			logger.info( " 错误信息 = " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
