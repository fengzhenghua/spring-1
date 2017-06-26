package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderM165Po;

public interface InfoServiceOrderM165Serv {
	
	/**
	 * 根据订单号查询服务订单宽带信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderM165Po> queryInfoServiceOrderM165ByOrderNo(InfoServiceOrderM165Po po)throws Exception;
	
	/**
	 * 根据商品订单号查询服务订单宽带信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	//public List<InfoServiceOrderM165Po> queryInfoServiceOrderM165ByOfrOrderNo(InfoServiceOrderM165Po po)throws Exception;
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderM165BySaleOrderNo(InfoServiceOrderM165Po po)throws Exception;

}
