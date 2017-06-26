package com.tydic.unicom.uoc.business.order.service.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface SendPhotoServDu {
	/**
	 * uoc0071 开户照片上传
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage getSendPhoto(String jsession_id,String serv_order_no) throws Exception;

}
