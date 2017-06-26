package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderCollectionHisVo;

public interface InfoServiceOrderCollectionHisServDu {

	public List<InfoServiceOrderCollectionHisVo> queryInfoServiceOrderCollectionHisByOrderNo(InfoServiceOrderCollectionHisVo vo)throws Exception;
}
