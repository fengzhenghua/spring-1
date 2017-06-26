package com.tydic.unicom.uac.business.common.interfaces;

import com.tydic.unicom.uac.business.common.vo.RewardOrderInfoVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface InfoInputOrderServDu {

	/**
	 * UAC0011-即时激励数据写入
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage createRewardOrderInfo(RewardOrderInfoVo vo) throws Exception;

}
