package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderFixVo;

public interface InfoServiceOrderFixServDu {

	//public List<InfoServiceOrderFixVo> queryInfoServiceOrderFixByOfrOrderNo(InfoServiceOrderFixVo vo) throws Exception;

	public List<InfoServiceOrderFixVo> queryInfoServiceOrderFixByOrderNo(InfoServiceOrderFixVo vo)throws Exception;

}
