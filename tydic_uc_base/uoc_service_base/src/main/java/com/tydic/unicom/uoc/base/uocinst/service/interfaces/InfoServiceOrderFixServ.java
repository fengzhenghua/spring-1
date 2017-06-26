package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderFixPo;

public interface InfoServiceOrderFixServ {
	
	/**
	 * 根据订单号查询服务订单固网信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderFixPo> queryInfoServiceOrderFixByOrderNo(InfoServiceOrderFixPo po)throws Exception;
	
	/**
	 * 根据商品订单号查询服务订单固网信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	//public List<InfoServiceOrderFixPo> queryInfoServiceOrderFixByOfrOrderNo(InfoServiceOrderFixPo po)throws Exception;
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderFixBySaleOrderNo(InfoServiceOrderFixPo po)throws Exception;

}
