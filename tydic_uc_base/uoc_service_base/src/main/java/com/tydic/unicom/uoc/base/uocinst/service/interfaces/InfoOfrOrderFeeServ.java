package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderFeePo;

public interface InfoOfrOrderFeeServ {
	
	/**
	 * 根据订单号查询商品订单费用详情表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoOfrOrderFeePo> queryInfoOfrOrderFeeByOrderNo(InfoOfrOrderFeePo po)throws Exception;
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoOfrOrderFeeBySaleOrderNo(InfoOfrOrderFeePo po)throws Exception;

}
