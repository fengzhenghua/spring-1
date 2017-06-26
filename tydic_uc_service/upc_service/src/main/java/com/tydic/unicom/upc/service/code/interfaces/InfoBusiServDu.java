package com.tydic.unicom.upc.service.code.interfaces;

import java.util.List;

import com.tydic.unicom.upc.vo.code.InfoBusiVo;

public interface InfoBusiServDu {

	/**
	 * 根据busi_id查询业务系统信息
	 * @param infoBusiPo
	 * @return
	 */
	public InfoBusiVo queryByBusiId(InfoBusiVo infoBusiVo) throws Exception;
	
	/**
	 * 获取所有的业务应用数据
	 * @return
	 * @throws Exception
	 */
	public List<InfoBusiVo> queryAllInfoBusi() throws Exception;
}
