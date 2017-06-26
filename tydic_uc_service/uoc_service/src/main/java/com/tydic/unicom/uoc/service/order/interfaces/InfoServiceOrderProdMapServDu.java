package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderProdMapVo;

public interface InfoServiceOrderProdMapServDu {

	//public List<InfoServiceOrderProdMapVo> queryInfoServiceOrderProdMapByOfrOrderNo(InfoServiceOrderProdMapVo vo) throws Exception;

	public List<InfoServiceOrderProdMapVo> queryInfoServiceOrderProdMapByOrderNo(InfoServiceOrderProdMapVo vo)throws Exception;

	public List<InfoServiceOrderProdMapVo> queryInfoServiceOrderProdMapByVo(InfoServiceOrderProdMapVo vo)throws Exception;
}
