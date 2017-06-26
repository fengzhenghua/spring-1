package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderGoodNbrPo;

public interface InfoServiceOrderGoodNbrServ {
	
	/**
	 * 根据订单号查询服务订单靓号信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderGoodNbrPo> queryInfoServiceOrderGoodNbrByOrderNo(InfoServiceOrderGoodNbrPo po)throws Exception;
	
	/**
	 * 根据商品订单号查询服务订单靓号信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	//public List<InfoServiceOrderGoodNbrPo> queryInfoServiceOrderGoodNbrByOfrOrderNo(InfoServiceOrderGoodNbrPo po)throws Exception;
	
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderGoodNbrBySaleOrderNo(InfoServiceOrderGoodNbrPo po)throws Exception;

}
