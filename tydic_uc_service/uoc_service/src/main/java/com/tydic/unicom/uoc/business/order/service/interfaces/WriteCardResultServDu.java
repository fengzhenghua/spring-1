package com.tydic.unicom.uoc.business.order.service.interfaces;

import com.tydic.unicom.uoc.business.order.service.vo.WriteCardResultInVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface WriteCardResultServDu {
	
	/**
	 * uoc0062 写卡结果查询
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage queryWriteCardResult(WriteCardResultInVo vo) throws Exception;
	/**
	 * uoc0063 写卡结果记录
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage createWriteCardResult(WriteCardResultInVo vo) throws Exception;


}
