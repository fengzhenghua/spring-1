package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPackagePo;

public interface InfoServiceOrderPackageServ {
	
	/**
	 * 根据订单号查询服务订单套餐列表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderPackagePo> queryInfoServiceOrderPackageByOrderNo(InfoServiceOrderPackagePo po)throws Exception;
	
	/**
	 * 根据商品订单号查询服务订单套餐列表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	//public List<InfoServiceOrderPackagePo> queryInfoServiceOrderPackageByOfrOrderNo(InfoServiceOrderPackagePo po)throws Exception;
	
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderPackageBySaleOrderNo(InfoServiceOrderPackagePo po)throws Exception;

}
