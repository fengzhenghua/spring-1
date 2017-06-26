package com.tydic.unicom.upc.base.database.code.interfaces;

import java.util.List;

import com.tydic.unicom.upc.base.database.po.code.SystemBusiParaPo;

public interface SystemBusiParaServ {

	/**
	 * 根据ParaGroup获取参数数据
	 * 如果传了para_code则会过滤
	 * @param systemBusiParaPo
	 * @return
	 */
	public List<SystemBusiParaPo> queryByParaGroup(SystemBusiParaPo systemBusiParaPo);
}
