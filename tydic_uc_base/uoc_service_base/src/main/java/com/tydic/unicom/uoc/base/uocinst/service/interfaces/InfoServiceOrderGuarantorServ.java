package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderGuarantorPo;

public interface InfoServiceOrderGuarantorServ {
	
	/**
	 * 根据订单号查询服务订单担保人表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderGuarantorPo> queryInfoServiceOrderGuarantorByOrderNo(InfoServiceOrderGuarantorPo po)throws Exception;
	
	/**
	 * 根据商品订单号查询服务订单担保人表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	//public List<InfoServiceOrderGuarantorPo> queryInfoServiceOrderGuarantorByOfrOrderNo(InfoServiceOrderGuarantorPo po)throws Exception;
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderGuarantorBySaleOrderNo(InfoServiceOrderGuarantorPo po)throws Exception;

}
