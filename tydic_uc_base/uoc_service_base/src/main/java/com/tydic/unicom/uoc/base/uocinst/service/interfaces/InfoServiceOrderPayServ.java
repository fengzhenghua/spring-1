package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPayPo;

public interface InfoServiceOrderPayServ {
	
	/**
	 * 根据订单号查询服务订单收费详情表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderPayPo> queryInfoServiceOrderPayByOrderNo(InfoServiceOrderPayPo po)throws Exception;
	
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderPayBySaleOrderNo(InfoServiceOrderPayPo po)throws Exception;
	
	/**
	 * 根据商品订单号查询服务订单收费详情表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	//public List<InfoServiceOrderPayPo> queryInfoServiceOrderPayByOfrOrderNo(InfoServiceOrderPayPo po)throws Exception;

}
