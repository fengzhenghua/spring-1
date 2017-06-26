package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderM165Vo;

public interface InfoServiceOrderM165ServDu{

	//public List<InfoServiceOrderM165Vo> queryInfoServiceOrderM165ByOfrOrderNo(InfoServiceOrderM165Vo vo) throws Exception;

	public List<InfoServiceOrderM165Vo> queryInfoServiceOrderM165ByOrderNo(InfoServiceOrderM165Vo vo)throws Exception;
}
