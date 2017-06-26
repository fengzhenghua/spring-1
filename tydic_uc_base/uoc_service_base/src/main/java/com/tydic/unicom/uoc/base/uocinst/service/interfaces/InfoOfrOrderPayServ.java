package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderPayPo;

public interface InfoOfrOrderPayServ {
	
	/**
	 * 根据订单号查询商品订单收费详情表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoOfrOrderPayPo> queryInfoOfrOrderPayByOrderNo(InfoOfrOrderPayPo po)throws Exception;
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean deleteInfoOfrOrderPayBySaleOrderNo(InfoOfrOrderPayPo po)throws Exception;

}
