package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderActivityVo;

public interface InfoServiceOrderActivityServDu {

	public List<InfoServiceOrderActivityVo> queryInfoServiceOrderActivityByOrderNo(InfoServiceOrderActivityVo vo)throws Exception;
}
