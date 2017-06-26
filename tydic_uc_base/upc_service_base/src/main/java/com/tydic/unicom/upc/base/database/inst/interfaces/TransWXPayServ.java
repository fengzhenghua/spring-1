package com.tydic.unicom.upc.base.database.inst.interfaces;

import java.util.List;

import com.tydic.unicom.upc.base.database.po.inst.TransWXPayPo;

public interface TransWXPayServ {

	/**
	 * 批量添加微信流水
	 * @param poList
	 */
	public void addTransWXPay(List<TransWXPayPo> poList);
	
	/**
	 * 获取指定天数的流水总数
	 * @param billDate
	 * @param appid
	 * @return
	 */
	public int getCountTransByBillDate(String billDate, String appid, String mchid);
}
