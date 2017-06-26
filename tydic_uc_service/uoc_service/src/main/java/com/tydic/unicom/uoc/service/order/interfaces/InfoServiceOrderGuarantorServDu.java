package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderGuarantorVo;


public interface InfoServiceOrderGuarantorServDu {

	//public List<InfoServiceOrderGuarantorVo> queryInfoServiceOrderGuarantorByOfrOrderNo(InfoServiceOrderGuarantorVo vo) throws Exception;

	public List<InfoServiceOrderGuarantorVo> queryInfoServiceOrderGuarantorByOrderNo(InfoServiceOrderGuarantorVo vo)throws Exception;
}
