package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderPackageVo;

public interface InfoServiceOrderPackageServDu {

	//public List<InfoServiceOrderPackageVo> queryInfoServiceOrderPackageByOfrOrderNo(InfoServiceOrderPackageVo vo) throws Exception;

	public List<InfoServiceOrderPackageVo> queryInfoServiceOrderPackageByOrderNo(InfoServiceOrderPackageVo vo)throws Exception;
}
