package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderHisVo;

public interface InfoServiceOrderHisServDu {

	/*
	 * 根据订单查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderHisVo> queryInfoServiceOrderHisByOrderNo(InfoServiceOrderHisVo vo) throws Exception;
	/*
	 * 根据oper_no,depart_no,oper_code,province_code,area_code查询对应的服务订单（多条）
	 */
	public List<InfoServiceOrderHisVo> queryInfoServiceOrderHisByVo(InfoServiceOrderHisVo vo) throws Exception;
}