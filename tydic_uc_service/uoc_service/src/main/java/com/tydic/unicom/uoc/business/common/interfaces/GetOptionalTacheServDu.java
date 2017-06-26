package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface GetOptionalTacheServDu {

	/**获取可选环节*/
	public UocMessage getOptionalTache(String jsession_id) throws Exception;
}
