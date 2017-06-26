package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;

public interface InfoServiceOrderServDu {

	public boolean updateInfoServiceOrder(InfoServiceOrderVo vo) throws Exception;

	public boolean deleteInfoServiceOrder(InfoServiceOrderVo vo) throws Exception;

	public boolean createInfoServiceOrder(InfoServiceOrderVo vo) throws Exception;
	/*
	 * 根据ServOrderNo查询服务订单表
	 */
	public InfoServiceOrderVo getInfoServiceOrderByServOrderNo(InfoServiceOrderVo vo) throws Exception;
	/*
	 * 根据商品订单查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderVo> queryInfoServiceOrderByOrderNo(InfoServiceOrderVo vo) throws Exception;
	/*
	 * 根据商品订单查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderVo> queryInfoServiceOrderByOfrOrderNo(InfoServiceOrderVo vo) throws Exception;
	/*
	 * 根据销售订单查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderVo> queryInfoServiceOrderBySaleOrderNo(InfoServiceOrderVo vo) throws Exception;
	/*
	 * 根据oper_no,depart_no,oper_code,province_code,area_code查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderVo> queryInfoServiceOrderByVo(InfoServiceOrderVo vo) throws Exception;
}