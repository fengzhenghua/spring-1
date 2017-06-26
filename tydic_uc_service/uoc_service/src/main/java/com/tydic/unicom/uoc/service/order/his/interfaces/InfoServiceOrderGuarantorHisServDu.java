package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderGuarantorHisVo;

public interface InfoServiceOrderGuarantorHisServDu {

	
	public List<InfoServiceOrderGuarantorHisVo> queryInfoServiceOrderGuarantorHisByOrderNo(InfoServiceOrderGuarantorHisVo vo)throws Exception;
}
