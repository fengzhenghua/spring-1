package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderFeeVo;

public interface InfoServiceOrderFeeServDu {

	/*
	 * 根据销售订单查询对应的服务订单费用详情表（多条）
	 */
	public List<InfoServiceOrderFeeVo> queryInfoServiceOrderFeeBySaleOrderNo(InfoServiceOrderFeeVo vo) throws Exception;

	public List<InfoServiceOrderFeeVo> queryInfoServiceOrderFeeByOrderNo(InfoServiceOrderFeeVo vo) throws Exception;
	
	public List<InfoServiceOrderFeeVo> queryInfoServiceOrderFeeByServOrderNo(InfoServiceOrderFeeVo vo) throws Exception;
	
	
}
