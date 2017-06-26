package com.tydic.unicom.apc.business.ofr.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface OppoNumberServiceServDu {

	public UocMessage oppoSelectNumber(String oper_no,String fuzzy_query,String page_info,String tele_type,String ser_type,String good_num_flag) throws Exception;
	
	public UocMessage oppoOccupyNumber(String oper_no,String acc_nbr,String ser_type,String tele_type,String occupiedFlag) throws Exception;
}
