package com.tydic.unicom.ugc.business.common.interfaces;

import com.tydic.unicom.ugc.business.common.vo.ApGoodsVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface ApGoodsServDu {

	public UocMessage queryApGoodsList(String jsession_id, String json_info) throws Exception;
	/**
	 * 4	触点商品维护  UGC0003
	 * @param jsession_id
	 * @param json_info
	 * @param oper_type
	 * @return
	 */
	public UocMessage commitOperateApGoods(String jsession_id, String json_info, String oper_type) throws Exception;

	public boolean addApGoods(ApGoodsVo vo) throws Exception;

	public boolean deleteApGoods(ApGoodsVo vo) throws Exception;

	public boolean updateApGoods(ApGoodsVo vo) throws Exception;

}
