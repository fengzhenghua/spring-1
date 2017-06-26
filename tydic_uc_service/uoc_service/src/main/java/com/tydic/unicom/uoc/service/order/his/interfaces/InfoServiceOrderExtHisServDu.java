package com.tydic.unicom.uoc.service.order.his.interfaces;


import java.util.Map;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderExtHisVo;

public interface InfoServiceOrderExtHisServDu {

	public InfoServiceOrderExtHisVo queryInfoServiceOrderExtByOrderNo(InfoServiceOrderExtHisVo vo)throws Exception;
	
	public Map<String,Object> getAttrDescHis(InfoServiceOrderExtHisVo vo,String tableName)throws Exception;
}
