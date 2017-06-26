package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.uoc.business.common.vo.ExpressVo;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface ExpressServDu {
	/**
	 * 获取顺丰纸质信息
	 * @param vo
	 * @return
	 */
	public UocMessage getPaperInfoForSF(ParaVo vo)throws Exception;
	/**
	 * 保存默认寄件信息  UOC0040
	 * @return
	 */
	public UocMessage saveSendInfo(ExpressVo vo) throws Exception;
	/**
	 * 	获取默认寄件信息 接口编号UOC0043
	 * @param vo
	 * @return
	 */
	
	public UocMessage getSendInfo(ExpressVo vo)throws Exception;
}
