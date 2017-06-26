
package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.GetServOrderNoPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPo;

public interface InfoServiceOrderServ {

	public boolean create(InfoServiceOrderPo infoServiceOrderPo) ;

	public boolean update(InfoServiceOrderPo infoServiceOrderPo);

	public boolean delete(InfoServiceOrderPo infoServiceOrderPo);

	public List<InfoServiceOrderPo> queryInfoServiceOrderByPo(InfoServiceOrderPo infoServiceOrderPo);
	/*
	 * 根据ServOrderNo查询服务订单表
	 */
	public InfoServiceOrderPo getInfoServiceOrderByServOrderNo(InfoServiceOrderPo po) throws Exception;

	/*
	 * 根据订单号查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderPo> queryInfoServiceOrderByOrderNo(InfoServiceOrderPo po) throws Exception;
	/*
	 * 根据商品订单查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderPo> queryInfoServiceOrderByOfrOrderNo(InfoServiceOrderPo po) throws Exception;
	/*
	 * 根据销售订单查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderPo> queryInfoServiceOrderBySaleOrderNo(InfoServiceOrderPo po) throws Exception;

	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */

	public boolean deleteInfoServiceOrderBySaleOrderNo(InfoServiceOrderPo po) throws Exception;
	/**
	 * 关联查询服务订单表和服务订单SIM卡信息表
	 */
	public List<InfoServiceOrderPo> queryInfoServiceOrderAndSim(GetServOrderNoPo po) throws Exception;
}

