package com.tydic.unicom.uoc.service.order.his.interfaces;


import java.util.Map;

import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderExtHisVo;
public interface InfoSaleOrderExtHisServDu {
	
	public InfoSaleOrderExtHisVo getInfoSaleOrderExtHisBySaleOrderNo(InfoSaleOrderExtHisVo vo)throws Exception;
	
	public Map<String, Object> getAttrDesc(InfoSaleOrderExtHisVo vo,String tableName)throws Exception;
}
