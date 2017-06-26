package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderActivityHisVo;

public interface InfoServiceOrderActivityHisServDu {

	public List<InfoServiceOrderActivityHisVo> queryInfoServiceOrderActivityHisByOrderNo(InfoServiceOrderActivityHisVo vo)throws Exception;
}
