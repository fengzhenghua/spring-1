package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;


import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderPayVo;

public interface InfoServiceOrderPayServDu {

	//public List<InfoServiceOrderPayVo> queryInfoServiceOrderPayByOfrOrderNo(InfoServiceOrderPayVo vo)throws Exception;


	public List<InfoServiceOrderPayVo> queryInfoServiceOrderPayByOrderNo(InfoServiceOrderPayVo vo)throws Exception;
}
