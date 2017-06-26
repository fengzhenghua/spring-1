package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;


import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderSimCardVo;

public interface InfoServiceOrderSimCardServDu {

	//public List<InfoServiceOrderSimCardVo> queryInfoServiceOrderSimCardByOfrOrderNo(InfoServiceOrderSimCardVo vo) throws Exception;
	
	public List<InfoServiceOrderSimCardVo> queryInfoServiceOrderSimCardByOrderNo(InfoServiceOrderSimCardVo vo)throws Exception;
}
