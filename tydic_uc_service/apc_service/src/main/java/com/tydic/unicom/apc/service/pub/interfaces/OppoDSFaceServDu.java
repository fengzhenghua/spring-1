package com.tydic.unicom.apc.service.pub.interfaces;

import java.util.Map;

public interface OppoDSFaceServDu {

	public Map<String,String> getLipLanguageInfo(String appid, String bucket) throws Exception;
	
	public Map<String, String> oppoLivingExamine(String appid, String bucket, String validate_data, String video,String idcard_number, String idcard_name) throws Exception;
}
