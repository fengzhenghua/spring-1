package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderGoodNbrHisVo;

public interface InfoServiceOrderGoodNbrHisServDu {

	
	public List<InfoServiceOrderGoodNbrHisVo> queryInfoServiceOrderGoodNbrHisByOrderNo(InfoServiceOrderGoodNbrHisVo vo) throws Exception;

}
