package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderAgreementVo;

public interface InfoServiceOrderAgreementServDu {

	public List<InfoServiceOrderAgreementVo> queryInfoServiceOrderAgreementByOrderNo(InfoServiceOrderAgreementVo vo)throws Exception;
}
