package com.tydic.unicom.upc.service.inst.interfaces;

import java.util.List;

import com.tydic.unicom.upc.vo.inst.PaySettleTransVo;


public interface PaySettleTransDu {
	public int isBillByDate(String bill_date);
	
	public List<PaySettleTransVo> getBillByDate_busi(String bill_date,String busi_id);

}
