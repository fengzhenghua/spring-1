package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;


import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderPersionVo;

public interface InfoServiceOrderPersionServDu {

	//public List<InfoServiceOrderPersionVo> queryInfoServiceOrderPersionByOfrOrderNo(InfoServiceOrderPersionVo vo) throws Exception;

	public List<InfoServiceOrderPersionVo> queryInfoServiceOrderPersionByOrderNo(InfoServiceOrderPersionVo vo)throws Exception;
}
