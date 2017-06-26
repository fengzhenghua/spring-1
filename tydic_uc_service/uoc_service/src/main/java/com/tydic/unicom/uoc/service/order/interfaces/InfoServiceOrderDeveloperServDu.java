package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;


import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderDeveloperVo;

public interface InfoServiceOrderDeveloperServDu {
	
	public List<InfoServiceOrderDeveloperVo> queryInfoServiceOrderDeveloperByOrderNo(InfoServiceOrderDeveloperVo vo)throws Exception;
}
