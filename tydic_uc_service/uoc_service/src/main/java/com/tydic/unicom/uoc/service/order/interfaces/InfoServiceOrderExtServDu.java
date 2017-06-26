package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;
import java.util.Map;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderExtVo;

public interface InfoServiceOrderExtServDu {

	public List<InfoServiceOrderExtVo> queryInfoServiceOrderExtByOrderNo(InfoServiceOrderExtVo vo)throws Exception;
	
	public Map<String,Object> getAttrDesc(InfoServiceOrderExtVo vo,String tableName)throws Exception;
	
	public boolean updateInfoServiceOrderExtFileSix(InfoServiceOrderExtVo vo)throws Exception;
}
