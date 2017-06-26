package com.tydic.unicom.ugc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface GetCodeListServDu {
	
	public UocMessage getCodeListData(String jsession_id,String type_code) throws Exception;
}
