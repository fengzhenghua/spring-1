package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.InfoRegionPo;

/**
 * 对表ZB_INFO_REGION的DAO类
 * @author ZhangCheng
 * @date 2017-04-12
 */
public interface InfoRegionServ {
	
	/**
	 * 查询地区信息
	 * @param infoRegionPo
	 * @return
	 */
	List<InfoRegionPo> queryInfoRegions(InfoRegionPo infoRegionPo);

}
