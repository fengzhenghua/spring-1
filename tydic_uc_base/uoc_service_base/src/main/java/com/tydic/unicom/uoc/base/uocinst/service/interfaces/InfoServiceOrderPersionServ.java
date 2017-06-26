package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPersionPo;

public interface InfoServiceOrderPersionServ {

	//public List<InfoServiceOrderPersionPo> queryInfoServiceOrderPersionByOfrOrderNo(InfoServiceOrderPersionPo po)throws Exception;
	
	/**
	 * 根据订单号查询服务订单个客信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderPersionPo> queryInfoServiceOrderPersionByOrderNo(InfoServiceOrderPersionPo po)throws Exception;
	
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderPersionBySaleOrderNo(InfoServiceOrderPersionPo po)throws Exception;
}
