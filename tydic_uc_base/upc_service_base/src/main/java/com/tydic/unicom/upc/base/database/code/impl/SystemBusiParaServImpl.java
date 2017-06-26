package com.tydic.unicom.upc.base.database.code.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.base.database.code.interfaces.SystemBusiParaServ;
import com.tydic.unicom.upc.base.database.po.code.SystemBusiParaPo;

@Service("SystemBusiParaServ")
public class SystemBusiParaServImpl implements SystemBusiParaServ {

	@Override
	public List<SystemBusiParaPo> queryByParaGroup(SystemBusiParaPo systemBusiParaPo) {
		if(systemBusiParaPo.getParam_group() == null || systemBusiParaPo.getParam_group().equals("")){
			throw new IllegalArgumentException("找不到param_group的值!");
		}
		
		Condition condition = Condition.build("queryByParaGroup").filter(systemBusiParaPo.convertThis2Map());
		return S.get(SystemBusiParaPo.class).query(condition);
	}

}
