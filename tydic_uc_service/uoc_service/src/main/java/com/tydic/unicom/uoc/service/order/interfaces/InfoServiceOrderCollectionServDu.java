package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;


import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderCollectionVo;

public interface InfoServiceOrderCollectionServDu {

	public List<InfoServiceOrderCollectionVo> queryInfoServiceOrderCollectionByOrderNo(InfoServiceOrderCollectionVo vo)throws Exception;
}
