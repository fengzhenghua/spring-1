package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface GetRegionInfoServDu {

	/**获取地区信息*/
	public UocMessage getRegionInfo(String jsession_id,String region_level,String upper_region_id) throws Exception;
	
	/**获取默认省份，城市和区域信息*/
	public UocMessage getDefaultProvinceCityAreaInfo(String jsession_id) throws Exception;
}
