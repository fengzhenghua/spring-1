package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderPackageHisPo;

public interface InfoServiceOrderPackageHisServ {
	/**
	 * 写服务订单套餐列表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoServiceOrderPackageHisPo(InfoServiceOrderPackageHisPo po)throws Exception;

	public List<InfoServiceOrderPackageHisPo> queryInfoServiceOrderPackageHisByOrderNo(InfoServiceOrderPackageHisPo po)throws Exception;
}
