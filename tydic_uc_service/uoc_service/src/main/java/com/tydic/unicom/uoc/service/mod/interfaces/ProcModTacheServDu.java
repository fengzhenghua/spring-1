package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.service.cache.service.redis.po.ProcModTache;
import com.tydic.unicom.uoc.business.common.vo.ProcModTacheVo;

public interface ProcModTacheServDu {

	public ProcModTacheVo queryProcModTacheVoByTacheCode(ProcModTacheVo procModTacheVo)throws Exception;
	
	public boolean create(ProcModTacheVo procModTacheVo)throws Exception;
	
	public boolean update(ProcModTacheVo procModTacheVo)throws Exception;
	
	public boolean delete(ProcModTacheVo procModTacheVo)throws Exception;
	
	public List<ProcModTacheVo> queryProcModTacheList(ProcModTacheVo procModTacheVo,int pageNo,int pageSize)throws Exception;
	
	public int  queryProcModTacheListCount(ProcModTacheVo procModTacheVo)throws Exception;
	
	public List<ProcModTacheVo> queryProcModTacheAll() throws Exception;
	/**根据province_code从redis获取数据*/
	public List<ProcModTache> queryProcModTacheByProvinceCodeFromRedis(String provinceCode) throws Exception;
	
	
}
