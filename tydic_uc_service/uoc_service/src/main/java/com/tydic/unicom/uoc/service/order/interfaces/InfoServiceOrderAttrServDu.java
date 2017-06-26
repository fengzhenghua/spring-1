package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderAttrVo;

public interface InfoServiceOrderAttrServDu {
	
	/**
	 * 更新服务订单属性表
	 * 
	 * @param infoServiceOrderAttrVoList
	 * @return boolean
	 * @throws Exception
	 */
	
	public boolean addInfoServiceOrderAttrList(List<InfoServiceOrderAttrVo> infoServiceOrderAttrVoList) throws Exception;

	public List<InfoServiceOrderAttrVo> queryInfoServiceOrderAttrByOrderNo(InfoServiceOrderAttrVo infoServiceOrderAttrVoList) throws Exception;
	
}
