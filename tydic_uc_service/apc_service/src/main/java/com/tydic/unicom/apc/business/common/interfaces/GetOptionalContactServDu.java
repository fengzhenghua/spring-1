package com.tydic.unicom.apc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface GetOptionalContactServDu {
	/**
	 * 获取可选触点 APC0011
	 */
	public UocMessage getOptionalContact(String jsession_id,String ap_id,String ap_name);
}
