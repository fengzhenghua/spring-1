package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderPackageHisVo;

public interface InfoServiceOrderPackageHisServDu {

	
	public List<InfoServiceOrderPackageHisVo> queryInfoServiceOrderPackageHisByOrderNo(InfoServiceOrderPackageHisVo vo)throws Exception;
}
