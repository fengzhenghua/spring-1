package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderProdMapPo;

public interface InfoServiceOrderProdMapServ {

	/*
	 * 操作服务订单产品列表
	 */
	//public List<InfoServiceOrderProdMapPo> queryInfoServiceOrderProdMapByOfrOrderNo(InfoServiceOrderProdMapPo po)throws Exception;
	
	/**
	 * 根据销售订单号查询服务订单产品列表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderProdMapPo> queryInfoServiceOrderProdMapByOrderNo(InfoServiceOrderProdMapPo po)throws Exception;
	
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderProdMapBySaleOrderNo(InfoServiceOrderProdMapPo po)throws Exception;
	
	/**
	 * 根据产品编码查询服务订单产品列表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderProdMapPo> queryInfoServiceOrderProdMapByProdCode(InfoServiceOrderProdMapPo po)throws Exception;
}
