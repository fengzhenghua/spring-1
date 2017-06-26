package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderFixHisVo;

public interface InfoServiceOrderFixHisServDu {

	
	public List<InfoServiceOrderFixHisVo> queryInfoServiceOrderFixHisByOrderNo(InfoServiceOrderFixHisVo vo)throws Exception;

}
