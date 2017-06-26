package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderDeveloperPo;

public interface InfoServiceOrderDeveloperServ {
	
	/**
	 * 根据订单号查询服务订单发展人表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderDeveloperPo> queryInfoServiceOrderDeveloperByOrderNo(InfoServiceOrderDeveloperPo po)throws Exception;
	
	/**
	 * 根据商品订单号查询服务订单发展人表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	//public List<InfoServiceOrderDeveloperPo> queryInfoServiceOrderDeveloperByOfrOrderNo(InfoServiceOrderDeveloperPo po)throws Exception;
	
	/**
	 * 根据服务订单号查询服务订单发展人表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderDeveloperPo> queryInfoServiceOrderDeveloperByServOrderNo(InfoServiceOrderDeveloperPo po)throws Exception;
	
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderDeveloperBySaleOrderNo(InfoServiceOrderDeveloperPo po)throws Exception;

}
