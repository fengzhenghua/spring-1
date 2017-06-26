package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderProdMapHisVo;

public interface InfoServiceOrderProdMapHisServDu {


	public List<InfoServiceOrderProdMapHisVo> queryInfoServiceOrderProdMapHisByOrderNo(InfoServiceOrderProdMapHisVo vo)throws Exception;

	public List<InfoServiceOrderProdMapHisVo> queryInfoServiceOrderProdHisMapByVo(InfoServiceOrderProdMapHisVo vo)throws Exception;
}
