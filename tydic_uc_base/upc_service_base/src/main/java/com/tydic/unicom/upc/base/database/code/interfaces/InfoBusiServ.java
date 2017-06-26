package com.tydic.unicom.upc.base.database.code.interfaces;

import java.util.List;

import com.tydic.unicom.upc.base.database.po.code.InfoBusiPo;

public interface InfoBusiServ {

	/**
	 * 根据busi_id查询业务系统信息
	 * @param infoBusiPo
	 * @return
	 */
	public InfoBusiPo queryByBusiId(InfoBusiPo infoBusiPo) throws Exception;
	
	
	/**
	 * 获取所有的业务应用数据
	 * @return
	 * @throws Exception
	 */
	public List<InfoBusiPo> queryAllInfoBusi() throws Exception;
}
