package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderAgreementPo;

public interface InfoServiceOrderAgreementServ {
	
	/**
	 * 根据订单号查询服务订单协议表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderAgreementPo> queryInfoServiceOrderAgreementByOrderNo(InfoServiceOrderAgreementPo po) throws Exception;
	
	/**
	 * 根据商品订单号查询服务订单协议表
	 * @param po
	 * @return
	 * @throws Exception
	 */
//	public List<InfoServiceOrderAgreementPo> queryInfoServiceOrderAgreementByOfrOrderNo(InfoServiceOrderAgreementPo po) throws Exception;
	
	
	/**
	 * 根据服务订单号查询服务订单协议表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public InfoServiceOrderAgreementPo queryInfoServiceOrderAgreementByServOrderNo(InfoServiceOrderAgreementPo po) throws Exception;
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean  deleteInfoServiceOrderAgreementBySaleOrderNo(InfoServiceOrderAgreementPo po) throws Exception;

}
