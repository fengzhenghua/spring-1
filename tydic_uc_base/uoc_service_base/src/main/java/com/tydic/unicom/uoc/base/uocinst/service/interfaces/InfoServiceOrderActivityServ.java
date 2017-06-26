package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderActivityPo;

public interface InfoServiceOrderActivityServ {

	/**
	 * 根据商品订单号查询服务订单优惠活动
	 */
//	public List<InfoServiceOrderActivityPo> queryInfoServiceOrderActivityByOfrOrderNo(InfoServiceOrderActivityPo po)throws Exception;
	
	/**
	 * 根据服务订单号查询服务订单优惠活动
	 */
	public List<InfoServiceOrderActivityPo> queryInfoServiceOrderActivityByServOrderNo(InfoServiceOrderActivityPo po)throws Exception;
	
	/**
	 * 根据订单号查询服务订单优惠活动
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public List<InfoServiceOrderActivityPo> queryInfoServiceOrderActivityByOrderNo(InfoServiceOrderActivityPo po)throws Exception;
	
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean deleteInfoServiceOrderActivityBySaleOrderNo(InfoServiceOrderActivityPo po)throws Exception;
}
