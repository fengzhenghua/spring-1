package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderTerminalPo;

public interface InfoServiceOrderTerminalServ {
	
	/**
	 * 根据订单号查询服务订单终端表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderTerminalPo> queryInfoServiceOrderTerminalByOrderNo(InfoServiceOrderTerminalPo po)throws Exception;
	
	/**
	 * 根据商品订单号查询服务订单终端表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	//public List<InfoServiceOrderTerminalPo> queryInfoServiceOrderTerminalByOfrOrderNo(InfoServiceOrderTerminalPo po)throws Exception;
	
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderTerminalBySaleOrderNo(InfoServiceOrderTerminalPo po)throws Exception;

}
