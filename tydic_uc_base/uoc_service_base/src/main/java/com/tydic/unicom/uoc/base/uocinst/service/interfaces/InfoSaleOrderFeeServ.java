package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderFeePo;

public interface InfoSaleOrderFeeServ {
	/**
	 * 通过销售订单号查询销售订单费用信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public InfoSaleOrderFeePo getInfoSaleOrderFeePoBySaleOrderNo(InfoSaleOrderFeePo po)throws Exception;

	/*
	 * 创建销售订单费用信息表
	 */
	public boolean createInfoSaleOrderFee(InfoSaleOrderFeePo po)throws Exception;
	
	/**
	 * 通过销售订单号删除表记录
	 * @param po
	 * @return
	 */
	public boolean delete(InfoSaleOrderFeePo po);
	
	/*
	 * 按支付订单查  销售订单费用信息表
	 */
	public List<InfoSaleOrderFeePo> queryInfoSaleOrderFeeByPayOrder(InfoPayOrderPo po)throws Exception;
}
