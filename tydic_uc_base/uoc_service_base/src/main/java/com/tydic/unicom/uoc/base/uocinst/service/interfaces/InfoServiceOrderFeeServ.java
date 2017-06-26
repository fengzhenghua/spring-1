package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderFeePo;

public interface InfoServiceOrderFeeServ {

	/*
	 * 根据销售订单查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderFeePo> queryInfoServiceOrderFeeBySaleOrderNo(InfoServiceOrderFeePo po) throws Exception;
	
	/*
	 * 根据商品订单查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderFeePo> queryInfoServiceOrderFeeByOfrOrderNo(InfoServiceOrderFeePo po) throws Exception;
	
	/*
	 * 根据服务订单查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderFeePo> queryInfoServiceOrderFeeByServOrderNo(InfoServiceOrderFeePo po) throws Exception;
	
	public List<InfoServiceOrderFeePo> queryInfoServiceOrderFeeByOrderNo(InfoServiceOrderFeePo po) throws Exception;
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderFeeBySaleOrderNo(InfoServiceOrderFeePo po) throws Exception;
	
}
