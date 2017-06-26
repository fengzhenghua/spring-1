package com.tydic.unicom.upc.base.database.inst.interfaces;

import java.util.List;

import com.tydic.unicom.upc.base.database.po.inst.PaySettleTransPo;

public interface PaySettleTransServ {
	
	// 
	//public List<PaySettleTransPo> getBillRecordByDate(String bill_date);
	
	// 判断是 是否已经对账
	public int  isBillByDate(String bill_date);
	
	// 查询业务系统所对应的账单
	public List<PaySettleTransPo> getBillByDate_busi(String bill_date,String busi_id);
}
