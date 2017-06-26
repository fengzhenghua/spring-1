package com.tydic.unicom.upc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.upc.base.database.code.interfaces.SystemBusiParaServ;
import com.tydic.unicom.upc.base.database.po.code.SystemBusiParaPo;
import com.tydic.unicom.upc.service.code.interfaces.SystemBusiParaServDu;
import com.tydic.unicom.upc.vo.code.SystemBusiParaVo;

public class SystemBusiParaServDuImpl implements SystemBusiParaServDu {

	@Autowired
	private SystemBusiParaServ systemBusiParaServ;
	
	@Override
	public List<SystemBusiParaVo> queryByParaGroup(String paraGroup) throws Exception{
		
		SystemBusiParaPo systemBusiParaPo = new SystemBusiParaPo();
		systemBusiParaPo.setParam_group(paraGroup);
		
		List<SystemBusiParaPo> poList = systemBusiParaServ.queryByParaGroup(systemBusiParaPo);
		if(poList != null && poList.size() > 0){
			
			List<SystemBusiParaVo> voList = new ArrayList<>();
			for(SystemBusiParaPo po : poList){
				SystemBusiParaVo vo = new SystemBusiParaVo();
				BeanUtils.copyProperties(vo, po);
				voList.add(vo);
			}
			
			return voList;
		}
		
		return null;
	}

	@Override
	public SystemBusiParaVo queryByParaGroupAndCode(String paraGroup, String paraCode)  throws Exception{
		SystemBusiParaPo systemBusiParaPo = new SystemBusiParaPo();
		systemBusiParaPo.setParam_group(paraGroup);
		systemBusiParaPo.setParam_code(paraCode);
		
		List<SystemBusiParaPo> poList = systemBusiParaServ.queryByParaGroup(systemBusiParaPo);
		if(poList != null && poList.size() > 0){
			
			SystemBusiParaVo vo = new SystemBusiParaVo();
			BeanUtils.copyProperties(vo, poList.get(0));
		
			return vo;
		}
		
		return null;
	}

	@Override
	public SystemBusiParaVo queryByParaCode(String paraCode)  throws Exception{
		return queryByParaGroupAndCode(paraCode, paraCode);
	}

}
