package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderExtHisPo;

public interface InfoServiceOrderExtHisServ {
	
	/**
	 * 写入服务订单拓展属性横表
	 * @param po
	 * @return
	 */
	public boolean create(InfoServiceOrderExtHisPo po) throws Exception;

	public InfoServiceOrderExtHisPo queryInfoServiceOrderExtHisByServOrderNo(InfoServiceOrderExtHisPo po) throws Exception;
}
