package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;


import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderGoodNbrVo;

public interface InfoServiceOrderGoodNbrServDu {

	//public List<InfoServiceOrderGoodNbrVo> queryInfoServiceOrderGoodNbrByOfrOrderNo(InfoServiceOrderGoodNbrVo vo) throws Exception;

	public List<InfoServiceOrderGoodNbrVo> queryInfoServiceOrderGoodNbrByOrderNo(InfoServiceOrderGoodNbrVo vo) throws Exception;

}
