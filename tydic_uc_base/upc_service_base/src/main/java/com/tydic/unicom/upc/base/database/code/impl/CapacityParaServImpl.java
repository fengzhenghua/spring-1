package com.tydic.unicom.upc.base.database.code.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.base.database.code.interfaces.CapacityParaServ;
import com.tydic.unicom.upc.base.database.po.code.CapacityParaPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("CapacityParaServ")
public class CapacityParaServImpl extends BaseServImpl<CapacityParaPo>  implements CapacityParaServ{

	private static final Logger logger = Logger.getLogger(CapacityParaServImpl.class);

	@Override
	public CapacityParaPo getCapacityPara(String busi_id,String aopName) {

		logger.info("---------------------------------");
		logger.info("---------------------------------");
		logger.info("CapacityParaServImpl po = " + busi_id + aopName);
		
		CapacityParaPo po = new CapacityParaPo();
		po.setAopname(aopName);
		po.setBusi_id(busi_id);
		if(po.getBusi_id() == null || "".equals(po.getBusi_id())){
			throw new IllegalArgumentException("找不到busi_id");
		}
		if(po.getAopname() == null || "".equals(po.getAopname())){
			throw new IllegalArgumentException("找不到busi_id");
		}
		
		Condition condition = Condition.build("queryCapacityPara").filter(po.convertThis2Map());
		return S.get(CapacityParaPo.class).queryFirst(condition);
	}

}
