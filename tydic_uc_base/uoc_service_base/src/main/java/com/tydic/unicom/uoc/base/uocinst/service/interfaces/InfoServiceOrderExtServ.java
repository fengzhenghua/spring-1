package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderExtPo;

public interface InfoServiceOrderExtServ {
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInfoServiceOrderExtBySaleOrderNo(InfoServiceOrderExtPo po)throws Exception;
	
	/**
	 * 根据订单号查询
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoServiceOrderExtPo> queryInfoServiceOrderExtByOrderNo(InfoServiceOrderExtPo po)throws Exception;
	
	/**
	 * 新增记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean insertInfoServiceOrderExt(InfoServiceOrderExtPo po)throws Exception;
	
	/**
	 * 更新记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean updateInfoServiceOrderExt(InfoServiceOrderExtPo po)throws Exception;

}
