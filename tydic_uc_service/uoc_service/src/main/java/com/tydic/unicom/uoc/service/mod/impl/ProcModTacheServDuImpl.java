package com.tydic.unicom.uoc.service.mod.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.ProcModTache;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.ProcModTachePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcModTacheServ;
import com.tydic.unicom.uoc.business.common.vo.ProcModTacheVo;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServDu;
import com.tydic.unicom.webUtil.UocMessage;
@Service("ProcModTacheServDu")
public class ProcModTacheServDuImpl implements ProcModTacheServDu{

	@Autowired
	private ProcModTacheServ procModTacheServ;
	@Autowired
	private RedisServiceServ redisServiceServ;

	@Override
	public ProcModTacheVo queryProcModTacheVoByTacheCode(
			ProcModTacheVo vo) throws Exception{
	//  tache_code
		ProcModTache procModTache = new ProcModTache();
		ProcModTacheVo procModTacheVo = new ProcModTacheVo();

		UocMessage msg = redisServiceServ.queryDataFromRedis("queryProcModTacheByTacheCode");
		RedisData redisData=(RedisData) msg.getArgs().get("RedisDataResult");
		Map<String,Object> tacheMap = redisData.getMap();
		procModTache = (ProcModTache) tacheMap.get(vo.getTache_code());
		if(procModTache != null){
			BeanUtils.copyProperties(procModTache, procModTacheVo);
			return procModTacheVo;
		}else{
			ProcModTachePo procPo = new ProcModTachePo();
			BeanUtils.copyProperties(procModTacheVo, procPo);
			procPo = procModTacheServ.queryProcModTacheByTacheCode(procPo);
			if(procPo != null){
				BeanUtils.copyProperties(procPo, procModTacheVo);
				return procModTacheVo;
			}else{
				return null;
			}

		}

	}
	@Override
	public boolean create(ProcModTacheVo procModTacheVo) throws Exception {
		ProcModTachePo procPo = new ProcModTachePo();
		BeanUtils.copyProperties(procModTacheVo, procPo);
		boolean res = procModTacheServ.create(procPo);
		return res;
	}
	@Override
	public boolean update(ProcModTacheVo procModTacheVo) throws Exception {
		ProcModTachePo procPo = new ProcModTachePo();
		BeanUtils.copyProperties(procModTacheVo, procPo);
		boolean res = procModTacheServ.update(procPo);
		return res;
	}
	@Override
	public boolean delete(ProcModTacheVo procModTacheVo) throws Exception {
		ProcModTachePo procPo = new ProcModTachePo();
		BeanUtils.copyProperties(procModTacheVo, procPo);
		boolean res = procModTacheServ.delete(procPo);
		return res;
	}



	@Override
	public int queryProcModTacheListCount(ProcModTacheVo procModTacheVo)
			throws Exception {
		ProcModTachePo procPo = new ProcModTachePo();
		BeanUtils.copyProperties(procModTacheVo, procPo);
		int res = procModTacheServ.queryProcModTacheListCount(procPo);
		return res;
	}
	@Override
	public List<ProcModTacheVo> queryProcModTacheList(
			ProcModTacheVo procModTacheVo, int pageNo, int pageSize)
			throws Exception {
		ProcModTachePo procPo = new ProcModTachePo();
		BeanUtils.copyProperties(procModTacheVo, procPo);
		List<ProcModTacheVo> listVo = new ArrayList<ProcModTacheVo>();
		List<ProcModTachePo> listPo = procModTacheServ.queryProcModTacheList(procPo, pageNo, pageSize);
		if(listPo != null && listPo.size()>0){
			for(ProcModTachePo po : listPo){
				ProcModTacheVo ordVo = new ProcModTacheVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

	@Override
	public List<ProcModTacheVo> queryProcModTacheAll() throws Exception {
		List<ProcModTachePo> list = procModTacheServ.queryProcModTacheAll();
		if(list != null && list.size()>0){
			List<ProcModTacheVo> listOut = new ArrayList<ProcModTacheVo>();
			for(int i=0;i<list.size();i++){
				ProcModTacheVo procModTacheVo = new ProcModTacheVo();
				BeanUtils.copyProperties(list.get(i), procModTacheVo);
				listOut.add(procModTacheVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

	@Override
	public List<ProcModTache> queryProcModTacheByProvinceCodeFromRedis(String provinceCode) throws Exception {
		UocMessage uocMessage = redisServiceServ.queryDataFromRedis("proc_tache");
		RedisData redisData=(RedisData) uocMessage.getArgs().get("RedisDataResult");
		Map<String,Object> dataMap = redisData.getMap();
		@SuppressWarnings("unchecked")
		List<ProcModTache> list = (List<ProcModTache>) dataMap.get(provinceCode+"_*");
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
