package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderCollectionPo;

public interface InfoServiceOrderCollectionServ {
	/**
	 * 根据订单号查询服务订单银行托收表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderCollectionPo> queryInfoServiceOrderCollectionByOrderNo(InfoServiceOrderCollectionPo po)throws Exception;
	
	/**
	 * 根据商品订单号查询服务订单银行托收表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	//public List<InfoServiceOrderCollectionPo> queryInfoServiceOrderCollectionByOfrOrderNo(InfoServiceOrderCollectionPo po)throws Exception;
	
	/**
	 * 根据服务订单号查询服务订单银行托收表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderCollectionPo> queryInfoServiceOrderCollectionByServOrderNo(InfoServiceOrderCollectionPo po)throws Exception;
	
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderCollectionBySaleOrderNo(InfoServiceOrderCollectionPo po)throws Exception;

}
