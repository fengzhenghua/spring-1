package com.tydic.unicom.uoc.service.mod.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.ProcModTacheLogin;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.ProcModTacheLoginPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcModTacheLoginServ;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheLoginServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheLoginVo;
import com.tydic.unicom.webUtil.UocMessage;
@Service("ProcModTacheLoginServDu")
public class ProcModTacheLoginServDuImpl implements ProcModTacheLoginServDu{

	@Autowired
	private ProcModTacheLoginServ procModTacheLoginServ;

	@Autowired
	private RedisServiceServ redisServiceServ;
	@Override
	public List<ProcModTacheLoginVo> queryProcModTacheLoginByTacheCode(
			ProcModTacheLoginVo procModTacheLoginVo) throws Exception{
		
		List<ProcModTacheLoginVo> listVo = new ArrayList<ProcModTacheLoginVo>();
		
		ProcModTacheLogin procModTacheLogin = new ProcModTacheLogin();
		ProcModTacheLoginVo procModTacheLoginVoRedis = new ProcModTacheLoginVo();
		UocMessage msg = redisServiceServ.queryDataFromRedis("queryProcModTacheLoginByTacheCode");
		RedisData redisData=(RedisData) msg.getArgs().get("RedisDataResult");
		Map<String,Object> loginMap=redisData.getMap();
		
		procModTacheLogin = (ProcModTacheLogin) loginMap.get(procModTacheLoginVo.getTache_code()+"-"+procModTacheLoginVo.getRole_id());
		if(procModTacheLogin != null){
			BeanUtils.copyProperties(procModTacheLogin, procModTacheLoginVoRedis);
			listVo.add(procModTacheLoginVoRedis);
			return listVo;
		}else{
			ProcModTacheLoginPo logPo = new ProcModTacheLoginPo();
			List<ProcModTacheLoginPo> listPo = new ArrayList<ProcModTacheLoginPo>();
			BeanUtils.copyProperties(procModTacheLoginVo, logPo);
					
			listPo = procModTacheLoginServ.queryProcModTacheLoginByTacheCode(logPo);
			if(listPo != null && listPo.size()>0){
				for(ProcModTacheLoginPo po : listPo){
					ProcModTacheLoginVo vo = new ProcModTacheLoginVo();
					BeanUtils.copyProperties(po, vo);
					listVo.add(vo);
				}
				return listVo;
			}else{
				return null;	
			}
		}
		
		
			
	}
	@Override
	public boolean create(ProcModTacheLoginVo procModTacheLoginVo)
			throws Exception {
		ProcModTacheLoginPo logPo = new ProcModTacheLoginPo();
		BeanUtils.copyProperties(procModTacheLoginVo, logPo);
		boolean res = procModTacheLoginServ.create(logPo);
		return res;
	}
	@Override
	public boolean update(ProcModTacheLoginVo procModTacheLoginVo)
			throws Exception {
		ProcModTacheLoginPo logPo = new ProcModTacheLoginPo();
		BeanUtils.copyProperties(procModTacheLoginVo, logPo);
		boolean res = procModTacheLoginServ.update(logPo);
		return res;
	}
	@Override
	public boolean delete(ProcModTacheLoginVo procModTacheLoginVo)
			throws Exception {
		ProcModTacheLoginPo logPo = new ProcModTacheLoginPo();
		BeanUtils.copyProperties(procModTacheLoginVo, logPo);
		boolean res = procModTacheLoginServ.delete(logPo);
		return res;
	}
	
	@Override
	public int queryProcModTacheLoginListCount(
			ProcModTacheLoginVo procModTacheLoginVo) throws Exception {
		ProcModTacheLoginPo logPo = new ProcModTacheLoginPo();
		BeanUtils.copyProperties(procModTacheLoginVo, logPo);
		int res = procModTacheLoginServ.queryProcModTacheLoginListCount(logPo);
		return res;
	}
	@Override
	public List<ProcModTacheLoginVo> queryProcModTacheLoginList(
			ProcModTacheLoginVo procModTacheLoginVo, int pageNo, int pageSize)
			throws Exception {
		List<ProcModTacheLoginVo> listVo = new ArrayList<ProcModTacheLoginVo>();
		ProcModTacheLoginPo logPo = new ProcModTacheLoginPo();
		BeanUtils.copyProperties(procModTacheLoginVo, logPo);
		
		List<ProcModTacheLoginPo> listPo = procModTacheLoginServ.queryProcModTacheLoginList(logPo, pageNo, pageSize);
		if(listPo != null && listPo.size()>0){
			for(ProcModTacheLoginPo poTemp : listPo){
				ProcModTacheLoginVo ordVo = new ProcModTacheLoginVo();
				BeanUtils.copyProperties(poTemp, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
	@Override
	public List<ProcModTacheLoginVo> queryProcModTacheLoginAll() throws Exception {
		List<ProcModTacheLoginPo> list = procModTacheLoginServ.queryProcModTacheLoginAll();
		if(list != null && list.size()>0){
			List<ProcModTacheLoginVo> listOut = new ArrayList<ProcModTacheLoginVo>();
			for(int i=0;i<list.size();i++){
				ProcModTacheLoginVo procModTacheLoginVo = new ProcModTacheLoginVo();
				BeanUtils.copyProperties(list.get(i), procModTacheLoginVo);
				listOut.add(procModTacheLoginVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}
