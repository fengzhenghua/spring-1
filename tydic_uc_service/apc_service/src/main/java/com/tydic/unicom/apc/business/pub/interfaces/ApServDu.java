package com.tydic.unicom.apc.business.pub.interfaces;

import com.tydic.unicom.apc.business.pub.vo.ApParaVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface ApServDu {
	/**
	 * 6	触点维护APC0005
	 * @return
	 * @throws Exception
	 */
	public UocMessage createOperateAp(ApParaVo vo) throws Exception;
	/**
	 * 7	触点商品维护 APC0006
	 * @return
	 * @throws Exception
	 */
	public UocMessage createOperateApGoods(ApParaVo vo) throws Exception;

	/**
	 * 触点查询 APC0008
	 * @return
	 * @throws Exception
	 */
	public UocMessage queryApDefineInfo(ApParaVo vo) throws Exception;

	/**
	 * 触点商品查询 APC0009
	 * @return
	 * @throws Exception
	 */
	public UocMessage queryApGoodsInfo(ApParaVo vo) throws Exception;

	/**
	 * 触点二维码页面选择发展人
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage queryApBulidQrcodeDevelopers(ApParaVo vo) throws Exception;

}
