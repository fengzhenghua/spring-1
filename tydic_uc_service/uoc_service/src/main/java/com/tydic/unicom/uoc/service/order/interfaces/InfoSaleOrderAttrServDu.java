package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderAttrVo;

public interface InfoSaleOrderAttrServDu {
	/*
	 * 根据SaleOrderNo删除InfoSaleOrderAttr表
	 */
	public boolean deleteInfoSaleOrderAttrBySaleOrderNo(InfoSaleOrderAttrVo vo)throws Exception;
	
	public List<InfoSaleOrderAttrVo> queryInfoSaleOrderAttrList(InfoSaleOrderAttrVo vo)throws Exception;
	
	
}
