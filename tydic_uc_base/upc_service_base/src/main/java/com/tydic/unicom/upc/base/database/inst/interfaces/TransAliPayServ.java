package com.tydic.unicom.upc.base.database.inst.interfaces;

import java.util.List;

import com.tydic.unicom.upc.base.database.po.inst.TransAliPayPo;

public interface TransAliPayServ {

	/**
	 * 批量添加流水
	 * @param poList
	 */
	public void addTrans(List<TransAliPayPo> poList);
	
	/**
	 * 获取指定天数的流水总数
	 * @param billDate
	 * @param appid
	 * @return
	 */
	public int getCountTransByBillDate(String billDate, String appid);
}
