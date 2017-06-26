package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoDeliverOrderPo;


public interface InfoDeliverOrderServ {

	public InfoDeliverOrderPo getInfoDeliverOrderByPayDeliverOrderNo(InfoDeliverOrderPo po) throws Exception;

	public boolean updateInfoDeliverOrder(InfoDeliverOrderPo po) throws Exception; 

	public List<InfoDeliverOrderPo> queryInfoDeliverOrderBySaleOrderNo(InfoDeliverOrderPo po)throws Exception;
	
	/**
	 * 根据交付订单号删除记录
	 * @param po
	 * @return
	 */
	public boolean deleteInfoDeliverOrderByPayDeliverOrderNo(InfoDeliverOrderPo po) throws Exception;
	
	public boolean create(InfoDeliverOrderPo po) throws Exception;
	/**
	 * 获取全部交付订单信息
	 */
	public List<InfoDeliverOrderPo> queryInfoDeliverOrderAll(InfoDeliverOrderPo po) throws Exception;
	/**
	 * 根据contact_tel查询出交付订单信息 
	 */
	public List<InfoDeliverOrderPo> queryInfoDeliverOrderByContactTel(InfoDeliverOrderPo po) throws Exception;
	
}
